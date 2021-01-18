package com.esgi.nova.games.ui.view_models

import androidx.lifecycle.ViewModel
import com.esgi.nova.events.ports.IDetailedEvent
import com.esgi.nova.files.application.model.FileWrapper
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.ports.IResumedGame
import com.esgi.nova.games.ports.ITotalValueResource
import com.esgi.nova.ui.IViewModelState
import java.util.*

class GameViewModel(

) : ViewModel(), IResumedGame, IViewModelState {

    lateinit var event: IFileWrapper<IDetailedEvent>
    override lateinit var id: UUID
    override lateinit var resources: MutableList<ITotalValueResource>
    override var duration: Int = 0
    override var rounds: Int = 0

    fun copyGame(game: IResumedGame){
        id = game.id
        resources = game.resources
        duration = game.duration
        rounds = game.rounds
    }

    override var initialized: Boolean = false
}