package com.esgi.nova.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class AppViewModel : ViewModel() {

    open val unexpectedError: LiveData<Boolean>
        get() = _unexpectedError
    protected var _unexpectedError = MutableLiveData<Boolean>()

    protected var initialized: Boolean = false

    open val isLoading: LiveData<Boolean>
        get() = _isLoading
    private var _isLoading = MutableLiveData(false)

    private var loadingCnt = 0

    fun setLoading() {
        loadingCnt++
        updateIsLoading()
    }

    fun unsetLoading() {
        loadingCnt--
        loadingCnt = if (loadingCnt >= 0) loadingCnt else 0
        updateIsLoading()
    }

    private fun updateIsLoading() {
        _isLoading.value = loadingCnt != 0
    }

    protected open fun loadingLaunch(
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        setLoading()

        return viewModelScope.launch {
            try {
                block.invoke(this)
            } finally {
                unsetLoading()
            }
        }
    }
}