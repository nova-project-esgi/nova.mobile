package com.esgi.nova.games.ui.endgame.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.ports.IRecappedGameWithResourceIcons
import com.esgi.nova.games.ports.ITotalValueResource
import com.esgi.nova.ui.IAppViewModel

class EndGameViewModel : ViewModel(), IAppViewModel {

    override var initialized: Boolean = false

    override val unexpectedError: LiveData<Boolean>
        get() = _unexpectedError

    private var _unexpectedError =  MutableLiveData<Boolean>()

    var resources: List<IFileWrapper<ITotalValueResource>> = listOf()
    var rounds: Int = 0

    fun populate(iRecappedGameWithResourceIcons: IRecappedGameWithResourceIcons) {
        resources = iRecappedGameWithResourceIcons.resources
        rounds = iRecappedGameWithResourceIcons.rounds
        initialized = true
    }

}