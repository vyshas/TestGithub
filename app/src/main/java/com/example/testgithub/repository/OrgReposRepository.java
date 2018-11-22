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

    /**
     * Schedules a repeating job service.
     */
  /*  public void scheduleRecurringSync(Context context) {
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        // Create the Job to periodically sync Sunshine
        Job syncSunshineJob = dispatcher.newJobBuilder()
                                        *//* The Service that will be used to sync  data *//*
                                        .setService(ReposService.class)
                                        *//* Set the UNIQUE tag used to identify this Job *//*
                                        .setTag(REPO_SYNC_TAG)
                                        *//*
                                         * Network constraints on which this Job should run. We choose to run on any
                                         * network, but you can also choose to run only on un-metered networks or when the
                                         * device is charging. It might be a good idea to include a preference for this,
                                         * as some users may not want to download any data on their mobile plan. ($$$)
                                         *//*
                                        .setConstraints(Constraint.ON_ANY_NETWORK)
                                        *//*
                                         * setLifetime sets how long this job should persist. The options are to keep the
                                         * Job "forever" or to have it die the next time the device boots up.
                                         *//*
                                        .setLifetime(Lifetime.FOREVER)
                                        *//*
                                         * We want  data to stay up to date, so we tell this Job to recur.
                                         *//*
                                        .setRecurring(true)
                                        *//*
                                         * We want the data to be synced every 3 to 4 hours. The first argument for
                                         * Trigger's static executionWindow method is the start of the time frame when the
                                         * sync should be performed. The second argument is the latest point in time at
                                         * which the data should be synced. Please note that this end time is not
                                         * guaranteed, but is more of a guideline for FirebaseJobDispatcher to go off of.
                                         *//*
                                        .setTrigger(Trigger.executionWindow(
                                                SYNC_INTERVAL_SECONDS,
                                                SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                                        *//*
                                         * If a Job with the tag with provided already exists, this new job will replace
                                         * the old one.
                                         *//*
                                        .setReplaceCurrent(true)
                                        *//* Once the Job is ready, call the builder's build method to return the Job *//*
                                        .build();

        // Schedule the Job with the dispatcher
        dispatcher.schedule(syncSunshineJob);
        Timber.d("Job scheduled");
    }*/



}
