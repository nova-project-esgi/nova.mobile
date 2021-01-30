package com.esgi.nova.users.ui.view_models

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.esgi.nova.application_state.application.IsSynchronized
import com.esgi.nova.dtos.user.UserLoginDto
import com.esgi.nova.infrastructure.api.exceptions.NoConnectionException
import com.esgi.nova.parameters.application.SetCurrentTheme
import com.esgi.nova.sound.application.SwitchSound
import com.esgi.nova.ui.AppViewModel
import com.esgi.nova.users.application.HasConnectedUser
import com.esgi.nova.users.application.LogInUser
import com.esgi.nova.users.application.LogOutUser
import com.esgi.nova.users.application.RetrieveUser
import com.esgi.nova.users.exceptions.InvalidPasswordException
import com.esgi.nova.users.exceptions.InvalidUsernameException
import com.esgi.nova.users.exceptions.UserNotFoundException
import com.esgi.nova.users.ui.models.LogUser
import com.esgi.nova.utils.reflectMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

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

class LoginViewModel constructor(
    private val logInUser: LogInUser,
    private val hasConnectedUser: HasConnectedUser,
    private val logOutUser: LogOutUser,
    private val retrieveUser: RetrieveUser,
    private val isSynchronized: IsSynchronized,
) : BaseLoginViewModel() {

    override val user: LiveData<LogUser>
        get() = _user
    private var _user = MutableLiveData(LogUser("", ""))

    override val navigateToDashboard: LiveData<Boolean>
        get() = _navigateToDashboard
    private var _navigateToDashboard = MutableLiveData<Boolean>()

    override val navigateToInitSetup: LiveData<Boolean>
        get() = _navigateToInitSetup
    private var _navigateToInitSetup = MutableLiveData<Boolean>()

    override val invalidUsername: LiveData<Boolean>
        get() = _invalidUsername
    private var _invalidUsername = MutableLiveData<Boolean>()

    override val invalidPassword: LiveData<Boolean>
        get() = _invalidPassword
    private var _invalidPassword = MutableLiveData<Boolean>()

    override val userNotFound: LiveData<Boolean>
        get() = _userNotFound
    private var _userNotFound = MutableLiveData<Boolean>()

    override val unavailableNetwork: LiveData<Boolean>
        get() = _unavailableNetwork
    private var _unavailableNetwork = MutableLiveData<Boolean>()


    override fun initialize(isReconnection: Boolean) {
        if (initialized) {
            return;
        }
        if (isReconnection) {
            logOutUser.execute()
            initUser()
        } else if (hasConnectedUser.execute()) {
            if (isSynchronized.execute()) {
                _navigateToDashboard.value = true
            } else {
                _navigateToInitSetup.value = true
            }
        } else {
            initUser()
        }
    }


    private fun initUser() {
        viewModelScope.launch {
            retrieveUser.execute()?.let { user ->
                _user.value = user.reflectMap()
            }
        }
    }

    override fun updateUsername(username: String) {
        _user.value?.username = username
    }

    override fun updatePassword(password: String) {
        _user.value?.password = password
    }


    override fun tryLogin() {
        val userLoginDto = _user.value?.reflectMap<LogUser, UserLoginDto>()
        userLoginDto?.let {
            try {
                userLoginDto.validate()
                setLoading()
                login(userLoginDto)
            } catch (e: InvalidUsernameException) {
                _invalidUsername.value = true
            } catch (e: InvalidPasswordException) {
                _invalidPassword.value = true
            }
        }
    }

    private fun login(userLoginDto: UserLoginDto) {
        viewModelScope.launch {
            try {
                logInUser.execute(userLoginDto)
                if (isSynchronized.execute()) {
                    _navigateToDashboard.value = true
                } else {
                    _navigateToInitSetup.value = true
                }
            } catch (e: NoConnectionException) {
                _unavailableNetwork.value = true
            } catch (e: UserNotFoundException) {
                _userNotFound.value = true
            } catch (e: Exception) {
                _unexpectedError.value = true
            } finally {
                unsetLoading()
            }
        }
    }


}