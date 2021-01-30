package com.esgi.nova.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.esgi.nova.users.ui.models.LogUser
import com.esgi.nova.users.ui.view_models.BaseLoginViewModel

class LoginViewModelFake(
    private var _user: MutableLiveData<LogUser> = MutableLiveData<LogUser>(),
    private var _navigateToDashboard: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    private var _navigateToInitSetup: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    private var _invalidUsername: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    private var _invalidPassword: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    private var _userNotFound: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    private var _unavailableNetwork: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    override val unexpectedError: LiveData<Boolean>
) : BaseLoginViewModel() {

    var isPasswordValid = true
    var isUsernameValid = true

    override val user: LiveData<LogUser> get() = _user
    override val navigateToDashboard: LiveData<Boolean> get() = _navigateToDashboard
    override val navigateToInitSetup: LiveData<Boolean> get() = _navigateToInitSetup
    override val invalidUsername: LiveData<Boolean> get() = _invalidUsername
    override val invalidPassword: LiveData<Boolean> get() = _invalidPassword
    override val userNotFound: LiveData<Boolean> get() = _userNotFound
    override val unavailableNetwork: LiveData<Boolean> get() = _unavailableNetwork
    override fun initialize(isReconnection: Boolean) {

    }

    override fun updateUsername(username: String) {

    }

    override fun updatePassword(password: String) {

    }

    override fun tryLogin() {

        _invalidUsername.value = isUsernameValid
        _invalidPassword.value = isPasswordValid
    }

}