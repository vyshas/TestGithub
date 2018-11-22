package com.example.testgithub;


import android.os.Bundle;

import com.example.testgithub.api.GithubService;
import com.example.testgithub.ui.common.NavigationController;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    GithubService githubService;

    @Inject
    NavigationController navigationController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            navigationController.navigateToOrgRepos();
        }


        /*   //FIXME: below code added to check inital setup of network calls using dagger injection.
         *//*githubService.getFollowers("vyshas").observe(this, userApiResponse ->
            Log.d("Main","response recieved:"+userApiResponse.body.get(0).login));*//*

    githubService.getOrgRepos("googlesamples").observe(this,userApiResponse->
            Log.d("Main","response recieved:"+userApiResponse.body.get(0).getFullName()));

*//*
    githubService.getUser("vyshas").observe(this, userApiResponse ->
            Log.d("Main","response recieved:"+userApiResponse.body.reposUrl));

            */
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
