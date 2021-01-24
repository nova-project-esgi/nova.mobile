package com.esgi.nova.games.ui.game

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.R
import com.esgi.nova.events.ports.IDetailedChoice
import com.esgi.nova.files.dtos.FileWrapperDto
import com.esgi.nova.games.application.*
import com.esgi.nova.games.ui.endgame.EndGameActivity
import com.esgi.nova.games.ui.game.adapters.GameResourcesAdapter
import com.esgi.nova.games.ui.game.fragments.ChoiceDetailFragment
import com.esgi.nova.games.ui.game.fragments.ChoicesListFragment
import com.esgi.nova.games.ui.game.fragments.OnChoiceConfirmedListener
import com.esgi.nova.games.ui.game.view_models.ChoicesListViewModel
import com.esgi.nova.games.ui.game.view_models.GameViewModel
import com.esgi.nova.ui.dashboard.DashboardActivity
import com.esgi.nova.utils.clear
import com.esgi.nova.utils.getUUIDExtra
import com.esgi.nova.utils.putUUIDExtra
import com.esgi.nova.utils.recyclerViewOrientation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_game.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.doAsync
import java.time.LocalTime
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

    @Inject
    lateinit var updateGame: UpdateGame


    val choicesListViewModel by viewModels<ChoicesListViewModel>()
    val gameViewModel by viewModels<GameViewModel>()


    companion object {
        const val DifficultyIdKey = "DifficultyId"
        const val ResourceSnackBarDurationMs = 2000
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

        setContentView(R.layout.activity_game)
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
            event_background_img?.setImageBitmap(gameViewModel.event.file)
            round_tv?.text = gameViewModel.rounds.toString()
            initResources()
            onChanged(choicesListViewModel.selected.value)
        }
    }

    private fun initResources() {
        resources_rv?.apply {

            layoutManager = LinearLayoutManager(
                this@GameActivity,
                resources.configuration.recyclerViewOrientation,
                false
            )
            adapter =
                GameResourcesAdapter(
                    gameViewModel.resources
                )
        }
    }

    private fun startDashboard() {
        runOnUiThread {
            DashboardActivity.start(this@GameActivity)
            finish()
        }

    }

    private fun startEndGameActivity() {
        runOnUiThread {
            EndGameActivity.start(this@GameActivity)
            finish()
        }

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
            getNextEvent.execute(game.id)?.let { event ->
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
                runOnUiThread {
                    choicesListViewModel.select(null);
                }
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


    override fun onStart() {
        super.onStart()
        startTimer()
    }

    override fun onStop() {
        super.onStop()
        stopTimer()
        doAsync {
            updateGame.execute(gameViewModel)
        }
    }

    override fun onChoiceConfirmed(choice: IDetailedChoice) {
        doAsync {
            gameViewModel.isEnded =
                confirmChoice.execute(
                    gameId = gameViewModel.id,
                    choiceId = choice.id,
                    duration = gameViewModel.duration
                )
            if (gameViewModel.isEnded) {
                startEndGameActivity()
            } else {
                showChangeResourcesSnackBar()
                nextRound()
            }
        }
    }

    private fun stopTimer() {
        gameViewModel.timer?.let { timer ->
            timer.cancel()
            gameViewModel.timer = null
        }
    }

    private fun startTimer() {
        if (gameViewModel.timer != null) {
            return
        }
        gameViewModel.timer = Timer()
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    gameViewModel.duration++
                    timer_tv?.text =
                        LocalTime.ofSecondOfDay(gameViewModel.duration.toLong()).toString()
                }
            }
        }
        gameViewModel.timer?.scheduleAtFixedRate(timerTask, 0, 1000)
    }


    private fun showChangeResourcesSnackBar() {
        choicesListViewModel.selected.value?.let { choice ->
            val choiceResourcesChanges = getFileWrapperForSelectedChoiceResources(choice)
            contentView?.let {
                ResourcesChangeSnackBar.make(
                    it, choiceResourcesChanges, ResourceSnackBarDurationMs
                )?.show()
            }
        }
    }

    private fun getFileWrapperForSelectedChoiceResources(choice: IDetailedChoice): List<FileWrapperDto<IDetailedChoice.IChangeValueResource>> {
        return choice.resources.mapNotNull { choiceResource ->
            gameViewModel.resources
                .firstOrNull { gameResource -> gameResource.data.id == choiceResource.id }
                ?.let { gameResource ->
                    FileWrapperDto(choiceResource, gameResource.file)
                }
        }
    }

}