package com.example.cleanarchitecture_mvvm.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cleanarchitecture_mvvm.R;
import com.example.domain.model.Repository;

public class DetailActivity extends AppCompatActivity {

    private ImageView iv;
    private TextView username,name,userUrl,repoName,repoUrl,repoDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        iv=findViewById(R.id.activity_detail_iv);
        username=findViewById(R.id.activity_detail_username_tv);
        userUrl=findViewById(R.id.activity_detail_userurl_tv);
        name=findViewById(R.id.activity_detail_name_tv);
        repoDesc=findViewById(R.id.activity_detail_repo_desc_tv);
        repoUrl=findViewById(R.id.activity_detail_repo_url_tv);
        repoName=findViewById(R.id.activity_detail_repo_name_tv);

        Repository repository= (Repository) getIntent().getSerializableExtra("repoDetail");

        Glide.with(this)
                .load(repository.getAvatar()) // Image URL
                .centerCrop() // Image scale type
                .into(iv);
        username.setText(repository.getUsername());
        userUrl.setText(repository.getUrl());
        name.setText(repository.getName());
        repoDesc.setText(repository.getRepoDetail().getDescription());
        repoUrl.setText(repository.getRepoDetail().getUrl());
        repoName.setText(repository.getRepoDetail().getName());


    }
}
