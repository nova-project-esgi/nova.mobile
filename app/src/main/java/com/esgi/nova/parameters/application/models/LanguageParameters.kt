package com.esgi.nova.parameters.application.models

import com.esgi.nova.languages.ports.IAppLanguage
import com.esgi.nova.parameters.ports.ILanguageParameters

data class LanguageParameters(
    override val selectedLanguage: IAppLanguage?,
    override val isDarkMode: Boolean,
    override val hasDailyEvents: Boolean,
    override val hasNotifications: Boolean,
    override val hasSound: Boolean
) : ILanguageParameters