package com.esgi.nova.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.GamesViewHolder
import com.esgi.nova.games.infrastructure.dto.LeaderBoardGameView

class GamesAdapter(private val scores: List<LeaderBoardGameView>) : RecyclerView.Adapter<GamesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GamesViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        val score: LeaderBoardGameView = scores[position]
        holder.bind(score, position)
    }

    override fun getItemCount(): Int = scores.size

}