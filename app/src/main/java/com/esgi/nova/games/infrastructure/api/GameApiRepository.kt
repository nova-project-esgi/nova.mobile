package com.esgi.nova.games.infrastructure.api

import android.content.Context
import com.esgi.nova.games.application.models.GameForCreation
import com.esgi.nova.games.infrastructure.api.models.GameForUpdate
import com.esgi.nova.games.infrastructure.api.models.GameResume
import com.esgi.nova.games.infrastructure.api.models.LeaderBoardGameView
import com.esgi.nova.games.ports.IGame
import com.esgi.nova.games.ports.IGameEdition
import com.esgi.nova.games.ports.IGameForCreation
import com.esgi.nova.games.ports.IGameState
import com.esgi.nova.infrastructure.api.AuthenticatedApiRepository
import com.esgi.nova.infrastructure.api.pagination.PageMetadata
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
        val test = game.reflectMapNotNull<IGameForCreation, GameForCreation>()
        return gameService.createGame(test).getLocatedContent<GameResume>()
    }

    suspend fun update(id: UUID, game: IGameEdition) {
        val gamet = game.reflectMapNotNull<IGameEdition, GameForUpdate>()
        val res = gameService.uploadGame(
            id,
            gamet
        )
//        if(res.code() ==  HttpConstants.Codes.NotFound){
//            throw GameNotFoundException(
//                id
//            )
//        }
    }

    suspend fun getDefaultGamesList(
        difficultyId: UUID,
        page: Int?,
        pageSize: Int?
    ): PageMetadata<LeaderBoardGameView> {
        return gameService.getLeaderBoardGamesByDifficulty(difficultyId.toString(), page, pageSize)
    }

    suspend fun getLastActiveGameForUser(username: String): IGameState {
        return gameService.getGameStateByUsername(username)
    }


}

