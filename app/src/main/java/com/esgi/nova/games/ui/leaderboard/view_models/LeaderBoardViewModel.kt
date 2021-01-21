package com.esgi.nova.games.ui.leaderboard.view_models

import androidx.lifecycle.ViewModel
import com.esgi.nova.dtos.difficulty.DetailedDifficultyDto
import com.esgi.nova.games.infrastructure.api.models.LeaderBoardGameView
import com.esgi.nova.games.ports.ILeaderBoardGameView
import com.esgi.nova.infrastructure.api.pagination.PageCursor
import com.esgi.nova.infrastructure.ports.IPageCursor
import com.esgi.nova.ui.IViewModelState

class LeaderBoardViewModel : ViewModel(), IViewModelState {
    override var initialized: Boolean = false

    var difficulties: List<DetailedDifficultyDto> = listOf()

    var currentDifficulty: DetailedDifficultyDto? = null

    lateinit var cursor: IPageCursor<ILeaderBoardGameView>


    var isLoading = false

}