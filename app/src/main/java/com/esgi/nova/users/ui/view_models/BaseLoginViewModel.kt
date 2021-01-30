package com.esgi.nova.users.ui.view_models

import androidx.lifecycle.LiveData
import com.esgi.nova.ui.AppViewModel
import com.esgi.nova.users.ui.models.LogUser

abstract class BaseLoginViewModel : AppViewModel() {
    abstract val user: LiveData<LogUser>
    abstract val navigateToDashboard: LiveData<Boolean>
    abstract val navigateToInitSetup: LiveData<Boolean>
    abstract val invalidUsername: LiveData<Boolean>
    abstract val invalidPassword: LiveData<Boolean>
    abstract val userNotFound: LiveData<Boolean>
    abstract val unavailableNetwork: LiveData<Boolean>

    abstract fun initialize(isReconnection: Boolean)

    abstract fun updateUsername(username: String)

    abstract fun updatePassword(password: String)

    abstract fun tryLogin()
}