package com.alfred.moviefiledownloadertask.di.Module.VMs;



import com.alfred.moviefiledownloadertask.Repository.MovieRepository;

import dagger.Binds;
import dagger.Module;

@Module

public abstract class RepoModule {
    @Binds
    public abstract MovieRepository bindMovieRepository(MovieRepository MovieRepository);

}
