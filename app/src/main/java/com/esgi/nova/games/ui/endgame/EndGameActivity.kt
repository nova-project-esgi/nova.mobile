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
import com.esgi.nova.games.application.GetLastEndedGame
import com.esgi.nova.games.ui.endgame.view_models.EndGameViewModel
import com.esgi.nova.games.ui.game.adapters.GameResourcesAdapter
import com.esgi.nova.ui.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.doAsync
import javax.inject.Inject


@AndroidEntryPoint
class EndGameActivity : AppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var getLastEndedGame: GetLastEndedGame

    private lateinit var binding: ActivityEndGameBinding
    val endGameViewModel by viewModels<EndGameViewModel>()

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
        if (!endGameViewModel.initialized) {
            doAsync {
                loadEndGameData()
                initEndGameDisplay()
            }
        } else {
            initEndGameDisplay()
        }




        binding.returnToDashboard.setOnClickListener(this)
    }

    private fun initEndGameDisplay() {
        runOnUiThread {
            binding.endTitle.text = getString(R.string.end_game_title)
            binding.endMessage.text = getString(R.string.end_game_message)
            binding.turnRecap.text = endGameViewModel.rounds.let { rounds ->
                resources.getQuantityString(R.plurals.turn_recap, rounds, rounds)
            }
            val losingResource = findLosingResourceName()
            binding.resourcesRecap.text =
                getString(R.string.end_game_resources_recap, losingResource)
            initResources()
        }
    }

    private fun findLosingResourceName(): String? {
        return endGameViewModel.resources
            .firstOrNull { resource -> resource.data.total == 0 }?.data?.name
    }

    private suspend fun loadEndGameData() {
        getLastEndedGame.execute()?.let { game ->
            game.let { endGameViewModel.populate(game) }
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
            adapter = GameResourcesAdapter(endGameViewModel.resources)
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.returnToDashboard ->
                DashboardActivity.start(this@EndGameActivity)
        }
    }

}