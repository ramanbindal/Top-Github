package com.example.data.network;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static String SERVER_BASE_URL = "https://github-trending-api.now.sh";
    private static Retrofit retrofit = null;


    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            File httpCacheDirectory = new File(context.getCacheDir(), "offlineCache");

            //10 MB
            Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

//            OkHttpClient client = new OkHttpClient.Builder()
//                    .addInterceptor(interceptor)
//                    .connectTimeout(3, TimeUnit.MINUTES)
//                    .readTimeout(3, TimeUnit.MINUTES)
//                    .build();

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(provideCacheInterceptor())
                    .addInterceptor(provideOfflineCacheInterceptor())
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_BASE_URL)
                    .client(httpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    private static Interceptor provideCacheInterceptor() {

        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response originalResponse = chain.proceed(request);
                String cacheControl = originalResponse.header("Cache-Control");

                if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                        cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0")) {


                    CacheControl cc = new CacheControl.Builder()
                            .maxStale(1, TimeUnit.DAYS)
                            .build();



                    request = request.newBuilder()
                            .cacheControl(cc)
                            .build();

                    return chain.proceed(request);

                } else {
                    return originalResponse;
                }
            }
        };

    }
    private static Interceptor provideOfflineCacheInterceptor() {

        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                try {
                    return chain.proceed(chain.request());
                } catch (Exception e) {


                    CacheControl cacheControl = new CacheControl.Builder()
                            .onlyIfCached()
                            .maxStale(1, TimeUnit.DAYS)
                            .build();

                    Request offlineRequest = chain.request().newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                    return chain.proceed(offlineRequest);
                }
            }
        };
    }
}
