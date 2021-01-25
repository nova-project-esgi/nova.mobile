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
import com.esgi.nova.difficulties.application.GetAllDetailedDifficultiesSortedByRank
import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.dtos.difficulty.DetailedDifficultyDto
import com.esgi.nova.games.application.GetLeaderBoardGamePageCursor
import com.esgi.nova.games.ui.leaderboard.adapters.GamesAdapter
import com.esgi.nova.games.ui.leaderboard.view_models.LeaderBoardViewModel
import com.esgi.nova.users.exceptions.InvalidDifficultyException
import com.esgi.nova.utils.NetworkUtils
import com.esgi.nova.utils.reflectMapCollection
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.doAsync
import javax.inject.Inject


@AndroidEntryPoint
class LeaderBoardActivity : AppCompatActivity(), AdapterView.OnItemClickListener {


    @Inject
    lateinit var getLeaderBoardPageCursor: GetLeaderBoardGamePageCursor

    @Inject
    lateinit var getAllDetailedDifficultiesSortedByRank: GetAllDetailedDifficultiesSortedByRank

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
        if (!viewModel.initialized) {
            doAsync {
                viewModel.difficulties = getAllDetailedDifficultiesSortedByRank.execute()
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

        binding.swipeContainer.setOnRefreshListener { reloadScoresRecyclerView() }
        binding.swipeContainer.setColorSchemeResources(
            R.color.primaryColor,
            R.color.secondaryColor
        )
        binding.tvLeaderBoardFilter.onItemClickListener = this

    }

    private fun initScoresRecyclerView() {
        binding.scoresRv.apply {
            layoutManager = LinearLayoutManager(this@LeaderBoardActivity)
            adapter = GamesAdapter(
                viewModel.cursor
            )
        }
        binding.scoresRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        binding.pbLoadMore.visibility = ProgressBar.VISIBLE
        viewModel.isLoading = true
        doAsync {
            viewModel.cursor.loadNext()
            viewModel.cursor.addAll(viewModel.cursor)
            runOnUiThread {
                binding.scoresRv.adapter?.notifyDataSetChanged()
                viewModel.isLoading = false
                binding.pbLoadMore.visibility = ProgressBar.GONE
            }
        }
    }

    private fun setDifficultiesView() {
        val arrayAdapter = ArrayAdapter(
            this@LeaderBoardActivity,
            R.layout.list_item,
            viewModel.difficulties
        )
        binding.tvLeaderBoardFilter.setAdapter(arrayAdapter)
        if (viewModel.difficulties.isNotEmpty()) {
            viewModel.currentDifficulty = viewModel.difficulties.firstOrNull()
            binding.tvLeaderBoardFilter.setText(viewModel.currentDifficulty.toString(), false)
            binding.swipeContainer.isRefreshing = true
            reloadScoresRecyclerView()
        } else {
            binding.tvLeaderBoardFilter.isEnabled = false
        }
        binding.tvLeaderBoardFilter.onItemClickListener = this@LeaderBoardActivity
    }


    private fun reloadScoresRecyclerView() {
        binding.scoresRv.visibility = View.GONE
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
                        binding.scoresRv.visibility = View.VISIBLE
                        binding.swipeContainer.isRefreshing = false
                    }
                } catch (e: InvalidDifficultyException) {
                    val toast = Toast.makeText(
                        this@LeaderBoardActivity,
                        getString(R.string.score_fetch_error_msg),
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                    binding.scoresRv.visibility = View.VISIBLE
                    binding.swipeContainer.isRefreshing = false
                }
            }

        } else {
            val toast = Toast.makeText(
                this,
                getString(R.string.network_not_available_msg),
                Toast.LENGTH_LONG
            )
            toast.show()
            binding.scoresRv.visibility = View.VISIBLE
            binding.swipeContainer.isRefreshing = false
        }
    }

    private fun verifyGameList() {
        if (viewModel.cursor.isEmpty()) {
            binding.tvNoGames.visibility = TextView.VISIBLE
        } else {
            binding.tvNoGames.visibility = TextView.GONE
        }
    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.currentDifficulty = parent?.getItemAtPosition(position) as DetailedDifficultyDto
        binding.swipeContainer.isRefreshing = true
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

