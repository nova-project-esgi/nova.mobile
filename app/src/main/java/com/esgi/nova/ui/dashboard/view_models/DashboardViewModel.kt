package com.esgi.nova.ui.dashboard.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.dtos.difficulty.DetailedDifficultyDto
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.ui.IAppViewModel

class DashboardViewModel : ViewModel(), IAppViewModel {
    override var initialized: Boolean = false

    override val unexpectedError: LiveData<Boolean>
        get() = _unexpectedError

    private var _unexpectedError =  MutableLiveData<Boolean>()

    var difficulties: List<DetailedDifficultyDto> = listOf()

    var selectedDifficulty: DetailedDifficultyDto? = null

    var wrapperResources =
        mutableListOf<IFileWrapper<IDetailedDifficulty.IStartValueResource>>()
}