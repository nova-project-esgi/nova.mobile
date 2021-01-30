package com.esgi.nova.games.ui.leaderboard.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.games.ports.ILeaderBoardGameView
import com.esgi.nova.games.ui.leaderboard.view_holders.LeaderBoardScoreViewHolder

class GamesAdapter(private val scores: Collection<ILeaderBoardGameView>) :
    RecyclerView.Adapter<LeaderBoardScoreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderBoardScoreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return LeaderBoardScoreViewHolder(
            inflater,
            parent
        )
    }

    override fun onBindViewHolder(holder: LeaderBoardScoreViewHolder, position: Int) {
        val score: ILeaderBoardGameView = scores.elementAt(position)
        holder.bind(score, position)
    }

    override fun getItemCount(): Int = scores.size

}