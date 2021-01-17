package com.esgi.nova.parameters.application

import com.esgi.nova.languages.ports.IAppLanguage
import com.esgi.nova.parameters.application.models.LanguageParameters
import com.esgi.nova.parameters.ports.ILanguageParameters
import com.esgi.nova.parameters.ports.IParameters

fun IParameters.toLanguageParameters(appLanguage: IAppLanguage?): ILanguageParameters = LanguageParameters(
    selectedLanguage = appLanguage,
    hasNotifications = this.hasNotifications,
    hasMusic = this.hasMusic,
    hasDailyEvents = this.hasDailyEvents,
    isDarkMode = this.isDarkMode
)