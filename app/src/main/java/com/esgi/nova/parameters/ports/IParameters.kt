package com.esgi.nova.parameters.ports

import com.esgi.nova.languages.ports.IAppLanguage

interface IParameters {
    val isDarkMode: Boolean
    val hasDailyEvents: Boolean
    val hasNotifications: Boolean
    val hasSound: Boolean
}