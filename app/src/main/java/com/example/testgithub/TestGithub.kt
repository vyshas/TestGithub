package com.example.testgithub

import android.app.Activity
import android.app.Application

import com.example.testgithub.dependencyinjection.AppInjector
import com.facebook.stetho.Stetho

import javax.inject.Inject

import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector

class TestGithub : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
        AppInjector.init(this)


    }

    override fun activityInjector() = dispatchingAndroidInjector

}
