package com.esgi.nova.games.ui.endgame

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.R
import com.esgi.nova.games.application.GetLastEndedGame
import com.esgi.nova.games.ui.endgame.view_models.EndGameViewModel
import com.esgi.nova.games.ui.game.adapters.GameResourcesAdapter
import com.esgi.nova.ui.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_end_game.*
import kotlinx.android.synthetic.main.activity_event.*
import org.jetbrains.anko.doAsync
import javax.inject.Inject


@AndroidEntryPoint
class EndGameActivity : AppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var getLastEndedGame: GetLastEndedGame


    val endGameViewModel by viewModels<EndGameViewModel>()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, EndGameActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)

        doAsync {
            if (!endGameViewModel.initialized) {
                loadEndGameData()
            }
            initEndGameDisplay()
        }

        return_to_dashboard?.setOnClickListener(this)
    }

    private fun initEndGameDisplay() {
        runOnUiThread {
            end_title?.text = getString(R.string.end_game_title)
            end_message?.text = getString(R.string.end_game_message)
            turn_recap?.text = endGameViewModel.rounds.let { rounds ->
                resources.getQuantityString(R.plurals.turn_recap, rounds, rounds)
            }
            val losingResource = findLosingResourceName()
            resources_recap?.text = getString(R.string.end_game_resources_recap, losingResource)
            initResources()
        }
    }

    private fun findLosingResourceName(): String? {
        return endGameViewModel.resources
            .firstOrNull { resource -> resource.data.total == 0 }?.data?.name
    }

    private fun loadEndGameData() {
        getLastEndedGame.execute()?.let { game ->
            game.let { endGameViewModel.populate(game) }
        }

    }

    private fun initResources() {

        resources_recap_rv?.apply {
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
            return_to_dashboard ->
                DashboardActivity.start(this@EndGameActivity)
        }
    }

}