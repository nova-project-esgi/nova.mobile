package com.esgi.nova.ui.init

import com.esgi.nova.application_state.application.SetSynchronizeState
import com.esgi.nova.difficulties.application.SynchronizeDifficulties
import com.esgi.nova.events.application.DeleteOrphansDailyEvents
import com.esgi.nova.events.application.SynchronizeEvents
import com.esgi.nova.games.application.SynchronizeLastActiveGame
import com.esgi.nova.languages.application.SynchronizeLanguages
import com.esgi.nova.resources.application.SynchronizeResources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class InitSetupActivityModule {

    @Provides
    fun provideCalculatorViewModelFactory(
        synchronizeEvents: SynchronizeEvents,
        synchronizeDifficulties: SynchronizeDifficulties,
        synchronizeLanguages: SynchronizeLanguages,
        synchronizeResources: SynchronizeResources,
        synchronizeLastActiveGame: SynchronizeLastActiveGame,
        deleteOrphansDailyEvents: DeleteOrphansDailyEvents,
        setSynchronizeState: SetSynchronizeState,
    ): IInitViewModelFactory =
        InitViewModelFactory(
            synchronizeEvents = synchronizeEvents,
            synchronizeDifficulties = synchronizeDifficulties,
            synchronizeLanguages = synchronizeLanguages,
            synchronizeResources = synchronizeResources,
            synchronizeLastActiveGame = synchronizeLastActiveGame,
            deleteOrphansDailyEvents = deleteOrphansDailyEvents,
            setSynchronizeState = setSynchronizeState
        )
}