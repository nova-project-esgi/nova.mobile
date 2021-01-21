package com.esgi.nova.games.ui.leaderboard.view_holders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.R
import com.esgi.nova.games.ports.ILeaderBoardGameView
import java.time.LocalTime

class LeaderBoardScoreViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.score_item, parent, false)) {
    private var usernameTv: TextView? = null
    private var roundTv: TextView? = null
    private var positionTv: TextView? = null
    private var trophyIv: ImageView? = null
    private var durationTv: TextView? = null

    init {
        usernameTv = itemView.findViewById(R.id.username_tv)
        roundTv = itemView.findViewById(R.id.round_tv)
        positionTv = itemView.findViewById(R.id.position_tv)
        trophyIv = itemView.findViewById(R.id.trophy_iv)
        durationTv = itemView.findViewById(R.id.duration_tv)
    }

    fun bind(score: ILeaderBoardGameView, position: Int) {
        usernameTv?.text = score.user.username
        roundTv?.text = "${score.eventCount}"
        durationTv?.text = LocalTime.ofSecondOfDay(score.duration.toLong()).toString()
        positionTv?.text = "${position + 1}"

        when (position) {
            0 -> {
                trophyIv?.setImageResource(R.drawable.first)
            }
            1 -> {
                trophyIv?.setImageResource(R.drawable.second)
            }
            2 -> {
                trophyIv?.setImageResource(R.drawable.third)
            }
            else -> {
                trophyIv?.setImageDrawable(null)
            }
        }
    }
}