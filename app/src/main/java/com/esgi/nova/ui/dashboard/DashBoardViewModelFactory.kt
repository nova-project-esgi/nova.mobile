package com.esgi.nova.ui.dashboard

import androidx.lifecycle.ViewModel
import com.esgi.nova.application_state.application.IsSynchronized
import com.esgi.nova.difficulties.application.GetAllDetailedDifficultiesSortedByRank
import com.esgi.nova.games.infrastructure.data.game.models.CanLaunchGame
import com.esgi.nova.games.infrastructure.data.game.models.CanResumeGame
import com.esgi.nova.resources.application.GetImageStartValueResourceWrappersByDifficultyId
import com.esgi.nova.ui.dashboard.view_models.DashboardViewModel

@Suppress("UNCHECKED_CAST")
class DashBoardViewModelFactory(
    private val isSynchronized: IsSynchronized,
    private val getAllDetailedDifficultiesSortedByRank: GetAllDetailedDifficultiesSortedByRank,
    private val getImageStartValueResourceWrappersByDifficultyId: GetImageStartValueResourceWrappersByDifficultyId,
    private val canLaunchGame: CanLaunchGame,
    private val canResumeGame: CanResumeGame,
) : IDashboardViewModelFactory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DashboardViewModel(
            getAllDetailedDifficultiesSortedByRank = getAllDetailedDifficultiesSortedByRank,
            getImageStartValueResourceWrappersByDifficultyId = getImageStartValueResourceWrappersByDifficultyId,
            canLaunchGame = canLaunchGame,
            canResumeGame = canResumeGame,
            isSynchronized = isSynchronized
        ) as T
    }
}