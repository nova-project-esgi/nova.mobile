package com.esgi.nova.parameters.application

import androidx.appcompat.app.AppCompatDelegate

abstract class ThemeUseCase {
    protected fun getThemeFlag(isDarkMode: Boolean) =
        if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO

}