package com.esgi.nova.games.ui

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.DashboardActivity
import com.esgi.nova.R
import com.esgi.nova.events.ports.IDetailedChoice
import com.esgi.nova.games.application.*
import com.esgi.nova.games.ui.adapters.ResourcesAdapter
import com.esgi.nova.games.ui.fragments.ChoiceDetailFragment
import com.esgi.nova.games.ui.fragments.ChoicesListFragment
import com.esgi.nova.games.ui.fragments.OnChoiceConfirmedListener
import com.esgi.nova.games.ui.view_models.ChoicesListViewModel
import com.esgi.nova.games.ui.view_models.GameViewModel
import com.esgi.nova.utils.clear
import com.esgi.nova.utils.getUUIDExtra
import com.esgi.nova.utils.putUUIDExtra
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_event.*
import org.jetbrains.anko.doAsync
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class GameActivity : AppCompatActivity(), Observer<IDetailedChoice?>, OnChoiceConfirmedListener {

    @Inject
    lateinit var getCurrentGame: GetCurrentGame

    @Inject
    lateinit var getNextEvent: GetNextEvent

    @Inject
    lateinit var getCurrentEvent: GetCurrentEvent

    @Inject
    lateinit var createGame: CreateGame

    @Inject
    lateinit var confirmChoice: ConfirmChoice

    val choicesListViewModel by viewModels<ChoicesListViewModel>()
    val gameViewModel by viewModels<GameViewModel>()


    companion object {
        const val DifficultyIdKey = "DifficultyId"
        fun start(context: Context): Context {
            val intent = Intent(context, GameActivity::class.java)
            context.startActivity(intent)
            return context
        }

        fun newGame(context: Context, difficultyId: UUID): Context {
            val intent = Intent(context, GameActivity::class.java)
            intent.putUUIDExtra(DifficultyIdKey, difficultyId)
            context.startActivity(intent)
            return context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_event)
        choicesListViewModel.selected.observe(this@GameActivity, this@GameActivity)

        doAsync {
            if (!gameViewModel.initialized) {
                loadGame()
                gameViewModel.initialized = true
            }
            initGame()
        }
    }

    private fun initGame() {
        runOnUiThread {
            choicesListViewModel.setChoices(gameViewModel.event.data.choices)
            event_title_tv?.text = gameViewModel.event.data.title
            event_description_tv?.text = gameViewModel.event.data.description
            event_background_img?.setImageBitmap(gameViewModel.event.img)
            initResources()
            onChanged(choicesListViewModel.selected.value)
        }
    }

    private fun initResources() {
        resources_rv?.apply {
            val orientation =
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) RecyclerView.VERTICAL else RecyclerView.HORIZONTAL
            layoutManager = LinearLayoutManager(
                this@GameActivity,
                orientation,
                false
            )
            adapter =
                ResourcesAdapter(
                    gameViewModel.resources
                )
        }
    }

    private fun startDashboard() {
        DashboardActivity.start(this@GameActivity)
        finish()
    }

    private fun loadGame() {
        val difficultyId = intent.getUUIDExtra(DifficultyIdKey)
        if (difficultyId != null) {
            createGame(difficultyId)
            return
        } else {
            reloadGame()
        }
    }

    private fun reloadGame() {
        getCurrentGame.execute()?.let { game ->
            gameViewModel.copyGame(game)
            getCurrentEvent.execute(game.id)?.let { event ->
                gameViewModel.event = event
                return
            }
        }
        startDashboard()
    }

    private fun createGame(difficultyId: UUID) {
        createGame.execute(difficultyId)?.let { game ->
            gameViewModel.copyGame(game)
            getNextEvent.execute(game.id)?.let { event ->
                gameViewModel.event = event
            }
        }
        intent.clear()
    }

    private fun nextRound() {
        getCurrentGame.execute()?.let { game ->
            gameViewModel.copyGame(game)
            getNextEvent.execute(game.id)?.let { event ->
                gameViewModel.event = event
                initGame()
                return
            }
        }
        startDashboard()
    }


    override fun onChanged(t: IDetailedChoice?) {
        if (t == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ChoicesListFragment.newInstance())
                .commitNow()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ChoiceDetailFragment.newInstance(this))
                .commitNow()
        }

    }

    override fun onChoiceConfirmed(choice: IDetailedChoice) {
        doAsync {
            val isEnded =
                confirmChoice.execute(gameId = gameViewModel.id, choiceId = choice.id, duration = 0)
            if (isEnded) {
                DashboardActivity.start(this@GameActivity)
            } else {
                runOnUiThread {
                    choicesListViewModel.select(null)
                }
                nextRound()
            }
        }
    }


}