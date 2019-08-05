package com.example.cleanarchitecture_mvvm.ui.main;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cleanarchitecture_mvvm.MainApplication;
import com.example.cleanarchitecture_mvvm.R;
import com.example.cleanarchitecture_mvvm.base.BaseActivity;
import com.example.cleanarchitecture_mvvm.ui.DetailActivity;
import com.example.domain.model.Repository;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<MainViewModel> implements MainNavigator, RepoListAdapter.onItemClick {

    private TextView textView;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    MainViewModel mainViewModel;

    TextView errorTv;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    RepoListAdapter repoListAdapter;

    @Override
    public MainViewModel getViewModel() {
        return mainViewModel;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((MainApplication) getApplicationContext()).getComponent().inject(this);


        textView = (TextView) findViewById(R.id.activity_main_text_view);
        progressBar = findViewById(R.id.activity_main_pb);

        recyclerView = findViewById(R.id.activity_main_rv);
        RecyclerView.LayoutManager layoutManagerFooter = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManagerFooter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);

        mainViewModel.setNavigator(this);

        mainViewModel.fetchDataFromApi();


    }

    @Override
    public void onResponseLoaded(List<Repository> respons) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        repoListAdapter = new RepoListAdapter(respons, this, this);
    }

    @Override
    public void onError(String message) {
        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        textView.setText(message);
    }

    @Override
    public void onItemClick(Repository repository) {
        Intent i = new Intent(MainActivity.this, DetailActivity.class);
        i.putExtra("repoDetail", repository);
        startActivity(i);
    }
}
