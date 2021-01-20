package com.esgi.nova.ui.init.view_models

import androidx.lifecycle.ViewModel
import com.esgi.nova.ui.IViewModelState

class InitViewModel : ViewModel(), IViewModelState {
    override var initialized: Boolean = false
    var currentStep: Int
        get() = _currentStep
        set(value) {
            _currentStep = if (value < stepLimit) value else _currentStep
        }
    var stepLimit = 0
    private var _currentStep = 0

}