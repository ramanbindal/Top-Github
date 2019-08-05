package com.example.topGithub;

import android.app.Application;

import com.example.topGithub.di.AppModule;
import com.example.topGithub.di.DaggerMyComponent;
import com.example.topGithub.di.MyComponent;

public class MainApplication extends Application {

    private MyComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerMyComponent.builder().appModule(new AppModule(getApplicationContext())).build();
    }

    public MyComponent getComponent() {
        return this.component;
    }
}
