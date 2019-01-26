package com.example.testgithub.repository;


import com.example.testgithub.AppExecutors;
import com.example.testgithub.api.ApiResponse;
import com.example.testgithub.api.GithubService;
import com.example.testgithub.db.OrgRepDao;
import com.example.testgithub.model.OrgRepos;
import com.example.testgithub.model.Resource;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

@Singleton
public class OrgReposRepository {

    private final GithubService githubService;
    private final AppExecutors appExecutors;
    private final OrgRepDao orgRepDao;


    private static final int SYNC_INTERVAL_HOURS = 3;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;
    private static final String REPO_SYNC_TAG = "repo-sync";

    @Inject
    public OrgReposRepository(GithubService githubService, AppExecutors appExecutors, OrgRepDao orgRepDao) {
        this.githubService = githubService;
        this.appExecutors = appExecutors;
        this.orgRepDao = orgRepDao;
    }

    public LiveData<Resource<List<OrgRepos>>> loadRepos(String organisation, Boolean isFullRefresh) {

        return new NetworkBoundResource<List<OrgRepos>, List<OrgRepos>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull List<OrgRepos> item) {
                //save in DB
                orgRepDao.insertOrgRepos(item);

            }

            @Override
            protected boolean shouldFetch(@Nullable List<OrgRepos> data) {
                if (isFullRefresh) {
                    return true;
                }

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

    public LiveData<Resource<List<OrgRepos>>> loadRepos(String organisation) {
        return loadRepos(organisation, false);
    }

    public LiveData<Resource<List<OrgRepos>>> refreshOrgRepos(String organisation) {
        return loadRepos(organisation, true);

    }
    
}
