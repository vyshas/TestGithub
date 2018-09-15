package com.example.testgithub.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.testgithub.AppExecutors;
import com.example.testgithub.api.ApiResponse;
import com.example.testgithub.api.GithubService;
import com.example.testgithub.db.OrgRepDao;
import com.example.testgithub.model.OrgRepos;
import com.example.testgithub.model.Resource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OrgReposRepository {


    private final GithubService githubService;
    private final AppExecutors appExecutors;
    private final OrgRepDao orgRepDao;

    @Inject
    public OrgReposRepository(GithubService githubService, AppExecutors appExecutors, OrgRepDao orgRepDao) {
        this.githubService = githubService;
        this.appExecutors = appExecutors;
        this.orgRepDao = orgRepDao;
    }

    public LiveData<Resource<List<OrgRepos>>> loadRepos(String organisation) {

        return new NetworkBoundResource<List<OrgRepos>, List<OrgRepos>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull List<OrgRepos> item) {
                //save in DB
                orgRepDao.insertOrgRepos(item);

            }

            @Override
            protected boolean shouldFetch(@Nullable List<OrgRepos> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<OrgRepos>> loadFromDb() {
                return orgRepDao.loadOrgRepositories();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<OrgRepos>>> createCall() {
                return githubService.getOrgRepos(organisation);
            }
        }.asLiveData();

    }
}
