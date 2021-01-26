package com.esgi.nova.parameters.ui.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esgi.nova.dtos.languages.AppLanguageDto
import com.esgi.nova.parameters.ports.IParameters
import com.esgi.nova.ui.IAppViewModel

class ParametersViewModel : ViewModel(), IParameters, IAppViewModel {
    override var isDarkMode: Boolean = false
    override var hasDailyEvents: Boolean = false
    override var hasNotifications: Boolean = false
    override var hasSound: Boolean = false
    override var initialized: Boolean = false

    override val unexpectedError: LiveData<Boolean>
        get() = _unexpectedError

    private var _unexpectedError =  MutableLiveData<Boolean>()
    var languages: List<AppLanguageDto> = listOf()
    var selectedLanguage: AppLanguageDto? = null

    fun copyParameters(params: IParameters){
        isDarkMode = params.isDarkMode
        hasDailyEvents = params.hasDailyEvents
        hasNotifications = params.hasNotifications
        hasSound = params.hasSound
    }
}