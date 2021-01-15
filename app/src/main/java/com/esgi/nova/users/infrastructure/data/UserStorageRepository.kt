package com.esgi.nova.users.infrastructure.data

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.esgi.nova.infrastructure.preferences.PreferenceConstants
import com.esgi.nova.users.infrastructure.data.models.LogUser
import com.esgi.nova.users.ports.IConnectedUser
import com.esgi.nova.users.ports.IConnectedUserPassword
import com.esgi.nova.users.ports.ILogUser
import javax.inject.Inject

class UserStorageRepository @Inject constructor(private val context: Context) {

    fun saveUser(user: IConnectedUserPassword){
        context.getSharedPreferences(PreferenceConstants.UserKey, AppCompatActivity.MODE_PRIVATE)?.let {
            sharedPreferences ->
                with(sharedPreferences.edit()) {
                    putString(PreferenceConstants.TokenKey, user.token)
                    putString(PreferenceConstants.UsernameKey, user.username)
                    putString(PreferenceConstants.PasswordKey, user.password)
                    apply()
            }
        }
    }

    fun getUserToken(): String? {
        return context.getSharedPreferences(PreferenceConstants.UserKey, Context.MODE_PRIVATE)
            .getString(PreferenceConstants.TokenKey, null)
    }

    fun getUsername(): String? {
        return context.getSharedPreferences(PreferenceConstants.UserKey, Context.MODE_PRIVATE)
            .getString(PreferenceConstants.UsernameKey, null)
    }


    fun getUser(): ILogUser? {
        context.getSharedPreferences(PreferenceConstants.UserKey, Context.MODE_PRIVATE)?.let {
            sharedPreferences ->
            sharedPreferences.getString(PreferenceConstants.UsernameKey, null)?.let { username ->
                sharedPreferences.getString(PreferenceConstants.PasswordKey, null)?.let { password ->
                    return LogUser(username, password)
                }
            }
        }
        return null
    }




}