package com.esgi.nova.ui.dashboard.view_models

import androidx.lifecycle.ViewModel
import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.dtos.difficulty.DetailedDifficultyDto
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.ui.IViewModelState

class DashboardViewModel() : ViewModel(), IViewModelState {
    override var initialized: Boolean = false

    var difficulties: List<DetailedDifficultyDto> = listOf()

    var selectedDifficulty: DetailedDifficultyDto? = null

    var wrapperResources =
        mutableListOf<IFileWrapper<IDetailedDifficulty.IStartValueResource>>()
}