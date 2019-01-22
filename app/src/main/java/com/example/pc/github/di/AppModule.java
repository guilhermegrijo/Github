package com.example.pc.github.di;

import com.example.pc.github.domain.interactors.LoadRepositoriesUseCase;
import com.example.pc.github.network.Api;
import com.example.pc.github.ui.search.SearchViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Provides
    @Singleton
    Api provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Api.class);
    }

    @Provides
    SearchViewModelFactory provideSearchViewModelFactory(LoadRepositoriesUseCase loadRepositoriesUseCase) {
        return new SearchViewModelFactory(loadRepositoriesUseCase);
    }
}
