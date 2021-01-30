package com.esgi.nova.ui.init.view_models

import androidx.lifecycle.LiveData
import com.esgi.nova.ui.AppViewModel

abstract class BaseInitViewModel : AppViewModel() {
    abstract val currentInitStep: LiveData<Int>
    abstract val navigateToDashboard: LiveData<Boolean>
    abstract val networkError: LiveData<Boolean>

    abstract fun loadContent()
}