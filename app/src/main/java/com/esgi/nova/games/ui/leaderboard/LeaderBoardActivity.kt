package com.esgi.nova.games.ui.leaderboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.R
import com.esgi.nova.difficulties.application.GetAllDetailedDifficulties
import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.dtos.difficulty.DetailedDifficultyDto
import com.esgi.nova.games.application.GetLeaderBoardGameList
import com.esgi.nova.games.application.GetLeaderBoardGamePageCursor
import com.esgi.nova.games.ui.leaderboard.adapters.GamesAdapter
import com.esgi.nova.games.ui.leaderboard.view_models.LeaderBoardViewModel
import com.esgi.nova.users.exceptions.InvalidDifficultyException
import com.esgi.nova.utils.NetworkUtils
import com.esgi.nova.utils.reflectMapCollection
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.activity_leader_board.*
import org.jetbrains.anko.doAsync
import javax.inject.Inject


@AndroidEntryPoint
class LeaderBoardActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    @Inject
    lateinit var getLeaderBoardGameList: GetLeaderBoardGameList

    @Inject
    lateinit var getLeaderBoardPageCursor: GetLeaderBoardGamePageCursor

    @Inject
    lateinit var getAllDetailedDifficulties: GetAllDetailedDifficulties


    val viewModel by viewModels<LeaderBoardViewModel>()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LeaderBoardActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)

        if (!viewModel.initialized) {
            doAsync {
                viewModel.difficulties = getAllDetailedDifficulties.execute()
                    .reflectMapCollection<IDetailedDifficulty, DetailedDifficultyDto>()
                    .toList()
                runOnUiThread {
                    setDifficultiesView()
                }
            }
            viewModel.initialized = true
        } else {
            setDifficultiesView()
        }

        initScoresRecyclerView()

        swipe_container?.setOnRefreshListener { reloadScoresRecyclerView() }
        swipe_container?.setColorSchemeResources(
            R.color.primaryColor,
            R.color.secondaryColor
        )
        tv_leaderBoard_filter?.onItemClickListener = this

    }

    private fun initScoresRecyclerView() {
        scores_rv?.apply {
            layoutManager = LinearLayoutManager(this@LeaderBoardActivity)
            adapter = GamesAdapter(
                viewModel.cursor
            )
        }
        scores_rv?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val llManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!viewModel.isLoading) {
                    if (llManager?.findLastCompletelyVisibleItemPosition() == viewModel.cursor.size - 1 && viewModel.cursor.hasNext == true) {
                        loadMore()
                    } else {
                        displayNoMoreGamesMessage()
                    }
                }
            }
        })
    }

    private fun loadMore() {
        pb_load_more?.visibility = ProgressBar.VISIBLE
        viewModel.isLoading = true
        doAsync {
            viewModel.cursor.loadNext()
            viewModel.cursor.addAll(viewModel.cursor)
            runOnUiThread {
                scores_rv?.adapter?.notifyDataSetChanged()
                viewModel.isLoading = false
                pb_load_more?.visibility = ProgressBar.GONE
            }
        }
    }

    private fun setDifficultiesView() {
        val arrayAdapter = ArrayAdapter(
            this@LeaderBoardActivity,
            R.layout.list_item,
            viewModel.difficulties
        )
        tv_leaderBoard_filter?.setAdapter(arrayAdapter)
        if (viewModel.difficulties.isNotEmpty()) {
            viewModel.currentDifficulty = viewModel.difficulties.firstOrNull()
            tv_leaderBoard_filter?.setText(viewModel.currentDifficulty?.name, false)
            swipe_container?.isRefreshing = true
            reloadScoresRecyclerView()
        } else {
            tv_leaderBoard_filter?.isEnabled = false
        }
        tv_leaderBoard_filter?.onItemClickListener = this@LeaderBoardActivity
    }


    private fun reloadScoresRecyclerView() {
        scores_rv?.visibility = View.GONE
        if (NetworkUtils.isNetworkAvailable(this)) {

            doAsync {
                try {
                    viewModel.currentDifficulty?.id?.let { difficultyId ->
                        getLeaderBoardPageCursor.execute(
                            difficultyId
                        )
                    }?.let { viewModel.cursor.copy(it) }
                    viewModel.cursor.addAll(viewModel.cursor.loadCurrent())
                    runOnUiThread {
                        verifyGameList()
                        scores_rv?.visibility = View.VISIBLE
                        swipe_container?.isRefreshing = false
                    }
                } catch (e: InvalidDifficultyException) {
                    val toast = Toast.makeText(
                        this@LeaderBoardActivity,
                        getString(R.string.score_fetch_error_msg),
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                    scores_rv?.visibility = View.VISIBLE
                    swipe_container?.isRefreshing = false
                }
            }

        } else {
            val toast = Toast.makeText(
                this,
                getString(R.string.network_not_available_msg),
                Toast.LENGTH_LONG
            )
            toast.show()
            scores_rv?.visibility = View.VISIBLE
            swipe_container?.isRefreshing = false
        }
    }

    private fun verifyGameList() {
        if (viewModel.cursor.isEmpty()) {
            tv_no_games.visibility = TextView.VISIBLE
        } else {
            tv_no_games.visibility = TextView.GONE
        }
    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.currentDifficulty = parent?.getItemAtPosition(position) as DetailedDifficultyDto
        swipe_container?.isRefreshing = true
        reloadScoresRecyclerView()
    }


    private fun displayNoMoreGamesMessage() {
        if (viewModel.cursor.isNotEmpty()){
            val toast = Toast.makeText(
                this@LeaderBoardActivity,
                getString(R.string.no_more_game_to_load),
                Toast.LENGTH_SHORT
            )
            toast.show()
        }
    }

}

