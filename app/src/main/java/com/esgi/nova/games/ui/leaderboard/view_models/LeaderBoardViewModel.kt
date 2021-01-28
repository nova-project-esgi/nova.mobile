package com.esgi.nova.games.ui.leaderboard.view_models

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.esgi.nova.difficulties.application.GetAllDetailedDifficultiesSortedByRank
import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.dtos.difficulty.DetailedDifficultyDto
import com.esgi.nova.games.application.GetLeaderBoardGamePageCursor
import com.esgi.nova.games.ports.ILeaderBoardGameView
import com.esgi.nova.infrastructure.api.exceptions.NoConnectionException
import com.esgi.nova.infrastructure.api.pagination.PageCursor
import com.esgi.nova.ui.AppViewModel
import com.esgi.nova.utils.reflectMapCollection

class LeaderBoardViewModel @ViewModelInject constructor(
    private val getLeaderBoardPageCursor: GetLeaderBoardGamePageCursor,
    private val getAllDetailedDifficultiesSortedByRank: GetAllDetailedDifficultiesSortedByRank,
) : AppViewModel() {


    val difficulties: LiveData<List<DetailedDifficultyDto>> get() = _difficulties
    private var _difficulties = MutableLiveData<List<DetailedDifficultyDto>>()

    val networkError: LiveData<Boolean> get() = _networkError
    private var _networkError = MutableLiveData<Boolean>()


    val selectedDifficulty: LiveData<DetailedDifficultyDto> get() = _selectedDifficulty
    private var _selectedDifficulty = MutableLiveData<DetailedDifficultyDto>()

    val noMoreGames: LiveData<Boolean> get() = _noMoreGames
    private var _noMoreGames = MutableLiveData<Boolean>()


    val newScores: LiveData<List<ILeaderBoardGameView>> get() = _newScores
    var _newScores = MutableLiveData<List<ILeaderBoardGameView>>()

    val emptyScores: LiveData<Boolean> get() = _emptyScores
    var _emptyScores = MutableLiveData<Boolean>()

    val scores: Set<ILeaderBoardGameView> get() = _cursor

    private val _cursor = PageCursor<ILeaderBoardGameView>(null) { game1, game2 ->
        val res = game2.eventCount.compareTo(game1.eventCount)
        if (res == 0) {
            return@PageCursor game1.id.compareTo(game2.id)
        }
        return@PageCursor res
    }


    fun initialize() {
        if (initialized) return
        setDifficulties()
    }

    private fun setDifficulties() {
        loadingLaunch {
            try {
                val difficulties = getAllDetailedDifficultiesSortedByRank.execute()
                    .reflectMapCollection<IDetailedDifficulty, DetailedDifficultyDto>()
                    .toList()
                _difficulties.value = difficulties
                _selectedDifficulty.value = difficulties.firstOrNull()
                loadScores()
            } catch (e: Exception) {
                _unexpectedError.value = true
            }
        }

    }

    fun loadScores() {
        loadingLaunch {
            try {
                selectedDifficulty.value?.let { difficulty ->
                    val cursor = getLeaderBoardPageCursor.execute(
                        difficulty.id
                    )
                    _cursor.copy(cursor)
                    _newScores.value = _cursor.loadCurrent().toList()
                }
            } catch (e: NoConnectionException) {
                _networkError.value = true
            } catch (e: Exception) {
                _unexpectedError.value = true
            }

        }
    }

    fun selectDifficulty(difficulty: DetailedDifficultyDto) {
        _selectedDifficulty.value = difficulty
        loadScores()
    }

    fun setScores(scores: List<ILeaderBoardGameView>) {
        _cursor.addAll(scores)
        _emptyScores.value = _cursor.isEmpty()
    }

    fun loadMore(lastItemPosition: Int) {
        loadingLaunch {
            try {
                if (lastItemPosition == _cursor.size - 1) {
                    if (_cursor.hasNext == true) {
                        _newScores.value = _cursor.loadNext().toList()
                    } else if (_cursor.isNotEmpty()) {
                        _noMoreGames.value = true
                    }
                }
            } catch (e: NoConnectionException) {
                _networkError.value = true
            } catch (e: Exception) {
                _unexpectedError.value = true
            }

        }
    }

}