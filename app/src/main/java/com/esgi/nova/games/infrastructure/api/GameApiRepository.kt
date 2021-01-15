package com.esgi.nova.games.infrastructure.api

import com.esgi.nova.games.infrastructure.api.models.GameResume
import com.esgi.nova.games.ports.IGame
import com.esgi.nova.games.ports.IGameForCreation
import com.esgi.nova.infrastructure.api.ApiRepository
import com.esgi.nova.users.application.GetUserToken
import com.esgi.nova.users.application.UpdateUserToken
import retrofit2.Retrofit
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
        val game = gameService.createGame(game).execute().getLocatedContent<GameResume>()?.execute()
        return game?.body()
    }

}

