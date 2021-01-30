package com.esgi.nova.games.ui.endgame.view_models

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.application.GetLastEndedGame
import com.esgi.nova.games.ports.ITotalValueResource
import com.esgi.nova.ui.AppViewModel

class EndGameViewModel @ViewModelInject constructor(
    private val getLastEndedGame: GetLastEndedGame,
) : AppViewModel() {


    val loosingResource: LiveData<String>
        get() = _loosingResource
    private var _loosingResource = MutableLiveData<String>()

    val newResources: LiveData<List<IFileWrapper<ITotalValueResource>>> get() = _newResources
    private var _newResources = MutableLiveData<List<IFileWrapper<ITotalValueResource>>>()

    val rounds: LiveData<Int> get() = _rounds
    private var _rounds = MutableLiveData(0)

    val resources get() = _resources
    private var _resources = mutableListOf<IFileWrapper<ITotalValueResource>>()

    fun initialize() {
        if (initialized) return
        loadingLaunch {
            try {
                getLastEndedGame.execute()?.let { game ->
                    _rounds.value = game.rounds
                    _newResources.value = game.resources
                }
            } catch (e: Exception) {
                _unexpectedError.value = true
            }

        }
        initialized = true
    }

    fun setResources(resources: List<IFileWrapper<ITotalValueResource>>) {
        _loosingResource.value =
            resources.firstOrNull { resource -> resource.data.total == 0 }?.data?.name
        _resources.clear()
        _resources.addAll(resources)
    }

}