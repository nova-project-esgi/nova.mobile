package com.esgi.nova.parameters.application

import androidx.appcompat.app.AppCompatDelegate
import com.esgi.nova.parameters.infrastructure.storage.ParametersStorageRepository
import javax.inject.Inject

class SwitchTheme @Inject constructor(
    private val parameterStorageRepository: ParametersStorageRepository
) : ThemeUseCase() {

    fun execute(isDarkMode: Boolean) {
        parameterStorageRepository.switchTheme(isDarkMode)
        AppCompatDelegate.setDefaultNightMode(getThemeFlag(isDarkMode))
    }
}

