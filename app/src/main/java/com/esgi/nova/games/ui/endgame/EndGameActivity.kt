package com.esgi.nova.games.ui.endgame

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.R
import com.esgi.nova.databinding.ActivityEndGameBinding
import com.esgi.nova.games.ports.IRecappedGameWithResourceIcons
import com.esgi.nova.games.ui.endgame.view_models.EndGameViewModel
import com.esgi.nova.games.ui.game.adapters.GameResourcesAdapter
import com.esgi.nova.ui.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EndGameActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityEndGameBinding
    private val endGameViewModel by viewModels<EndGameViewModel>()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, EndGameActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onBackPressed() {
        DashboardActivity.start(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEndGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        endGameViewModel.recappedGame.observe(this) { game ->
            initEndGameDisplay(game)
        }
        endGameViewModel.initialize()
        binding.returnToDashboard.setOnClickListener(this)
    }

    private fun initEndGameDisplay(game: IRecappedGameWithResourceIcons) {
        runOnUiThread {
            binding.endTitle.text = getString(R.string.end_game_title)
            binding.endMessage.text = getString(R.string.end_game_message)
            binding.turnRecap.text = endGameViewModel.rounds?.let { rounds ->
                resources.getQuantityString(R.plurals.turn_recap, rounds, rounds)
            }
            binding.resourcesRecap.text =
                getString(R.string.end_game_resources_recap, endGameViewModel.loosingResource)
            initResources()
        }
    }


    private fun initResources() {

        binding.resourcesRecapRv.apply {
            val orientation = RecyclerView.HORIZONTAL
            layoutManager = LinearLayoutManager(
                this@EndGameActivity,
                orientation,
                false
            )
            adapter = endGameViewModel.resources?.let { GameResourcesAdapter(it) }
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.returnToDashboard ->
                DashboardActivity.start(this@EndGameActivity)
        }
    }

}