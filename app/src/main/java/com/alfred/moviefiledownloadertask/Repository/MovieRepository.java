package com.alfred.moviefiledownloadertask.Repository;

import com.alfred.moviefiledownloadertask.data.response.Movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieRepository {
    @Inject
    ApiInterface apiInterface;
    @Inject
    public MovieRepository() {
    }

    public Single<List<Movie>> getMovie(){
        return apiInterface.getAppVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}