package com.esgi.nova

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private var isLoading = false

    private val PAGE_SIZE = 10

    companion object{
        fun start(context: Context){
            val intent = Intent(context, LeaderBoardActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)

        scores_rv?.apply {
            layoutManager = LinearLayoutManager(this@LeaderBoardActivity)
            adapter = GamesAdapter(games)
        }

        val linearLayoutManager = scores_rv.layoutManager as LinearLayoutManager
        scores_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val llManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (llManager != null && llManager.findLastCompletelyVisibleItemPosition() == games.size - 1) {
                        if (games.size%10 == 0 ) {
                            loadMore()

                        }
                    }
                }
            }
        })

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

    private fun loadMore() {
        pb_load_more.visibility = ProgressBar.VISIBLE
        isLoading = true
        doAsync {
            val moreGames = getLeaderBoardGameList.execute(currentDifficulty.id, games.size/PAGE_SIZE, PAGE_SIZE)
            runOnUiThread {
                moreGames?.values?.forEach {
                    games.add(it)
                }
                games.sortBy { game -> game.eventCount }
                scores_rv.adapter?.notifyDataSetChanged()
                isLoading = false
                pb_load_more.visibility = ProgressBar.GONE
            }
        }
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


    private fun refreshRecyclerView() {
        scores_rv.visibility = View.GONE
        if (NetworkUtils.isNetworkAvailable(this)) {

            doAsync {
                try {
                    val execute = getLeaderBoardGameList.execute(
                        currentDifficulty.id,
                        0,
                        PAGE_SIZE
                    )
                    execute?.let {
                        games.clear()
                        games.addAll(it.values)
                        games.sortBy { game -> game.eventCount }
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

