package com.esgi.nova.infrastructure.data

import com.esgi.nova.NovaApplication
import com.esgi.nova.difficulties.infrastructure.data.difficulty.DifficultyDAO
import com.esgi.nova.infrastructure.data.dao.BaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import kotlin.reflect.full.*

@Module
@InstallIn(ApplicationComponent::class)
object AppDatabaseModule {

    @Provides
    fun provideAppDatabase(
    ): AppDatabase {
        return AppDatabase.getAppDataBase(NovaApplication.getContext())!!
    }

    @Provides
    fun  provideDifficultyDao(db: AppDatabase) = db.difficultyDAO()
    @Provides
    fun  provideChoiceDao(db: AppDatabase) = db.choiceDAO()
    @Provides
    fun  provideChoiceResourceDao(db: AppDatabase) = db.choiceResourceDAO()
    @Provides
    fun  provideDifficultyResourceDao(db: AppDatabase) = db.difficultyResourceDAO()
    @Provides
    fun  provideResourceDao(db: AppDatabase) = db.resourceDAO()
    @Provides
    fun  provideEventDao(db: AppDatabase) = db.eventDAO()
    @Provides
    fun  provideLanguageDao(db: AppDatabase) = db.languageDAO()

}