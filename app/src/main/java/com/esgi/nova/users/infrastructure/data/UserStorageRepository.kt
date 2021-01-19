package com.esgi.nova.users.infrastructure.data

import android.content.Context
import com.esgi.nova.infrastructure.preferences.PreferenceConstants
import com.esgi.nova.infrastructure.preferences.getUUID
import com.esgi.nova.infrastructure.preferences.putUUID
import com.esgi.nova.infrastructure.storage.BaseStorageRepository
import com.esgi.nova.users.infrastructure.data.models.LogUser
import com.esgi.nova.users.infrastructure.data.models.UserResume
import com.esgi.nova.users.ports.IConnectedUserPassword
import com.esgi.nova.users.ports.ILogUser
import com.esgi.nova.users.ports.IUserResume
import java.util.*
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
            putUUID(PreferenceConstants.User.UserIdKey, user.id)
            apply()
        }
    }



    fun getUserToken(): String? = preference.getString(PreferenceConstants.User.TokenKey, null)


    fun getUsername(): String? = preference.getString(PreferenceConstants.User.UsernameKey, null)
    fun getUserId(): UUID? = preference.getUUID(PreferenceConstants.User.UsernameKey, null)

    fun getUserResume(): IUserResume? = getUserId()?.let { userId ->
        getUsername()?.let { username ->
            UserResume (username = username, id = userId)
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

    fun removeUser(): ILogUser?{
        val user = getUser()
        with(preference.edit()) {
            remove(PreferenceConstants.User.TokenKey)
            remove(PreferenceConstants.User.UsernameKey)
            remove(PreferenceConstants.User.PasswordKey)
            remove(PreferenceConstants.User.UserIdKey)
            apply()
        }
        return user
    }




}