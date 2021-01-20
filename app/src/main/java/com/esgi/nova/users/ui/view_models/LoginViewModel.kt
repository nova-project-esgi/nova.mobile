package com.esgi.nova.users.ui.view_models

import androidx.lifecycle.ViewModel
import com.esgi.nova.ui.IViewModelState
import com.esgi.nova.users.ports.ILogUser

class LoginViewModel : ViewModel(), IViewModelState, ILogUser {
    override var initialized: Boolean = false
    override var username: String =""
    override var password: String = ""

    fun copyUser(user: ILogUser){
        username = user.username
        password = user.password
    }
}