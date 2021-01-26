package com.esgi.nova.ui.init.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esgi.nova.ui.IAppViewModel

class InitViewModel : ViewModel(), IAppViewModel {
    override var initialized: Boolean = false

    override val unexpectedError: LiveData<Boolean>
        get() = _unexpectedError

    private var _unexpectedError =  MutableLiveData<Boolean>()
    var currentStep: Int
        get() = _currentStep
        set(value) {
            _currentStep = if (value < stepLimit) value else _currentStep
        }
    var stepLimit = 0
    private var _currentStep = 0

}