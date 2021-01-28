package com.esgi.nova.games.ui.game.view_models

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.esgi.nova.events.ports.IDetailedChoice
import com.esgi.nova.events.ports.IDetailedEvent
import com.esgi.nova.files.dtos.FileWrapperDto
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.application.*
import com.esgi.nova.games.ports.IRecappedGameWithResourceIcons
import com.esgi.nova.games.ports.ITotalValueResource
import com.esgi.nova.games.ui.game.models.RecappedGameWithResourceIcons
import com.esgi.nova.ui.AppViewModel
import com.esgi.nova.utils.reflectMap
import java.util.*

class GameViewModel @ViewModelInject constructor(

    private val getCurrentGame: GetCurrentGame,
    private val getNextEvent: GetNextEvent,
    private val getCurrentEvent: GetCurrentEvent,
    private val createGame: CreateGame,
    private val confirmChoice: ConfirmChoice,
    private val updateGame: UpdateGame,
) : AppViewModel() {

    val event: LiveData<IFileWrapper<IDetailedEvent>> get() = _event
    private var _event = MutableLiveData<IFileWrapper<IDetailedEvent>>()

    private var timer: Timer? = null

    val selectedChoice: LiveData<IDetailedChoice> get() = _selectedChoice
    private var _selectedChoice = MutableLiveData<IDetailedChoice>()

    val newChoices: LiveData<List<IDetailedChoice>> get() = _newChoices
    private var _newChoices = MutableLiveData<List<IDetailedChoice>>()

    val choices: List<IDetailedChoice> get() = _choices
    private var _choices = mutableListOf<IDetailedChoice>()

    private var _game: RecappedGameWithResourceIcons? = null

    val newResources: LiveData<List<IFileWrapper<ITotalValueResource>>> get() = _newResources
    private var _newResources = MutableLiveData<List<IFileWrapper<ITotalValueResource>>>()

    val resources: List<IFileWrapper<ITotalValueResource>> get() = _resources
    private var _resources = mutableListOf<IFileWrapper<ITotalValueResource>>()

    val gameDuration: LiveData<Int> get() = _gameDuration
    private var _gameDuration = MutableLiveData(0)

    val rounds: LiveData<Int> get() = _rounds
    private var _rounds = MutableLiveData(0)


    val gameEnded: LiveData<Boolean> get() = _gameEnded
    private var _gameEnded = MutableLiveData<Boolean>()

    val changeResources: LiveData<List<IFileWrapper<IDetailedChoice.IChangeValueResource>>> get() = _changeResources
    private var _changeResources =
        MutableLiveData<List<IFileWrapper<IDetailedChoice.IChangeValueResource>>>()


    fun select(item: IDetailedChoice?) {
        _selectedChoice.value = item
    }


    fun initialize(difficultyId: UUID?) {
        _selectedChoice.value = _selectedChoice.value
        if (initialized) return
        loadingLaunch {
            try {
                if (difficultyId != null) {
                    createGame(difficultyId)
                } else {
                    getCurrentGame()
                }
            } catch (e: Exception) {
                Log.e(GameViewModel::class.qualifiedName, e.message.toString())
                _unexpectedError.value = true
            }
        }
    }

    fun setGameResources(resources: List<IFileWrapper<ITotalValueResource>>) {
        _resources.clear()
        _resources.addAll(resources)
    }

    private suspend fun getCurrentGame() {

        getCurrentGame.execute()?.let { game ->
            copyGameProperties(game)
            getCurrentEvent.execute(game.id)?.let { event ->
                updateEvent(event)
                return
            }
            getNextEvent.execute(game.id)?.let { event ->
                updateEvent(event)
                return
            }
        }
    }

    private fun copyGameProperties(game: IRecappedGameWithResourceIcons) {
        _game = game.reflectMap()
        _newResources.value = game.resources
        _rounds.value = game.rounds
    }

    private fun updateEvent(event: IFileWrapper<IDetailedEvent>) {
        _event.value = event
        _newChoices.value = event.data.choices
    }

    fun updateChoices(choices: List<IDetailedChoice>) {
        _choices.clear()
        _choices.addAll(choices)
    }


    private suspend fun createGame(difficultyId: UUID) {
        createGame.execute(difficultyId)?.let { game ->
            copyGameProperties(game)
            getNextEvent.execute(game.id)?.let { event ->
                updateEvent(event)
            }
        }
    }

    private suspend fun nextRound() {
        getCurrentGame.execute()?.let { game ->
            copyGameProperties(game)
            getNextEvent.execute(game.id)?.let { event ->
                updateEvent(event)
                _selectedChoice.value = null
            }
        }
    }

    fun updateGame() {
        loadingLaunch {
            try {
                _game?.let {
                    updateGame.execute(game = it)
                }
            } catch (e: Exception) {
                Log.e(GameViewModel::class.qualifiedName, e.message.toString())
                _unexpectedError.value = true
            }
        }
    }

    fun stopTimer() {
        timer?.let { it ->
            it.cancel()
            it.purge()
            timer = null
        }
    }

    fun startTimer() {
        if (timer != null) {
            return
        }
        timer = Timer()
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                _game?.let { game ->
                    if (isLoading.value == false) {
                        game.duration++
                        _gameDuration.postValue(game.duration)
                    }

                }
            }
        }
        timer?.scheduleAtFixedRate(timerTask, 0, 1000)
    }


    private fun setChangesResources() {
        _selectedChoice.value?.let { choice ->
            _changeResources.value = choice.resources.mapNotNull { choiceResource ->
                _resources.firstOrNull { gameResource -> gameResource.data.id == choiceResource.id }
                    ?.let { gameResource ->
                        FileWrapperDto(choiceResource, gameResource.file)
                    }
            }
        }
    }

    fun confirmChoice() {
        loadingLaunch {
            try {
                _selectedChoice.value?.let { choice ->
                    _game?.let { game ->
                        val isEnded = confirmChoice.execute(
                            gameId = game.id,
                            choiceId = choice.id,
                            duration = game.duration
                        )
                        game.isEnded = isEnded
                        _gameEnded.value = isEnded
                        setChangesResources()
                        nextRound()
                    }
                }
            } catch (e: Exception) {
                Log.e(GameViewModel::class.qualifiedName, e.message.toString())
                _unexpectedError.value = true
            }

        }
    }

}