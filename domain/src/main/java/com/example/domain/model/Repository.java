package com.example.domain.model;

import java.io.Serializable;

public class Repository implements Serializable {

    public String username;
    public String name;
    public String type;
    public String url;
    public String avatar;
    public RepoDetail repoDetail;

    public Repository(String username, String name, String type, String url, String avatar, RepoDetail repoDetail) {
        this.username = username;
        this.name = name;
        this.type = type;
        this.url = url;
        this.avatar = avatar;
        this.repoDetail = repoDetail;
    }

    public Repository() {
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

    public RepoDetail getRepoDetail() {
        return repoDetail;
    }

    public void setRepoDetail(RepoDetail repoDetail) {
        this.repoDetail = repoDetail;
    }
}
