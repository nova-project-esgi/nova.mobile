package com.esgi.nova.games.infrastructure.api

import android.content.Context
import com.esgi.nova.games.infrastructure.api.models.GameResume
import com.esgi.nova.games.infrastructure.api.models.LeaderBoardGameView
import com.esgi.nova.games.ports.IGame
import com.esgi.nova.games.ports.IGameEdition
import com.esgi.nova.games.ports.IGameForCreation
import com.esgi.nova.games.ports.IGameState
import com.esgi.nova.infrastructure.api.AuthenticatedApiRepository
import com.esgi.nova.infrastructure.api.pagination.IPageMetadata
import com.esgi.nova.users.application.GetUserToken
import com.esgi.nova.users.application.UpdateUserToken
import com.esgi.nova.utils.reflectMapNotNull
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject


class GameApiRepository @Inject constructor(
    @ApplicationContext context: Context,
    getUserToken: GetUserToken, updateUserToken: UpdateUserToken
) : AuthenticatedApiRepository(
    getUserToken,
    updateUserToken, context
) {
    private var gameService: GameService = apiBuilder()
        .build()
        .create(GameService::class.java)

    suspend fun createGame(game: IGameForCreation): IGame? {
        return gameService.createGame(game.reflectMapNotNull()).getLocatedContent<GameResume>()
    }

    suspend fun update(id: UUID, game: IGameEdition) {
        gameService.updateGame(
            id,
            game.reflectMapNotNull()
        )
    }

    suspend fun getDefaultGamesList(
        difficultyId: UUID,
        page: Int?,
        pageSize: Int?
    ): IPageMetadata<LeaderBoardGameView> {
        return gameService.getLeaderBoardGamesByDifficulty(difficultyId.toString(), page, pageSize)
            .toSecuredPage()
    }

    suspend fun getLastActiveGameForUser(username: String): IGameState {
        return gameService.getGameStateByUsername(username)
    }


}

