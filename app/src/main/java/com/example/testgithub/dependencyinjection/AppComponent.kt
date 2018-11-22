package com.example.testgithub.dependencyinjection

import android.app.Application
import com.example.testgithub.TestGithub
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules =
        [AndroidInjectionModule::class,
            MainActivityModule::class,
            AppModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(testGithub: TestGithub)
}
