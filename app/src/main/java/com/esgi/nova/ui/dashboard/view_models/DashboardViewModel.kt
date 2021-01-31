package com.esgi.nova.ui.dashboard.view_models

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.esgi.nova.difficulties.application.GetAllDetailedDifficultiesSortedByRank
import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.dtos.difficulty.DetailedDifficultyDto
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.application.CreateGame
import com.esgi.nova.games.infrastructure.data.game.models.CanLaunchGame
import com.esgi.nova.games.infrastructure.data.game.models.CanResumeGame
import com.esgi.nova.resources.application.GetImageStartValueResourceWrappersByDifficultyId
import com.esgi.nova.utils.reflectMapCollection


class DashboardViewModel @ViewModelInject constructor(
    private val createGame: CreateGame,
    private val getAllDetailedDifficultiesSortedByRank: GetAllDetailedDifficultiesSortedByRank,
    private val getImageStartValueResourceWrappersByDifficultyId: GetImageStartValueResourceWrappersByDifficultyId,
    private val canLaunchGame: CanLaunchGame,
    private val canResumeGame: CanResumeGame,
) : BaseDashboardViewModel() {

    override val canLaunch: LiveData<Boolean>
        get() = _canLaunch
    private var _canLaunch = MutableLiveData<Boolean>()

    override val canResume: LiveData<Boolean>
        get() = _canResume
    private var _canResume = MutableLiveData<Boolean>()

    override val showParameterSaved: LiveData<Boolean>
        get() = _showParameterSaved
    private var _showParameterSaved = MutableLiveData<Boolean>()


    override val difficulties: LiveData<List<DetailedDifficultyDto>> get() = _difficulties
    private var _difficulties = MutableLiveData<List<DetailedDifficultyDto>>()

    override val selectedDifficulty: LiveData<DetailedDifficultyDto> get() = _selectedDifficulty
    private var _selectedDifficulty = MutableLiveData<DetailedDifficultyDto>()

    override val wrapperResources: List<IFileWrapper<IDetailedDifficulty.IStartValueResource>> get() = _wrapperResources
    private var _wrapperResources =
        mutableListOf<IFileWrapper<IDetailedDifficulty.IStartValueResource>>()

    override val newResources: LiveData<List<IFileWrapper<IDetailedDifficulty.IStartValueResource>>> get() = _newResources
    private var _newResources =
        MutableLiveData<List<IFileWrapper<IDetailedDifficulty.IStartValueResource>>>()

    override fun initialize(hasSavedParameters: Boolean) {

        initActionsAvailability()
        if (hasSavedParameters) {
            _showParameterSaved.value = hasSavedParameters
        }
        if (initialized) return


        initDifficulties()

        initialized = true
    }

    private fun initActionsAvailability() {
        loadingLaunch {
            _canLaunch.value = canLaunchGame.execute()
            _canResume.value = canResumeGame.execute()
        }
    }

    override fun selectDifficulty(difficultyDto: DetailedDifficultyDto) {
        _selectedDifficulty.value = difficultyDto
        loadingLaunch {
            _newResources.value =
                getImageStartValueResourceWrappersByDifficultyId.execute(difficultyDto.id)
        }
    }

    override fun setResourcesWrappers(wrappers: List<IFileWrapper<IDetailedDifficulty.IStartValueResource>>) {
        _wrapperResources.clear()
        _wrapperResources.addAll(wrappers)
    }

    private fun initDifficulties() {
        loadingLaunch {
            _difficulties.value = getAllDetailedDifficultiesSortedByRank
                .execute()
                .reflectMapCollection<IDetailedDifficulty, DetailedDifficultyDto>()
                .toMutableList()

            difficulties.value?.firstOrNull()?.let { difficulty ->
                selectDifficulty(difficulty)
            }
        }
    }
}