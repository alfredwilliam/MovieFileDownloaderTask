package com.alfred.moviefiledownloadertask.di.Module;

import com.alfred.moviefiledownloadertask.ui.mainActivity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeSplashActivity();

}
