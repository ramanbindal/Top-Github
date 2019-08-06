package com.example.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RepositoryNw {

    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("avatar")
    @Expose
    public String avatar;
    @SerializedName("repo")
    @Expose
    public RepoDetailNw repo;

    public RepositoryNw(String username, String name, String type, String url, String avatar, RepoDetailNw repo) {
        this.username = username;
        this.name = name;
        this.type = type;
        this.url = url;
        this.avatar = avatar;
        this.repo = repo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public RepoDetailNw getRepo() {
        return repo;
    }

    public void setRepo(RepoDetailNw repo) {
        this.repo = repo;
    }
}
