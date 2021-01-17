package com.esgi.nova.users.infrastructure.data

import android.content.Context
import com.esgi.nova.infrastructure.preferences.PreferenceConstants
import com.esgi.nova.infrastructure.storage.BaseStorageRepository
import com.esgi.nova.users.infrastructure.data.models.LogUser
import com.esgi.nova.users.ports.IConnectedUserPassword
import com.esgi.nova.users.ports.ILogUser
import javax.inject.Inject

class UserStorageRepository @Inject constructor(
    context: Context
) : BaseStorageRepository(context) {
    override val preferenceKey: String = PreferenceConstants.User.Key

    fun saveUser(user: IConnectedUserPassword) {
        with(preference.edit()) {
            putString(PreferenceConstants.User.TokenKey, user.token)
            putString(PreferenceConstants.User.UsernameKey, user.username)
            putString(PreferenceConstants.User.PasswordKey, user.password)
            apply()
        }
    }

    fun getUserToken(): String? = preference.getString(PreferenceConstants.User.TokenKey, null)


    fun getUsername(): String? = preference.getString(PreferenceConstants.User.UsernameKey, null)


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

    fun removeUser(): ILogUser?{
        val user = getUser()
        with(preference.edit()) {
            remove(PreferenceConstants.User.TokenKey)
            remove(PreferenceConstants.User.UsernameKey)
            remove(PreferenceConstants.User.PasswordKey)
            apply()
        }
        return user
    }




}