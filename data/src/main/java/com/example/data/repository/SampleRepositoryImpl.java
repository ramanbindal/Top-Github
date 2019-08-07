package com.example.data.repository;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.data.data.SampleRoomDatabase;
import com.example.data.data.dao.SampleDao;
import com.example.data.models.RepositoryNw;
import com.example.data.network.ApiInterface;
import com.example.data.sharedpreference.SharedPreferenceHelper;
import com.example.domain.model.RepoDetail;
import com.example.domain.model.Repository;
import com.example.domain.repository.SampleRepository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.functions.Function;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SampleRepositoryImpl implements SampleRepository {

    private ApiInterface apiInterface;
    private SharedPreferenceHelper sharedPreferenceHelper;
    private Context context;

    private SampleDao sampleDao;
    private SampleRoomDatabase db;

    public SampleRepositoryImpl(ApiInterface apiInterface, SharedPreferenceHelper sharedPreferenceHelper, SampleRoomDatabase db, Context context) {
        this.apiInterface = apiInterface;
        this.sharedPreferenceHelper = sharedPreferenceHelper;
        this.context = context;
        this.db = db;
        sampleDao = db.wordDao();
    }


    @Override
    public Single<List<Repository>> fetchData() {
        return apiInterface.fetchData("java", "weekly")
                .map(new Function<List<RepositoryNw>, List<Repository>>() {
                    @Override
                    public List<Repository> apply(List<RepositoryNw> repositoryNws) throws Exception {
                        List<Repository> repositoryList = new ArrayList<>();

                        for (RepositoryNw repositoryNw : repositoryNws) {
                            Repository repository = new Repository(repositoryNw.getUsername(),
                                    repositoryNw.getName(),
                                    repositoryNw.getType(),
                                    repositoryNw.getUrl(),
                                    repositoryNw.getAvatar(),
                                    new RepoDetail(repositoryNw.getRepo().getName(),
                                            repositoryNw.getRepo().getDescription(), repositoryNw.getRepo().getUrl()));


                            ContextWrapper cw = new ContextWrapper(context);
                            File directory = cw.getDir("Top_Github_attachments", Context.MODE_PRIVATE);
                            String fileName = repository.getUsername() + ".jpg";
                            String filePath = directory.getAbsolutePath()+"/"+fileName;
                            File file = new File(filePath);
                            if (file.exists()) {
                                Log.e("raman","file path and exists"+filePath);
                                repository.setImagePath(filePath);
                            } else {
                            }

                            repositoryList.add(repository);
                        }

                        return repositoryList;
                    }
                });
    }

    @Override
    public Single<Repository> fetchImage(Repository repository) {
        return Single.create(new SingleOnSubscribe<Repository>() {
            @Override
            public void subscribe(SingleEmitter<Repository> emitter) throws Exception {


                String url = repository.getAvatar();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();


                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println("request failed: " + e.getMessage());
                        Log.e("raman", e.getMessage());
                        emitter.tryOnError(e);
                    }

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        InputStream finput = response.body().byteStream();

                        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                        byte[] data = new byte[16384];

                        String fileName = "";
                        ContextWrapper cw = new ContextWrapper(context);
                        File directory = cw.getDir("Top_Github_attachments", Context.MODE_PRIVATE);

                        fileName = repository.getUsername() + ".jpg";
                        File file = new File(directory, fileName);
                        FileOutputStream out = null;

                        out = new FileOutputStream(file);

                        int c;
                        while ((c = finput.read(data)) != -1) {
                            out.write(data, 0, c);
                        }

                        String filePath = directory.getAbsolutePath()+"/"+fileName;
                        repository.setImagePath(filePath);
                        emitter.onSuccess(repository);
                    }

                });


            }
        });
    }

}
