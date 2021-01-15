package com.esgi.nova.infrastructure.data

import com.esgi.nova.application.NovaApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

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

    @Provides
    fun  provideGameDao(db: AppDatabase) = db.gameDao()

    @Provides
    fun  provideGameEventDao(db: AppDatabase) = db.gameEventDao()


}