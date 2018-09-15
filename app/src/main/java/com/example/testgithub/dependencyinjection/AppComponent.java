package com.example.testgithub.dependencyinjection;

import android.app.Application;

import com.example.testgithub.TestGithub;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton @Component(modules = {
    AndroidInjectionModule.class, MainActivityModule.class, AppModule.class
}) public interface AppComponent {

  @Component.Builder interface Builder {

    @BindsInstance
    Builder application(Application application);

    AppComponent build();
  }

  void inject(TestGithub testGithub);
}
