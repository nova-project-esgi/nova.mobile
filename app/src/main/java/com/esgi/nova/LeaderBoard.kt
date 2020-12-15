package com.esgi.nova

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.esgi.nova.adapters.ScoresAdapter
import com.esgi.nova.models.Score
import kotlinx.android.synthetic.main.activity_leader_board.*
import java.util.*

class LeaderBoard : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var scoresFacile = mutableListOf<Score>(
        Score(1, "Maxime", 300,Date()),
        Score(2, "Sacha", 145,Date()),
        Score(3, "James", 65,Date()),
        Score(4, "Masa", 795,Date()),
        Score(5, "Jérémy", 20,Date()),
        Score(6, "Simon", 125,Date()),
        Score(7, "Lucas", 200,Date()),
        Score(8, "Théau", 855,Date()),
        Score(8, "Léa", 1,Date())
    )

    private var scoresMoyen = mutableListOf<Score>(
        Score(1, "Moyen", 300,Date()),
        Score(2, "Moyen", 145,Date()),
        Score(3, "Moyen", 65,Date()),
        Score(4, "Moyen", 795,Date()),
        Score(5, "Moyen", 20,Date()),
        Score(6, "Moyen", 125,Date()),
        Score(7, "Moyen", 200,Date()),
        Score(8, "Moyen", 855,Date()),
        Score(8, "Moyen", 1,Date())
    )

    private var scoresDifficile = mutableListOf<Score>(
        Score(1, "Difficile", 300,Date()),
        Score(2, "Difficile", 145,Date()),
        Score(3, "Difficile", 65,Date()),
        Score(4, "Difficile", 795,Date()),
        Score(5, "Difficile", 20,Date()),
        Score(6, "Difficile", 125,Date()),
        Score(7, "Difficile", 200,Date()),
        Score(8, "Difficile", 855,Date()),
        Score(8, "Difficile", 1,Date())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)
        spn_ld_difficulty.adapter = ArrayAdapter(this, R.layout.spinner_item, listOf("Facile","Normal","Difficile"))

        val itemDivider = DividerItemDecoration(applicationContext, 1)
        itemDivider.setDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.golden_divider)!!)
        rv_scores.addItemDecoration(itemDivider);

        scoresFacile.sortByDescending { it.turn }

        updateRecyclerView(scoresFacile)
        swipeContainer.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { refreshRecyclerView() })

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)

        spn_ld_difficulty.onItemSelectedListener = this

    }


    fun refreshRecyclerView() {
        scoresFacile.add(Score(9,"test",3333,Date(1111,10,15)))
        scoresFacile.sortByDescending { it.turn }
        updateRecyclerView(scoresFacile)
        swipeContainer.setRefreshing(false);

    }

    private fun updateRecyclerView(scores: List<Score>) {
        rv_scores?.apply {
            layoutManager = LinearLayoutManager(this@LeaderBoard)
            adapter = ScoresAdapter(scores)
        }
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text: String = parent?.getItemAtPosition(position).toString()
        Log.v("DIFFICULTE",text);
        if (text == "Facile") {
            updateRecyclerView(scoresFacile)
        } else if (text == "Normal") {
            updateRecyclerView(scoresMoyen)
        } else {
            updateRecyclerView(scoresDifficile)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

}