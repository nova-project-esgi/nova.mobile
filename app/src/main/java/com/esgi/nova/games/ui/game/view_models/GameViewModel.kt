package com.esgi.nova.games.ui.game.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esgi.nova.events.ports.IDetailedEvent
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.ports.IRecappedGameWithResourceIcons
import com.esgi.nova.games.ports.ITotalValueResource
import com.esgi.nova.ui.IAppViewModel
import java.util.*

class GameViewModel : ViewModel(), IRecappedGameWithResourceIcons, IAppViewModel {

    lateinit var event: IFileWrapper<IDetailedEvent>
    override lateinit var difficultyId: UUID
    override var isEnded: Boolean = false
    override lateinit var userId: UUID
    override lateinit var id: UUID
    override lateinit var resources: List<IFileWrapper<ITotalValueResource>>
    override var duration: Int = 0
    override var rounds: Int = 0
    var timer: Timer? = null
    val isLoading = MutableLiveData<Boolean>()

    fun setLoading(isLoading: Boolean) {
        this.isLoading.value = isLoading
    }


    fun copyGame(game: IRecappedGameWithResourceIcons) {
        id = game.id
        resources = game.resources
        duration = game.duration
        rounds = game.rounds
        difficultyId = game.difficultyId
        userId = game.userId
        isEnded = game.isEnded
    }

    override var initialized: Boolean = false
    override val unexpectedError: LiveData<Boolean>
        get() = _unexpectedError

    private var _unexpectedError =  MutableLiveData<Boolean>()
}