package com.example.data.repository;

import android.content.Context;

import com.example.data.data.SampleRoomDatabase;
import com.example.data.data.dao.SampleDao;
import com.example.data.models.ResponseNw;
import com.example.data.network.ApiInterface;
import com.example.data.sharedpreference.SharedPreferenceHelper;
import com.example.domain.model.Repo;
import com.example.domain.model.Response;
import com.example.domain.repository.SampleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.functions.Function;

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
    public Single<List<Response>> fetchData() {
        return apiInterface.fetchData("java", "weekly")
                .map(new Function<List<ResponseNw>, List<Response>>() {
                    @Override
                    public List<Response> apply(List<ResponseNw> responseNws) throws Exception {
                        List<Response> responseList = new ArrayList<>();

                        for (ResponseNw responseNw : responseNws) {
                            Response response = new Response(responseNw.getUsername(),
                                    responseNw.getName(),
                                    responseNw.getType(),
                                    responseNw.getUrl(),
                                    responseNw.getAvatar(),
                                    new Repo(responseNw.getRepo().getName(),
                                            responseNw.getRepo().getDescription(), responseNw.getRepo().getUrl()));
                            responseList.add(response);
                        }

                        return responseList;
                    }
                });
    }

}
