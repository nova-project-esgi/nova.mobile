package com.esgi.nova.application_state.storage.models

import com.esgi.nova.parameters.ports.IParameters

data class Parameters(
    override val isDarkMode: Boolean,
    override val hasDailyEvents: Boolean,
    override val hasNotifications: Boolean,
    override val hasSound: Boolean
) : IParameters