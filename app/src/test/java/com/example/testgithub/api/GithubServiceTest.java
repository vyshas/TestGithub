package com.example.testgithub.api;

import static com.example.testlastfm.util.LiveDataTestUtil.getValue;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


import com.example.testgithub.model.OrgRepos;
import com.example.testgithub.util.LiveDataCallAdapterFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RunWith(JUnit4.class)
public class GithubServiceTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private GithubService service;

    private MockWebServer mockWebServer;

    @Before
    public void createService() throws IOException {
        mockWebServer = new MockWebServer();
        service = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(GithubService.class);
    }


    @After
    public void stopService() throws IOException {
        mockWebServer.shutdown();
    }

    private void enqueueResponse(String fileName) throws IOException {
        enqueueResponse(fileName, Collections.<String, String>emptyMap());
    }

    private void enqueueResponse(String fileName, Map<String, String> headers) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("api-response/" + fileName);

        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        MockResponse mockResponse = new MockResponse();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            mockResponse.addHeader(header.getKey(), header.getValue());
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)));
    }

    @Test
    public void getOrgRepos() throws IOException,InterruptedException {
        enqueueResponse("repos-googlesamples.json");

        List<OrgRepos> orgRepos = getValue(service.getOrgRepos("googlesamples")).body;

        RecordedRequest request = mockWebServer.takeRequest();

        assertThat(request.getPath(), is("/orgs/googlesamples/repos"));

        OrgRepos repo = orgRepos.get(0);
        assertThat(repo.getFullName(), is("googlesamples/web-fundamentals"));

    }

}
