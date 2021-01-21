package com.esgi.nova.games.ui.leaderboard.view_models

import androidx.lifecycle.ViewModel
import com.esgi.nova.dtos.difficulty.DetailedDifficultyDto
import com.esgi.nova.games.ports.ILeaderBoardGameView
import com.esgi.nova.infrastructure.api.pagination.PageCursor
import com.esgi.nova.infrastructure.ports.IPageCursor
import com.esgi.nova.ui.IViewModelState

class LeaderBoardViewModel : ViewModel(), IViewModelState {
    override var initialized: Boolean = false

    var difficulties: List<DetailedDifficultyDto> = listOf()

    var currentDifficulty: DetailedDifficultyDto? = null

    var cursor: IPageCursor<ILeaderBoardGameView> =
        PageCursor(null) { cursor1, cursor2 ->
            val res = cursor2.eventCount.compareTo(cursor1.eventCount)
            if (res == 0) {
                return@PageCursor cursor1.id.compareTo(cursor2.id)
            }
            return@PageCursor res
        }


    var isLoading = false

}