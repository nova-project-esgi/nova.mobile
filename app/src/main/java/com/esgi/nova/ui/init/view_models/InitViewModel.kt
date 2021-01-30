package com.esgi.nova.ui.init.view_models

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.esgi.nova.application_state.application.SetSynchronizeState
import com.esgi.nova.difficulties.application.SynchronizeDifficulties
import com.esgi.nova.events.application.DeleteOrphansDailyEvents
import com.esgi.nova.events.application.SynchronizeEvents
import com.esgi.nova.games.application.SynchronizeLastActiveGame
import com.esgi.nova.infrastructure.api.exceptions.NoConnectionException
import com.esgi.nova.languages.application.SynchronizeLanguages
import com.esgi.nova.ports.Synchronize
import com.esgi.nova.resources.application.SynchronizeResources

class InitViewModel @ViewModelInject constructor(
    synchronizeEvents: SynchronizeEvents,
    synchronizeDifficulties: SynchronizeDifficulties,
    synchronizeLanguages: SynchronizeLanguages,
    synchronizeResources: SynchronizeResources,
    synchronizeLastActiveGame: SynchronizeLastActiveGame,
    deleteOrphansDailyEvents: DeleteOrphansDailyEvents,
    private val setSynchronizeState: SetSynchronizeState,
) : BaseInitViewModel() {


    override val currentInitStep: LiveData<Int>
        get() = _currentInitStep

    private var _currentInitStep = MutableLiveData(0)

    override val navigateToDashboard: LiveData<Boolean>
        get() = _navigateToDashboard
    private var _navigateToDashboard = MutableLiveData<Boolean>()

    override val networkError: LiveData<Boolean>
        get() = _networkError
    private var _networkError = MutableLiveData<Boolean>()

    private val stepsList: List<Synchronize> = listOf(
        synchronizeLanguages,
        synchronizeResources,
        synchronizeDifficulties,
        synchronizeEvents,
        synchronizeLastActiveGame,
        deleteOrphansDailyEvents
    )

    override fun loadContent() {
        if (initialized) return

        loadingLaunch {
            try {
                setSynchronizeState.execute(false)
                stepsList.forEach { sync ->
                    sync.execute()
                    _currentInitStep.value = _currentInitStep.value?.plus(1)
                }
                _currentInitStep.value = _currentInitStep.value?.plus(1)
                setSynchronizeState.execute(true)
                _navigateToDashboard.value = true
            } catch (e: NoConnectionException) {
                _networkError.value = true
            } catch (e: Exception) {
                _unexpectedError.value = true
            }
        }
        initialized = true
    }


}