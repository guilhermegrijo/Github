package com.example.pc.github.di;

import com.example.pc.github.ui.search.SearchFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract SearchFragment bindSearchFragment();
}
