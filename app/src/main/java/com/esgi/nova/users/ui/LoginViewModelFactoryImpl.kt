package com.esgi.nova.users.ui

import androidx.lifecycle.ViewModel
import com.esgi.nova.application_state.application.IsSynchronized
import com.esgi.nova.users.application.HasConnectedUser
import com.esgi.nova.users.application.LogInUser
import com.esgi.nova.users.application.LogOutUser
import com.esgi.nova.users.application.RetrieveUser
import com.esgi.nova.users.ui.view_models.LoginViewModel

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactoryImpl(
    private val logInUser: LogInUser,
    private val hasConnectedUser: HasConnectedUser,
    private val logOutUser: LogOutUser,
    private val retrieveUser: RetrieveUser,
    private val isSynchronized: IsSynchronized,
) : LoginViewModelFactory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(
            logInUser = logInUser,
            hasConnectedUser = hasConnectedUser,
            logOutUser = logOutUser,
            retrieveUser = retrieveUser,
            isSynchronized = isSynchronized
        ) as T
    }
}