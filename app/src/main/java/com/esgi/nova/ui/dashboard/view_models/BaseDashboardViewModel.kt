package com.esgi.nova.ui.dashboard.view_models

import androidx.lifecycle.LiveData
import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.dtos.difficulty.DetailedDifficultyDto
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.ui.AppViewModel

abstract class BaseDashboardViewModel : AppViewModel() {
    abstract val canLaunch: LiveData<Boolean>
    abstract val canResume: LiveData<Boolean>
    abstract val showParameterSaved: LiveData<Boolean>
    abstract val difficulties: LiveData<List<DetailedDifficultyDto>>
    abstract val selectedDifficulty: LiveData<DetailedDifficultyDto>
    abstract val wrapperResources: List<IFileWrapper<IDetailedDifficulty.IStartValueResource>>
    abstract val newResources: LiveData<List<IFileWrapper<IDetailedDifficulty.IStartValueResource>>>


    abstract fun selectDifficulty(difficultyDto: DetailedDifficultyDto)

    abstract fun setResourcesWrappers(wrappers: List<IFileWrapper<IDetailedDifficulty.IStartValueResource>>)
    abstract fun initialize(hasSavedParameters: Boolean)
}