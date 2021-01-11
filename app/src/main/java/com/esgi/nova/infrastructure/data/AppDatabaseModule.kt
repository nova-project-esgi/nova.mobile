package com.esgi.nova.infrastructure.data

import com.esgi.nova.NovaApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object AppDatabaseModule {

    @Provides
    fun provideAppDatabase(
    ): AppDatabase {
        return AppDatabase.getAppDataBase(NovaApplication.getContext())!!
    }
}