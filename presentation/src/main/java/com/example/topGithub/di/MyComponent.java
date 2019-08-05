package com.example.topGithub.di;


import com.example.topGithub.ui.di.MainModule;
import com.example.topGithub.ui.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, UseCaseModule.class, RepositoryModule.class, MainModule.class})
public interface MyComponent {

    void inject(MainActivity mainActivity);


}
