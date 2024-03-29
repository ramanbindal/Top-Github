package com.example.topGithub.ui.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.topGithub.R;
import com.example.domain.model.Repository;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.MyViewHolder> {
    List<Repository> dataList;
    public onItemClick onItemClick;
    public Context context;

    public RepoListAdapter(List<Repository> dataList, RepoListAdapter.onItemClick onItemClick, Context context) {
        this.dataList = dataList;
        this.onItemClick = onItemClick;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.repo_list_rv_item, null);
        MyViewHolder mh = new MyViewHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.userNameTv.setText(dataList.get(i).getUsername());
        myViewHolder.repoNameTv.setText(dataList.get(i).getRepoDetail().getName());

        File imgFile = new File(dataList.get(i).getImagePath());
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            myViewHolder.avatar.setImageBitmap(myBitmap);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView userNameTv, repoNameTv;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.repo_list_rv_item_repo_image);
            userNameTv = itemView.findViewById(R.id.repo_list_rv_item_repo_name);
            repoNameTv = itemView.findViewById(R.id.repo_list_rv_item_user_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onItemClick(dataList.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface onItemClick {
        public void onItemClick(Repository repository);
    }
}
