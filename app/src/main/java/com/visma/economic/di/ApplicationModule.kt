package com.visma.economic.di

import android.app.Application
import com.visma.economic.MainApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Named("application")
    fun provideApplication(): Application {
        return MainApplication.application
    }
}
