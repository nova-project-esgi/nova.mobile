package com.esgi.nova.users.ui.view_models

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
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
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val logInUser: LogInUser,
    private val hasConnectedUser: HasConnectedUser,
    private val setCurrentTheme: SetCurrentTheme,
    private val logOutUser: LogOutUser,
    private val retrieveUser: RetrieveUser,
    private val switchSound: SwitchSound,
    private val isSynchronized: IsSynchronized,
    @Assisted private val savedStateHandle: SavedStateHandle
) :  AppViewModel() {

    val user: LiveData<LogUser>
        get() = _user
    private var _user = MutableLiveData<LogUser>()

    val navigateToDashboard: LiveData<Boolean>
        get() = _navigateToDashboard
    private var _navigateToDashboard = MutableLiveData<Boolean>()

    val navigateToInitSetup: LiveData<Boolean>
        get() = _navigateToInitSetup
    private var _navigateToInitSetup = MutableLiveData<Boolean>()

    val invalidUsername: LiveData<Boolean>
        get() = _invalidUsername
    private var _invalidUsername = MutableLiveData<Boolean>()

    val invalidPassword: LiveData<Boolean>
        get() = _invalidPassword
    private var _invalidPassword = MutableLiveData<Boolean>()

    val userNotFound: LiveData<Boolean>
        get() = _userNotFound
    private var _userNotFound = MutableLiveData<Boolean>()

    val unavailableNetwork: LiveData<Boolean>
        get() = _unavailableNetwork
    private var _unavailableNetwork = MutableLiveData<Boolean>()


    fun initialize(isReconnection: Boolean) {
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

    fun updateUsername(username: String) {
        _user.value?.username = username
    }

    fun updatePassword(password: String) {
        _user.value?.password = password
    }


    fun tryLogin() {
        val userLoginDto = user.value?.reflectMap<LogUser, UserLoginDto>()
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