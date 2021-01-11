package com.esgi.nova.users.dtos

import com.esgi.nova.users.exceptions.InvalidPasswordException
import com.esgi.nova.users.exceptions.InvalidUsernameException

class UserLoginDto(username: String, password: String) {
    val username: String
    val password: String

    init {
        this.username = username.trim()
        this.password = password.trim()
    }

    fun validate() {
        if (username.isEmpty() || username.length < 6 || username.length > 20) {
            throw InvalidUsernameException()
        }
        if (password.isEmpty() || password.length < 8) {
            throw InvalidPasswordException()
        }
    }


}