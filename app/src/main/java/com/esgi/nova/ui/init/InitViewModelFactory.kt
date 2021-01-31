package com.esgi.nova.ui.init

import androidx.lifecycle.ViewModel
import com.esgi.nova.application_state.application.SetSynchronizeState
import com.esgi.nova.difficulties.application.SynchronizeDifficulties
import com.esgi.nova.events.application.DeleteOrphansDailyEvents
import com.esgi.nova.events.application.SynchronizeEvents
import com.esgi.nova.games.application.SynchronizeLastActiveGame
import com.esgi.nova.languages.application.SynchronizeLanguages
import com.esgi.nova.resources.application.SynchronizeResources
import com.esgi.nova.ui.init.view_models.InitViewModel

@Suppress("UNCHECKED_CAST")
class InitViewModelFactory(
    private val synchronizeEvents: SynchronizeEvents,
    private val synchronizeDifficulties: SynchronizeDifficulties,
    private val synchronizeLanguages: SynchronizeLanguages,
    private val synchronizeResources: SynchronizeResources,
    private val synchronizeLastActiveGame: SynchronizeLastActiveGame,
    private val deleteOrphansDailyEvents: DeleteOrphansDailyEvents,
    private val setSynchronizeState: SetSynchronizeState,
) : IInitViewModelFactory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return InitViewModel(
            synchronizeEvents = synchronizeEvents,
            synchronizeDifficulties = synchronizeDifficulties,
            synchronizeLanguages = synchronizeLanguages,
            synchronizeResources = synchronizeResources,
            synchronizeLastActiveGame = synchronizeLastActiveGame,
            deleteOrphansDailyEvents = deleteOrphansDailyEvents,
            setSynchronizeState = setSynchronizeState
        ) as T
    }
}