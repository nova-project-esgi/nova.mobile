package com.esgi.nova.games.ports

import com.esgi.nova.models.Role
import java.util.*

interface ILeaderBoardGameView {
    val id: UUID
    val user: IUserRecapped
    val duration: Int
    val difficultyId: UUID
    val resources: List<IGameResourceView>
    val eventCount: Int

    interface IGameResourceView {
        val resourceId: UUID
        val total: Int
        val name: String
    }
    interface IUserRecapped {
        val id: UUID
        val email: String
        val role: Role
        val username: String
    }
}