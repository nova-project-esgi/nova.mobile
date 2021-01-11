package com.esgi.nova.users.exceptions

class InvalidUsernameException : Throwable() {
    override val message: String
        get() = "L'identifiant doit avoir entre 6 et 20 caractères"
}
