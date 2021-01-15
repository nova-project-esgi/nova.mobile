package com.esgi.nova.users.application

import com.esgi.nova.users.application.models.ConnectedUserPassword
import com.esgi.nova.users.ports.IConnectedUser
import com.esgi.nova.users.ports.IConnectedUserPassword

fun IConnectedUser.toConnectedUserPassword(password: String): IConnectedUserPassword = ConnectedUserPassword(
    password = password,
    email = email,
    role = role,
    username = username,
    token = token
)