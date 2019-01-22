package com.example.pc.github.domain.interactors;

import com.example.pc.github.domain.Repository;
import com.example.pc.github.domain.model.Result;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LoadRepositoriesUseCase {


    private final Repository repository;

    @Inject
    public LoadRepositoriesUseCase(Repository repository) {
        this.repository = repository;
    }


    public Observable<Result> execute(String search, int page) {
        return repository.getRepos(search, page);
    }
}
