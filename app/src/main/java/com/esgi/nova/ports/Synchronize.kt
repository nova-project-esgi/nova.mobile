package com.esgi.nova.ports

interface Synchronize {
    suspend fun execute()
}