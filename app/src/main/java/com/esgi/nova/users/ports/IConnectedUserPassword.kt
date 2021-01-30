package com.esgi.nova.users.ports

interface IConnectedUserPassword : IConnectedUser {
    val password: String
}