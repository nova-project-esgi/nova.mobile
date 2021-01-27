package com.esgi.nova.ui.init.view_models

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.esgi.nova.application_state.application.IsSynchronized
import com.esgi.nova.application_state.application.SetSynchronizeState
import com.esgi.nova.difficulties.application.SynchronizeDifficulties
import com.esgi.nova.events.application.DeleteOrphansDailyEvents
import com.esgi.nova.events.application.SynchronizeEvents
import com.esgi.nova.games.application.SynchronizeLastActiveGame
import com.esgi.nova.languages.application.SynchronizeLanguages
import com.esgi.nova.ports.Synchronize
import com.esgi.nova.resources.application.SynchronizeResources
import com.esgi.nova.ui.AppViewModel
import kotlinx.coroutines.launch

class InitViewModel @ViewModelInject constructor(
    synchronizeEvents: SynchronizeEvents,
    synchronizeDifficulties: SynchronizeDifficulties,
    synchronizeLanguages: SynchronizeLanguages,
    synchronizeResources: SynchronizeResources,
    synchronizeLastActiveGame: SynchronizeLastActiveGame,
    deleteOrphansDailyEvents: DeleteOrphansDailyEvents,
    private val isSynchronized: IsSynchronized,
    private val setSynchronizeState: SetSynchronizeState,
    @Assisted private val savedStateHandle: SavedStateHandle
) : AppViewModel() {


    val currentInitStep: LiveData<Int>
        get() = _currentInitStep

    private var _currentInitStep = MutableLiveData<Int>()

    val navigateToDashboard: LiveData<Boolean>
        get() = _navigateToDashboard
    private var _navigateToDashboard = MutableLiveData<Boolean>()

    private val stepsList: List<Synchronize> = listOf(
        synchronizeLanguages,
        synchronizeResources,
        synchronizeDifficulties,
        synchronizeEvents,
        synchronizeLastActiveGame,
        deleteOrphansDailyEvents
    )

    fun loadContent() {
        if (initialized) return

        viewModelScope.launch {
            stepsList.forEach { sync ->
                sync.execute()
                _currentInitStep.value = _currentInitStep.value?.plus(1)
            }
            _currentInitStep.value = _currentInitStep.value?.plus(1)
            setSynchronizeState.execute(true)
            _navigateToDashboard.value = true
        }
        initialized = true
//
//        runOnUiThread { setLoadingText(viewModel.currentStep) }
//
//        DashboardActivity.start(this@InitSetupActivity)
//        finish()
    }


}