package com.esgi.nova.games.ui.endgame.view_models

import androidx.lifecycle.ViewModel
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.ports.IRecappedGameWithResourceIcons
import com.esgi.nova.games.ports.ITotalValueResource
import com.esgi.nova.ui.IViewModelState

class EndGameViewModel : ViewModel(), IViewModelState {

    override var initialized: Boolean = false

    var resources: List<IFileWrapper<ITotalValueResource>> = listOf()
    var rounds: Int = 0

    fun populate(iRecappedGameWithResourceIcons: IRecappedGameWithResourceIcons) {
        resources = iRecappedGameWithResourceIcons.resources
        rounds = iRecappedGameWithResourceIcons.rounds
        initialized = true
    }

}