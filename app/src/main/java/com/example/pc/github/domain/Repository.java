package com.example.pc.github.domain;

import com.example.pc.github.domain.model.Result;
import com.example.pc.github.network.Api;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class Repository {

    private final Api apiCallInterface;


    @Inject
    Repository(Api apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }

    public Observable<Result> getRepos(String search, int page) {
        return apiCallInterface.listRepos(search, 15, page, "stars");
    }

}