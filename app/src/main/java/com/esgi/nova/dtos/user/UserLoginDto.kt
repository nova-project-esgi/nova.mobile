package com.esgi.nova.dtos.user

import com.esgi.nova.users.exceptions.InvalidPasswordException
import com.esgi.nova.users.exceptions.InvalidUsernameException
import com.esgi.nova.users.ports.ILogUser

class UserLoginDto(username: String, password: String) : ILogUser {
    override val username: String = username.trim()
    override val password: String = password.trim()

    fun validate() {
        if (username.isEmpty() || username.length < 6 || username.length > 20) {
            throw InvalidUsernameException()
        }
        if (password.isEmpty() || password.length < 8) {
            throw InvalidPasswordException()
        }
    }


}