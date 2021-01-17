package com.esgi.nova.application_state.storage.models

import com.esgi.nova.languages.ports.IAppLanguage
import com.esgi.nova.parameters.ports.IParameters

data class Parameters(
    override val isDarkMode: Boolean,
    override val hasDailyEvents: Boolean,
    override val hasNotifications: Boolean,
    override val hasMusic: Boolean
) : IParameters {
}