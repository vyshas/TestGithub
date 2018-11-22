
package com.example.testgithub.dependencyinjection;


import android.app.Application;


import com.example.testgithub.api.GithubService;
import com.example.testgithub.db.GithubDb;
import com.example.testgithub.db.OrgRepDao;
import com.example.testgithub.ui.common.ViewModelModule;
import com.example.testgithub.util.LiveDataCallAdapterFactory;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module(includes = ViewModelModule.class)
class AppModule {

    @Singleton
    @Provides
    GithubService provideGithubService(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(okHttpClient)
                .build()
                .create(GithubService.class);
    }


    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(StethoInterceptor stethoInterceptor) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addNetworkInterceptor(stethoInterceptor)
                .writeTimeout(10, TimeUnit.MINUTES)
                .build();

        return okHttpClient;
    }

    @Singleton
    @Provides
    GithubDb provideDb(Application app) {
        return Room.databaseBuilder(app, GithubDb.class,"github.db").build();
    }

    @Singleton
    @Provides
    OrgRepDao provideOrgRepoDao(GithubDb db) {
        return db.orgRepDao();
    }

    @Singleton
    @Provides
    public StethoInterceptor provideStethoInterceptor() {
        return new StethoInterceptor();
    }



}
