package com.alfred.moviefiledownloadertask.Repository;

import com.alfred.moviefiledownloadertask.data.response.Movie;
import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("movies")
    Single<List<Movie>> getAppVersion();
}
