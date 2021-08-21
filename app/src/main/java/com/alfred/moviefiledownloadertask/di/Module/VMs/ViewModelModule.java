package com.alfred.moviefiledownloadertask.di.Module.VMs;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alfred.moviefiledownloadertask.di.ViewModelKey;
import com.alfred.moviefiledownloadertask.ui.mainActivity.MainActivityViewModel;
import com.alfred.moviefiledownloadertask.utils.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelFactory);

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel.class)
    public abstract ViewModel bindMainActivityViewModel (MainActivityViewModel loginViewModel);

}
