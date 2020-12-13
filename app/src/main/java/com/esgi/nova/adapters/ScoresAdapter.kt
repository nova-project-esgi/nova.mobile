package com.esgi.nova.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.ScoreViewHolder
import com.esgi.nova.models.Score

class ScoresAdapter(private val scores: List<Score>): RecyclerView.Adapter<ScoreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ScoreViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score: Score = scores[position]
        holder.bind(score)
    }

    override fun getItemCount(): Int = scores.size

}