package com.esgi.nova.models

import java.util.*

data class Difficulty(val id: UUID, val name: String){
    override fun toString(): String {
        return name
    }
}
