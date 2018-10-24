package com.example.testgithub;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.testgithub.dependencyinjection.AppInjector;
import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class TestGithub extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;


    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
        AppInjector.init(this);


    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }


}
