package com.esgi.nova.models

import java.util.*

data class Resource(
    val id: UUID,
    val name: String,
    val value: Int
) {
}