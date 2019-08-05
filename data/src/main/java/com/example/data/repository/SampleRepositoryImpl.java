package com.example.data.repository;

import android.content.Context;

import com.example.data.data.SampleRoomDatabase;
import com.example.data.data.dao.SampleDao;
import com.example.data.models.RepositoryNw;
import com.example.data.network.ApiInterface;
import com.example.data.sharedpreference.SharedPreferenceHelper;
import com.example.domain.model.RepoDetail;
import com.example.domain.model.Repository;
import com.example.domain.repository.SampleRepository;

import java.util.ArrayList;
import java.util.List;

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
                            repositoryList.add(repository);
                        }

                        return repositoryList;
                    }
                });
    }

}
