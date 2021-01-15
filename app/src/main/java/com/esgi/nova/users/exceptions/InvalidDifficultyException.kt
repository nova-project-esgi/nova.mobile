package com.esgi.nova.users.exceptions

class InvalidDifficultyException : Throwable(){
    override val message: String
        get() = "Aucune partie trouvé pour cette difficulté"
}