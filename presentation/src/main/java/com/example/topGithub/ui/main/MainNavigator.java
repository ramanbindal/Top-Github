package com.example.topGithub.ui.main;

import com.example.domain.model.Repository;

import java.util.List;

public interface MainNavigator {

    void onResponseLoaded(List<Repository> respons);

    void onError(String message);
}
