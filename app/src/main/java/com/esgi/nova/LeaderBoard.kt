package com.esgi.nova

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.esgi.nova.adapters.ScoresAdapter
import com.esgi.nova.models.Score
import kotlinx.android.synthetic.main.activity_leader_board.*

class LeaderBoard : AppCompatActivity() {

    private var scores = mutableListOf<Score>(
        Score(1, "Maxime", 300),
        Score(2, "Sacha", 145),
        Score(3, "James", 65),
        Score(4, "Masa", 795),
        Score(5, "Jérémy", 20),
        Score(6, "Simon", 125),
        Score(7, "Lucas", 200),
        Score(8, "Théau", 855)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)
        spn_ld_difficulty.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listOf("Facile","Moyen","Difficile"))

        //scores = scores.sortedWith(Comparator { o1, o2 -> o2.turn - o1.turn } ).toMutableList()

        scores.sortByDescending { it.turn }

        rv_scores?.apply {
            layoutManager = LinearLayoutManager(this@LeaderBoard)
            adapter = ScoresAdapter(scores)
        }

        swipeContainer.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { fetchTimelineAsync(0) })

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);
    }


    fun fetchTimelineAsync(page: Int) {
        scores.add(Score(9,"test",3333))
        scores.sortByDescending { it.turn }

        rv_scores?.apply {
            layoutManager = LinearLayoutManager(this@LeaderBoard)
            adapter = ScoresAdapter(scores)
        }
        swipeContainer.setRefreshing(false);

    }

}