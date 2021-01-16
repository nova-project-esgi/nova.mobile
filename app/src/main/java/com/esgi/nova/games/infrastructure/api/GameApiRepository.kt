package com.esgi.nova.games.infrastructure.api

import com.esgi.nova.games.application.models.GameForCreation
import com.esgi.nova.games.infrastructure.api.models.GameForUpdate
import com.esgi.nova.games.infrastructure.api.models.GameResume
import com.esgi.nova.games.ports.IGame
import com.esgi.nova.games.ports.IGameForCreation
import com.esgi.nova.games.ports.IDetailedGame
import com.esgi.nova.games.ports.IGameEdition
import com.esgi.nova.infrastructure.api.ApiRepository
import com.esgi.nova.users.application.GetUserToken
import com.esgi.nova.users.application.UpdateUserToken
import com.esgi.nova.utils.reflectMapNotNull
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject


class GameApiRepository @Inject constructor(getUserToken: GetUserToken, updateUserToken: UpdateUserToken): ApiRepository(getUserToken,updateUserToken) {
    private var gameService: GameService

    init {
        val retrofit = Retrofit.Builder()
            .apiBuilder()
            .build()

        gameService = retrofit.create(GameService::class.java)
    }

    fun retrieveUser() {
        gameService.retrieveUser()?.execute()?.body()
    }

    fun createGame(game: IGameForCreation): IGame? {
        val test = game.reflectMapNotNull<IGameForCreation, GameForCreation>()
        return gameService.createGame(test).execute().getLocatedContent<GameResume>()
    }

    fun update(id: UUID, game: IGameEdition){
        val gamet = game.reflectMapNotNull<IGameEdition, GameForUpdate>()
        val res = gameService.uploadGame(
            id,
            gamet
        ).execute()
    }

}

