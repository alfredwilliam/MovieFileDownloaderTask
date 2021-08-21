package com.alfred.moviefiledownloadertask.utils;

import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.alfred.moviefiledownloadertask.di.Component.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;


public class MyApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().Application(this).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LocaleManager.updateLanguage(this,LocaleManager.getLang());
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.updateLanguage(this,LocaleManager.getLang());
    }
}
