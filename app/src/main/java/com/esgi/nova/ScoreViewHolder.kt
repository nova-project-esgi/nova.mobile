package com.esgi.nova

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.models.Score

class ScoreViewHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.score_item, parent, false))
{
    private var username: TextView? = null
    private var turn: TextView? = null

    init {
        username = itemView.findViewById(R.id.tv_username)
        turn = itemView.findViewById(R.id.tv_turn)
    }

    fun bind(score: Score)
    {
        username?.text = score.userName
        turn?.text = "${score.turn} tours"
    }
}