package com.esgi.nova.ui.dashboard

import com.esgi.nova.difficulties.application.GetAllDetailedDifficultiesSortedByRank
import com.esgi.nova.games.application.CreateGame
import com.esgi.nova.games.infrastructure.data.game.models.CanLaunchGame
import com.esgi.nova.games.infrastructure.data.game.models.CanResumeGame
import com.esgi.nova.resources.application.GetImageStartValueResourceWrappersByDifficultyId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class DashBoardActivityModule {

    @Provides
    fun provideCalculatorViewModelFactory(
        createGame: CreateGame,
        getAllDetailedDifficultiesSortedByRank: GetAllDetailedDifficultiesSortedByRank,
        getImageStartValueResourceWrappersByDifficultyId: GetImageStartValueResourceWrappersByDifficultyId,
        canLaunchGame: CanLaunchGame,
        canResumeGame: CanResumeGame,
    ): IDashboardViewModelFactory =
        DashBoardViewModelFactory(
            createGame = createGame,
            getAllDetailedDifficultiesSortedByRank = getAllDetailedDifficultiesSortedByRank,
            getImageStartValueResourceWrappersByDifficultyId = getImageStartValueResourceWrappersByDifficultyId,
            canLaunchGame = canLaunchGame,
            canResumeGame = canResumeGame
        )
}