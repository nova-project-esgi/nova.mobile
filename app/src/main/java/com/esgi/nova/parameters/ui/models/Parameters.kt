package com.esgi.nova.parameters.ui.models

import com.esgi.nova.parameters.ports.IParameters

data class Parameters(
    override var isDarkMode: Boolean,
    override var hasDailyEvents: Boolean,
    override var hasNotifications: Boolean,
    override var hasSound: Boolean
) : IParameters {
}