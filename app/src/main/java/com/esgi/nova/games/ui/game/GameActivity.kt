package com.esgi.nova.games.ui.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.esgi.nova.R
import com.esgi.nova.databinding.ActivityGameBinding
import com.esgi.nova.events.ports.IDetailedChoice
import com.esgi.nova.events.ports.IDetailedEvent
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.application.*
import com.esgi.nova.games.ports.ITotalValueResource
import com.esgi.nova.games.ui.endgame.EndGameActivity
import com.esgi.nova.games.ui.game.adapters.GameResourcesAdapter
import com.esgi.nova.games.ui.game.fragments.ChoiceDetailFragment
import com.esgi.nova.games.ui.game.fragments.ChoicesListFragment
import com.esgi.nova.games.ui.game.view_models.GameViewModel
import com.esgi.nova.ui.dashboard.DashboardActivity
import com.esgi.nova.utils.clear
import com.esgi.nova.utils.getUUIDExtra
import com.esgi.nova.utils.putUUIDExtra
import com.esgi.nova.utils.recyclerViewOrientation
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.contentView
import java.time.LocalTime
import java.util.*


@AndroidEntryPoint
class GameActivity : AppCompatActivity() {


    val viewModel by viewModels<GameViewModel>()


    private lateinit var binding: ActivityGameBinding


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
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.selectedChoice.observe(this) { choice -> onSelectedChoiceChanged(choice) }
        viewModel.isLoading.observe(this) { loading -> onLoadingChanged(loading) }
        viewModel.gameDuration.observe(this) { duration -> updateGameDuration(duration) }
        viewModel.newResources.observe(this) { resources -> updateResources(resources) }
        viewModel.rounds.observe(this) { rounds -> updateRounds(rounds) }
        viewModel.event.observe(this) { event -> updateEvent(event) }
        viewModel.changeResources.observe(this) { changesResources ->
            showChangeResourcesSnackBar(
                changesResources
            )
        }
        viewModel.unexpectedError.observe(this) { onUnexpectedError() }


        viewModel.gameEnded.observe(this) { isEnded -> onGameEnded(isEnded) }
        viewModel.initialize(intent.getUUIDExtra(DifficultyIdKey))
        intent.clear()
        initResources()

    }

    private fun onUnexpectedError() {
        Toast.makeText(this, R.string.unexpected_error_msg, Toast.LENGTH_SHORT).show()
        startDashboard()
    }

    private fun updateRounds(rounds: Int) {
        binding.roundTv.text = rounds.toString()
    }

    private fun updateResources(resources: List<IFileWrapper<ITotalValueResource>>) {
        runOnUiThread {
            viewModel.setGameResources(resources)
            binding.resourcesRv.adapter?.notifyDataSetChanged()
        }
    }

    private fun onGameEnded(ended: Boolean) {
        if (ended) {
            startEndGameActivity()
        }
    }

    private fun updateEvent(event: IFileWrapper<IDetailedEvent>) {
        binding.eventTitleTv.text = event.data.title
        binding.eventDescriptionTv.text = event.data.description
        binding.eventBackgroundImg.setImageBitmap(event.file)
        binding.root.loaderFl.bringToFront()
    }

    private fun initResources() {
        binding.resourcesRv.apply {
            layoutManager = LinearLayoutManager(
                this@GameActivity,
                resources.configuration.recyclerViewOrientation,
                false
            )
            adapter =
                GameResourcesAdapter(
                    viewModel.resources
                )
        }
    }

    private fun startDashboard() {
        DashboardActivity.start(this@GameActivity)
    }

    private fun startEndGameActivity() {
        EndGameActivity.start(this@GameActivity)
    }

    private fun onSelectedChoiceChanged(t: IDetailedChoice?) {
        if (t == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ChoicesListFragment.newInstance())
                .commitNow()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ChoiceDetailFragment.newInstance())
                .commitNow()
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.startTimer()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopTimer()
        viewModel.updateGame()

    }

    private fun updateGameDuration(duration: Int) {
        binding.timerTv.text =
            LocalTime.ofSecondOfDay(duration.toLong()).toString()
    }


    private fun showChangeResourcesSnackBar(changesResources: List<IFileWrapper<IDetailedChoice.IChangeValueResource>>) {
        contentView?.let {
            ResourcesChangeSnackBar.make(
                it, changesResources, ResourceSnackBarDurationMs
            )?.show()
        }
    }

    private fun onLoadingChanged(loading: Boolean?) {
        if (loading == true) {
            binding.root.loaderFl.visibility = View.VISIBLE
        } else {
            binding.root.loaderFl.visibility = View.GONE
        }
    }

}