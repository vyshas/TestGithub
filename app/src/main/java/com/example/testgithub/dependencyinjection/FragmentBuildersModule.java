package com.example.testgithub.dependencyinjection;

import com.example.testgithub.ui.OrgReposFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract OrgReposFragment orgReposFragment();

}
