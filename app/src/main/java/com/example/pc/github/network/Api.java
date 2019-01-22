package com.example.pc.github.network;

import com.example.pc.github.domain.model.Result;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Api {
    @GET("search/repositories")
    Observable<Result> listRepos(@Query("q") String query, @Query("per_page") int size, @Query("page") int page, @Query("sort") String order);
}