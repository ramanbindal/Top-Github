package com.example.topGithub.ui.di;

import android.arch.lifecycle.ViewModelProvider;

import com.example.topGithub.base.ViewModelProviderFactory;
import com.example.topGithub.ui.main.MainViewModel;
import com.example.domain.usecases.FetchDataUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Provides
    MainViewModel provideMainViewModel(FetchDataUseCase fetchDataUseCase) {
        return new MainViewModel(fetchDataUseCase);
    }

    @Provides
    ViewModelProvider.Factory mainViewModelProvider(MainViewModel mainViewModel) {
        return new ViewModelProviderFactory<>(mainViewModel);
    }

   }

