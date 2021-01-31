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
import com.esgi.nova.databinding.ActivityLeaderBoardBinding
import com.esgi.nova.dtos.difficulty.DetailedDifficultyDto
import com.esgi.nova.games.ports.ILeaderBoardGameView
import com.esgi.nova.games.ui.leaderboard.adapters.GamesAdapter
import com.esgi.nova.games.ui.leaderboard.view_models.LeaderBoardViewModel
import com.esgi.nova.ui.snackbars.IconSnackBar.Companion.infoSnackBar
import com.esgi.nova.ui.snackbars.IconSnackBar.Companion.networkErrorSnackBar
import com.esgi.nova.ui.snackbars.IconSnackBar.Companion.unexpectedErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LeaderBoardActivity : AppCompatActivity(), AdapterView.OnItemClickListener {


//    @Inject
//    lateinit var getLeaderBoardPageCursor: GetLeaderBoardGamePageCursor
//
//    @Inject
//    lateinit var getAllDetailedDifficultiesSortedByRank: GetAllDetailedDifficultiesSortedByRank

    private lateinit var binding: ActivityLeaderBoardBinding

    val viewModel by viewModels<LeaderBoardViewModel>()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LeaderBoardActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.difficulties.observe(this) { difficulties -> setDifficultiesView(difficulties) }
        viewModel.selectedDifficulty.observe(this) { selectedDifficulty ->
            selectDifficulty(
                selectedDifficulty
            )
        }
        viewModel.newScores.observe(this) { scores -> handleNewScores(scores) }
        viewModel.noMoreGames.observe(this) { noMoreGames -> displayNoMoreGamesMessage(noMoreGames) }
        viewModel.networkError.observe(this) { showNetworkErrorMessage() }
        viewModel.unexpectedError.observe(this) { showUnexpectedErrorMessage() }
        viewModel.emptyScores.observe(this) { isEmpty -> switchEmptyScoreMessage(isEmpty) }
        viewModel.isLoading.observe(this) { isLoading -> onLoading(isLoading) }
        viewModel.initialize()

        initScoresRecyclerView()
        initSwipeContainer()
        binding.tvLeaderBoardFilter.onItemClickListener = this
    }

    private fun onLoading(loading: Boolean?) {
        if (loading == true) {
            binding.root.loaderFl.visibility = View.VISIBLE
        } else {
            binding.root.loaderFl.visibility = View.GONE
            binding.swipeContainer.isRefreshing = loading == true
        }
    }

    private fun initSwipeContainer() {
        binding.swipeContainer.setOnRefreshListener { reloadScores() }
        binding.swipeContainer.setColorSchemeResources(
            R.color.primaryColor,
            R.color.secondaryColor
        )
    }

    private fun showUnexpectedErrorMessage() {
        binding.root.unexpectedErrorSnackBar()?.show()
    }

    private fun showNetworkErrorMessage() {
        binding.root.networkErrorSnackBar()?.show()
    }

    private fun handleNewScores(scores: List<ILeaderBoardGameView>) {
        runOnUiThread {
            viewModel.setScores(scores)
            binding.scoresRv.adapter?.notifyDataSetChanged()
            binding.pbLoadMore.visibility = ProgressBar.GONE
        }
    }


    private fun initScoresRecyclerView() {
        binding.scoresRv.apply {
            layoutManager = LinearLayoutManager(this@LeaderBoardActivity)
            adapter = GamesAdapter(
                viewModel.scores
            )
        }
        binding.scoresRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val llManager = recyclerView.layoutManager as LinearLayoutManager?
                llManager?.findLastCompletelyVisibleItemPosition()?.let { lastItemPosition ->
                    viewModel.loadMore(lastItemPosition)
                }
            }
        })
    }


    private fun selectDifficulty(selectedDifficulty: DetailedDifficultyDto) {
        binding.tvLeaderBoardFilter.isEnabled = true
        binding.tvLeaderBoardFilter.setText(selectedDifficulty.toString(), false)
    }

    private fun setDifficultiesView(difficulties: List<DetailedDifficultyDto>) {
        val arrayAdapter = ArrayAdapter(
            this@LeaderBoardActivity,
            R.layout.list_item,
            difficulties
        )
        binding.tvLeaderBoardFilter.setAdapter(arrayAdapter)
        binding.tvLeaderBoardFilter.isEnabled = false
    }


    private fun reloadScores() {
        binding.swipeContainer.isRefreshing = true
        viewModel.loadScores()
    }

    private fun switchEmptyScoreMessage(isEmptyScores: Boolean) {
        if (isEmptyScores) {
            binding.tvNoGames.visibility = TextView.VISIBLE
        } else {
            binding.tvNoGames.visibility = TextView.GONE
        }
    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.selectDifficulty(parent?.getItemAtPosition(position) as DetailedDifficultyDto)
    }


    private fun displayNoMoreGamesMessage(noMoreGames: Boolean) {
        if (noMoreGames) {
            binding.root.infoSnackBar(R.string.no_more_game_to_load)?.show()
        }
    }

}

