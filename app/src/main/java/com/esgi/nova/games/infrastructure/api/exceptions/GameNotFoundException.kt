package com.esgi.nova.games.infrastructure.api.exceptions

import java.util.*

class GameNotFoundException(val id: UUID) : Throwable() {

}
