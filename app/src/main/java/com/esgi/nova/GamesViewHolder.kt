package com.esgi.nova

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.games.infrastructure.dto.LeaderBoardGameView

class GamesViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.score_item, parent, false)) {
    private var username: TextView? = null
    private var turn: TextView? = null
    private var tv_position: TextView? = null
    private var trophy: ImageView? = null
    private var tv_date: TextView? = null

    init {
        username = itemView.findViewById(R.id.username_tv)
        turn = itemView.findViewById(R.id.round_tv)
        tv_position = itemView.findViewById(R.id.position_tv)
        trophy = itemView.findViewById(R.id.trophy_iv)
        tv_date = itemView.findViewById(R.id.date_tv)
    }

    fun bind(score: LeaderBoardGameView, position: Int) {
        username?.text = score.user.username

        turn?.text = "${score.eventCount} tours"
        tv_position?.text = "${position + 1}"

        when (position) {
            0 -> {
                trophy?.setImageResource(R.drawable.first)
            }
            1 -> {
                trophy?.setImageResource(R.drawable.second)
            }
            2 -> {
                trophy?.setImageResource(R.drawable.third)
            }
            else -> {
                trophy?.setImageDrawable(null)
            }
        }
    }
}