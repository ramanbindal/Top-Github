package com.example.topGithub.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.topGithub.R;
import com.example.domain.model.Repository;

import java.io.File;

public class DetailActivity extends AppCompatActivity {

    private ImageView iv;
    private TextView username, name, userUrl, repoName, repoUrl, repoDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        iv = findViewById(R.id.activity_detail_iv);
        username = findViewById(R.id.activity_detail_username_tv);
        userUrl = findViewById(R.id.activity_detail_userurl_tv);
        name = findViewById(R.id.activity_detail_name_tv);
        repoDesc = findViewById(R.id.activity_detail_repo_desc_tv);
        repoUrl = findViewById(R.id.activity_detail_repo_url_tv);
        repoName = findViewById(R.id.activity_detail_repo_name_tv);

        Repository repository = (Repository) getIntent().getSerializableExtra("repoDetail");
        userUrl.setText(repository.getUrl());
        name.setText(repository.getName());
        repoDesc.setText(repository.getRepoDetail().getDescription());
        repoUrl.setText(repository.getRepoDetail().getUrl());
        repoName.setText(repository.getRepoDetail().getName());


        File imgFile = new File(repository.getImagePath());
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            iv.setImageBitmap(myBitmap);

        }

    }
}
