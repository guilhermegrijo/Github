package com.example.pc.github.ui.search;

import com.example.pc.github.domain.interactors.LoadRepositoriesUseCase;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SearchViewModelFactory implements ViewModelProvider.Factory {


    private final LoadRepositoriesUseCase loadRepositoriesUseCase;

    public SearchViewModelFactory(LoadRepositoriesUseCase loadRepositoriesUseCase) {
        this.loadRepositoriesUseCase = loadRepositoriesUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            return (T) new SearchViewModel(loadRepositoriesUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

