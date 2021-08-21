package com.alfred.moviefiledownloadertask.di.Component;

import com.alfred.moviefiledownloadertask.di.Module.ActivityBuilderModule;
import com.alfred.moviefiledownloadertask.di.Module.RetrofitModule;
import com.alfred.moviefiledownloadertask.di.Module.VMs.ViewModelModule;
import com.alfred.moviefiledownloadertask.utils.MyApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBuilderModule.class,
        RetrofitModule.class,
        ViewModelModule.class
})
public interface ApplicationComponent extends AndroidInjector<MyApplication> {



    @Component.Builder
     interface Builder{
        @BindsInstance
        Builder Application(MyApplication application);

        ApplicationComponent build();
    }
}
