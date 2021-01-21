package com.esgi.nova.users.infrastructure.data

import android.content.Context
import com.esgi.nova.infrastructure.preferences.PreferenceConstants
import com.esgi.nova.infrastructure.preferences.getUUID
import com.esgi.nova.infrastructure.preferences.putUUID
import com.esgi.nova.infrastructure.storage.BaseStorageRepository
import com.esgi.nova.users.infrastructure.data.models.LogUser
import com.esgi.nova.users.infrastructure.data.models.UserRecapped
import com.esgi.nova.users.ports.IConnectedUserPassword
import com.esgi.nova.users.ports.ILogUser
import com.esgi.nova.users.ports.IUserRecapped
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class UserStorageRepository @Inject constructor(
    @ApplicationContext context: Context
) : BaseStorageRepository(context) {
    override val preferenceKey: String = PreferenceConstants.User.Key

    fun saveUser(user: IConnectedUserPassword) {
        with(preference.edit()) {
            putString(PreferenceConstants.User.TokenKey, user.token)
            putString(PreferenceConstants.User.UsernameKey, user.username)
            putString(PreferenceConstants.User.PasswordKey, user.password)
            putUUID(PreferenceConstants.User.UserIdKey, user.id)
            putBoolean(PreferenceConstants.User.IsConnectedKey, true)
            apply()
        }
    }

    fun changeConnectionState(isConnected: Boolean){
        with(preference.edit()) {
            putBoolean(PreferenceConstants.User.IsConnectedKey, isConnected)
            apply()
        }
    }



    fun getUserToken(): String? = preference.getString(PreferenceConstants.User.TokenKey, null)


    fun getUsername(): String? = preference.getString(PreferenceConstants.User.UsernameKey, null)
    fun getUserId(): UUID? = preference.getUUID(PreferenceConstants.User.UserIdKey, null)
    fun getUserConnectionState(): Boolean = preference.getBoolean(PreferenceConstants.User.IsConnectedKey, false)

    fun getUserResume(): IUserRecapped? = getUserId()?.let { userId ->
        getUsername()?.let { username ->
            UserRecapped (username = username, id = userId)
        }
    }

    fun getUser(): ILogUser? {
        preference.getString(PreferenceConstants.User.UsernameKey, null)
            ?.let { username ->
                preference.getString(PreferenceConstants.User.PasswordKey, null)
                    ?.let { password ->
                        return LogUser(username, password)
                    }
            }
        return null
    }

    fun removeUser(){
        with(preference.edit()) {
            remove(PreferenceConstants.User.TokenKey)
            remove(PreferenceConstants.User.UsernameKey)
            remove(PreferenceConstants.User.PasswordKey)
            remove(PreferenceConstants.User.UserIdKey)
            remove(PreferenceConstants.User.IsConnectedKey)
            apply()
        }
    }




}