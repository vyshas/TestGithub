package com.example.testgithub.respository;


import static com.example.testgithub.ApiUtil.successCall;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.testgithub.InstantAppExecutors;
import com.example.testgithub.api.ApiResponse;
import com.example.testgithub.api.GithubService;
import com.example.testgithub.db.GithubDb;
import com.example.testgithub.db.OrgRepDao;
import com.example.testgithub.model.OrgRepos;
import com.example.testgithub.model.Resource;
import com.example.testgithub.repository.OrgReposRepository;
import com.example.testlastfm.util.TestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class RepoRepositoryTest {

    private OrgReposRepository repository;
    private OrgRepDao dao;
    private GithubService service;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void init() {
        dao = mock(OrgRepDao.class);
        service = mock(GithubService.class);
        GithubDb db = mock(GithubDb.class);
        when(db.orgRepDao()).thenReturn(dao);
        repository = new OrgReposRepository(service,new InstantAppExecutors(),dao);
    }

    @Test
    public void loadRepoFromNetwork() throws IOException {
        MutableLiveData<List<OrgRepos>> dbData = new MutableLiveData<>();
        when(dao.loadOrgRepositories()).thenReturn(dbData);

        LiveData<Resource<List<OrgRepos>>> resourceLiveData = repository.loadRepos("foo");
        verify(dao).loadOrgRepositories();

        OrgRepos repo = TestUtil.createOrgRepos("foo");

        List<OrgRepos> orgReposList = Collections.singletonList(repo);
        LiveData<ApiResponse<List<OrgRepos>>> call = successCall(orgReposList);
        when(service.getOrgRepos("foo")).thenReturn(call);

        Observer<Resource<List<OrgRepos>>> observer = mock(Observer.class);
        resourceLiveData.observeForever(observer);

        verify(observer).onChanged(Resource.loading( null));

        MutableLiveData<List<OrgRepos>> updatedDbData = new MutableLiveData<>();
        when(dao.loadOrgRepositories()).thenReturn(updatedDbData);
        dbData.setValue(Collections.emptyList());

        verify(service).getOrgRepos("foo");
        //Capture the arguments passed when inserting
        ArgumentCaptor<List<OrgRepos>> inserted = ArgumentCaptor.forClass((Class) List.class);
        verify(dao).insertOrgRepos(inserted.capture());
        assertThat(inserted.getValue().size(), is(1));
        OrgRepos first = inserted.getValue().get(0);
        assertThat(first.getName(), is("foo"));
        assertThat(first.getId(), is(1));


    }

}