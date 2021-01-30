package com.esgi.nova.users.ui

import com.esgi.nova.application_state.application.IsSynchronized
import com.esgi.nova.users.application.HasConnectedUser
import com.esgi.nova.users.application.LogInUser
import com.esgi.nova.users.application.LogOutUser
import com.esgi.nova.users.application.RetrieveUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class LoginActivityModule {

    @Provides
    fun provideCalculatorViewModelFactory(
        logInUser: LogInUser,
        hasConnectedUser: HasConnectedUser,
        logOutUser: LogOutUser,
        retrieveUser: RetrieveUser,
        isSynchronized: IsSynchronized,
    ): LoginViewModelFactory =
        LoginViewModelFactoryImpl(
            logInUser = logInUser,
            hasConnectedUser = hasConnectedUser,
            logOutUser = logOutUser,
            retrieveUser = retrieveUser,
            isSynchronized = isSynchronized
        )
}