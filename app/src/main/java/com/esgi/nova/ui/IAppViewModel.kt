package com.esgi.nova.ui

import androidx.lifecycle.LiveData

interface IAppViewModel {
    var initialized: Boolean
    val unexpectedError: LiveData<Boolean>
}