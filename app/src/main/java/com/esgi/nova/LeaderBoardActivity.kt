package com.esgi.nova

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.esgi.nova.adapters.GamesAdapter
import com.esgi.nova.difficulties.application.GetAllDetailedDifficulties
import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.dtos.difficulty.DetailedDifficultyDto
import com.esgi.nova.games.application.GetLeaderBoardGameList
import com.esgi.nova.games.infrastructure.dto.LeaderBoardGameView
import com.esgi.nova.users.exceptions.InvalidDifficultyException
import com.esgi.nova.utils.NetworkUtils
import com.esgi.nova.utils.reflectMapCollection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_leader_board.*
import org.jetbrains.anko.doAsync
import javax.inject.Inject

@AndroidEntryPoint
class LeaderBoardActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    @Inject
    lateinit var getLeaderBoardGameList: GetLeaderBoardGameList

    @Inject
    lateinit var getAllDetailedDifficulties: GetAllDetailedDifficulties

    private lateinit var difficulties: List<DetailedDifficultyDto>

    private lateinit var currentDifficulty: DetailedDifficultyDto

    private var games = mutableListOf<LeaderBoardGameView>()

    companion object{
        fun start(context: Context){
            val intent = Intent(context, LeaderBoardActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)

        val itemDivider = DividerItemDecoration(applicationContext, 1)
        itemDivider.setDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.golden_divider
            )!!
        )
        scores_rv.addItemDecoration(itemDivider)

        scores_rv?.apply {
            layoutManager = LinearLayoutManager(this@LeaderBoardActivity)
            adapter = GamesAdapter(games)
        }

        swipe_container.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { refreshRecyclerView() })
        swipe_container.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        tv_leaderBoard_filter.onItemClickListener = this
        generateDifficulties()

    }

    private fun generateDifficulties() {
        doAsync {
            try {
                val result = getAllDetailedDifficulties.execute()

                difficulties =
                    result.reflectMapCollection<IDetailedDifficulty, DetailedDifficultyDto>()
                        .toList()
                runOnUiThread {
                    val arrayAdapter = ArrayAdapter(
                        this@LeaderBoardActivity,
                        R.layout.list_item,
                        difficulties
                    )
                    tv_leaderBoard_filter.setAdapter(arrayAdapter)
                    tv_leaderBoard_filter.inputType = 0
                    if (difficulties.isNotEmpty()) {
                        currentDifficulty = difficulties[0]
                        tv_leaderBoard_filter.setText(difficulties[0].name, false)
                        swipe_container.isRefreshing = true
                        refreshRecyclerView()
                    } else {
                        tv_leaderBoard_filter.isEnabled = false
                    }
                    tv_leaderBoard_filter.onItemClickListener = this@LeaderBoardActivity
                }
            } catch (e: Error) {

            }
        }
    }


    fun refreshRecyclerView() {
        scores_rv.visibility = View.GONE
        if (NetworkUtils.isNetworkAvailable(this)) {

            doAsync {
                try {
                    val execute = getLeaderBoardGameList.execute(currentDifficulty.id)
                    execute?.let {
                        games.clear()
                        games.addAll(it.values)
                    }
                    runOnUiThread {
                        scores_rv.visibility = View.VISIBLE
                        swipe_container.setRefreshing(false)
                    }
                } catch (e: InvalidDifficultyException) {
                    val toast = Toast.makeText(
                        this@LeaderBoardActivity,
                        "Une erreur est survenue lors de la récupération des scores",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                    scores_rv.visibility = View.VISIBLE
                    swipe_container.isRefreshing = false
                }
            }

        } else {
            val toast = Toast.makeText(this, "Le réseau n'est pas disponible", Toast.LENGTH_LONG)
            toast.show()
            scores_rv.visibility = View.VISIBLE
            swipe_container.isRefreshing = false
        }
    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        currentDifficulty = parent?.getItemAtPosition(position) as DetailedDifficultyDto
        swipe_container.isRefreshing = true
        refreshRecyclerView()
    }

}