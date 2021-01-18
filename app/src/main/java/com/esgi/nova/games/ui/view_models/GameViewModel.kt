package com.esgi.nova.games.ui.view_models

import androidx.lifecycle.ViewModel
import com.esgi.nova.events.ports.IDetailedEvent
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.ports.IResumedGameWithResourceIcons
import com.esgi.nova.games.ports.ITotalValueResource
import com.esgi.nova.ui.IViewModelState
import java.util.*

class GameViewModel(

) : ViewModel(), IResumedGameWithResourceIcons, IViewModelState {

    lateinit var event: IFileWrapper<IDetailedEvent>
    override lateinit var id: UUID
    override lateinit var resources: List<IFileWrapper<ITotalValueResource>>
    override var duration: Int = 0
    override var rounds: Int = 0
    var timer: Timer? = null


    fun copyGame(game: IResumedGameWithResourceIcons) {
        id = game.id
        resources = game.resources
        duration = game.duration
        rounds = game.rounds
    }

    override var initialized: Boolean = false
}