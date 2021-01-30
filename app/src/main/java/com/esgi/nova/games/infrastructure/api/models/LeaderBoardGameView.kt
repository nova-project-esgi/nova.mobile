package com.esgi.nova.games.infrastructure.api.models

import com.esgi.nova.games.ports.ILeaderBoardGameView
import com.esgi.nova.models.Role
import java.util.*

data class LeaderBoardGameView(
    override val id: UUID,
    override val user: UserRecapped,
    override val duration: Int,
    override val difficultyId: UUID,
    override val resources: List<GameResourceView>,
    override val eventCount: Int
) : ILeaderBoardGameView {


    data class GameResourceView(
        override val resourceId: UUID,
        override val total: Int,
        override val name: String
    ) : ILeaderBoardGameView.IGameResourceView

    data class UserRecapped(
        override val id: UUID,
        override val email: String,
        override val role: Role,
        override val username: String
    ) :
        ILeaderBoardGameView.IUserRecapped

}
