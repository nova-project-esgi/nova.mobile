package com.esgi.nova.users.ui.models

import com.esgi.nova.users.ports.ILogUser

data class LogUser(override var username: String, override var password: String): ILogUser
