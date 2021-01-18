package com.esgi.nova.parameters.application

import androidx.appcompat.app.AppCompatDelegate
import com.esgi.nova.parameters.infrastructure.storage.ParametersStorageRepository
import javax.inject.Inject

class SetCurrentTheme @Inject constructor(
    private val parameterStorageRepository: ParametersStorageRepository
): ThemeUseCase() {

    fun execute(){
        AppCompatDelegate.setDefaultNightMode(
            getThemeFlag(
                parameterStorageRepository.get().isDarkMode
            )
        )
    }
}