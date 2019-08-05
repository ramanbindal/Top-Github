package com.example.cleanarchitecture_mvvm.ui.main;

import com.example.cleanarchitecture_mvvm.base.BaseViewModel;
import com.example.domain.model.Repository;
import com.example.domain.usecases.FetchDataUseCase;

import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;

public class MainViewModel extends BaseViewModel<MainNavigator> {


    public FetchDataUseCase fetchDataUseCase;

    public MainViewModel(FetchDataUseCase fetchDataUseCase) {
        this.fetchDataUseCase = fetchDataUseCase;
    }

    public void fetchDataFromApi() {
        fetchDataUseCase.execute(new DisposableSingleObserver<List<Repository>>() {
            @Override
            public void onSuccess(List<Repository> respons) {
                getNavigator().onResponseLoaded(respons);
            }

            @Override
            public void onError(Throwable e) {
                getNavigator().onError(e.getMessage());
            }
        }, FetchDataUseCase.Params.fetchData());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (fetchDataUseCase != null) {
            fetchDataUseCase.dispose();
        }
    }
}
