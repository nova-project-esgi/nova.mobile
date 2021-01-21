package com.esgi.nova.parameters.ui.view_models

import androidx.lifecycle.ViewModel
import com.esgi.nova.dtos.languages.AppLanguageDto
import com.esgi.nova.parameters.ports.IParameters
import com.esgi.nova.ui.IViewModelState

class ParametersViewModel(

) : ViewModel(), IParameters, IViewModelState {
    override var isDarkMode: Boolean = false
    override var hasDailyEvents: Boolean = false
    override var hasNotifications: Boolean = false
    override var hasSound: Boolean = false
    override var initialized: Boolean = false
    var languages: List<AppLanguageDto> = listOf()
    var selectedLanguage: AppLanguageDto? = null

    fun copyParameters(params: IParameters){
        isDarkMode = params.isDarkMode
        hasDailyEvents = params.hasDailyEvents
        hasNotifications = params.hasNotifications
        hasSound = params.hasSound
    }
}