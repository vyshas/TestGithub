package com.example.testgithub.api;

import android.arch.lifecycle.LiveData;

import com.example.testgithub.model.Followers;
import com.example.testgithub.model.OrgRepos;
import com.example.testgithub.model.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * REST API access points
 */
public interface GithubService {

    @GET("users/{login}")
    LiveData<ApiResponse<User>> getUser(@Path("login") String login);

    @GET("users/{login}/followers")
    LiveData<ApiResponse<List<Followers>>> getFollowers(@Path("login") String login);

    @GET("orgs/{organisation}/repos")
    LiveData<ApiResponse<List<OrgRepos>>> getOrgRepos(@Path("organisation") String organisation);


}

