package com.esgi.nova

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.esgi.nova.adapters.ScoresAdapter
import com.esgi.nova.models.Score
import com.esgi.nova.network.score.ScoreRepository
import com.esgi.nova.utils.NetworkUtils
import kotlinx.android.synthetic.main.activity_leader_board.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LeaderBoard : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var scores = mutableListOf<Score>(
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)
        spn_ld_difficulty.adapter = ArrayAdapter(this, R.layout.spinner_item, listOf("Facile","Normal","Difficile"))

        val itemDivider = DividerItemDecoration(applicationContext, 1)
        itemDivider.setDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.golden_divider)!!)
        rv_scores.addItemDecoration(itemDivider);

        scores.sortByDescending { it.turn }
        rv_scores?.apply {
            layoutManager = LinearLayoutManager(this@LeaderBoard)
            adapter = ScoresAdapter(scores)
        }

        swipeContainer.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { refreshRecyclerView() })

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)

        spn_ld_difficulty.onItemSelectedListener = this

    }


    fun refreshRecyclerView() {
        rv_scores.visibility = View.GONE
        if (NetworkUtils.isNetworkAvailable(this)) {
            ScoreRepository.retrieveUser(object: Callback<Score> {
                override fun onResponse(call: Call<Score>, response: Response<Score>) {
                    response.body()?.let {
                        scores.add(it)
                    }
                    scores.sortByDescending { it.turn }
                    rv_scores.visibility = View.VISIBLE
                    swipeContainer.setRefreshing(false)
                }

                override fun onFailure(call: Call<Score>, t: Throwable) {
                    val toast = Toast.makeText(this@LeaderBoard, "Une erreur est survenue lors de la récupération des scores", Toast.LENGTH_LONG)
                    toast.show()
                    scores.clear()
                    rv_scores.visibility = View.VISIBLE
                    swipeContainer.setRefreshing(false)
                }
            })

        } else {
            val toast = Toast.makeText(this, "Le réseau n'est pas disponible", Toast.LENGTH_LONG)
            toast.show()
            scores.clear()
            rv_scores.visibility = View.VISIBLE
            swipeContainer.setRefreshing(false)
        }

    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text: String = parent?.getItemAtPosition(position).toString()
        Log.v("DIFFICULTE",text);
        //TODO changer la valeur de difficulté avant de lancer la requete de récupération
        refreshRecyclerView()
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

}