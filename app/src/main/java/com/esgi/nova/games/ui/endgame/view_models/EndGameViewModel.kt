package com.esgi.nova.games.ui.endgame.view_models

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esgi.nova.games.application.GetLastEndedGame
import com.esgi.nova.games.ports.IRecappedGameWithResourceIcons
import com.esgi.nova.ui.AppViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class EndGameViewModel @ViewModelInject constructor(
    private val getLastEndedGame: GetLastEndedGame,
    ) :  AppViewModel() {


    val recappedGame: LiveData<IRecappedGameWithResourceIcons>
        get() = _recappedGame

    private var _recappedGame = MutableLiveData<IRecappedGameWithResourceIcons>()

    val loosingResource: String?
        get() = resources?.firstOrNull { resource -> resource.data.total == 0 }?.data?.name

    var rounds = recappedGame.value?.rounds
    var resources = recappedGame.value?.resources


    fun initialize() {
        if (initialized) return
        setLoading()
        viewModelScope.launch {
            getLastEndedGame.execute()?.let { game ->
                _recappedGame.value = game
            }
            unsetLoading()
        }
        initialized = true
    }


}