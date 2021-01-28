package com.esgi.nova.games.ui.endgame

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.R
import com.esgi.nova.databinding.ActivityEndGameBinding
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.ports.ITotalValueResource
import com.esgi.nova.games.ui.endgame.view_models.EndGameViewModel
import com.esgi.nova.games.ui.game.adapters.GameResourcesAdapter
import com.esgi.nova.ui.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EndGameActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityEndGameBinding
    private val viewModel by viewModels<EndGameViewModel>()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, EndGameActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onBackPressed() {
        navigateToDashboard()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEndGameBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.rounds.observe(this) { rounds -> setRounds(rounds) }
        viewModel.newResources.observe(this) { resources -> updateResources(resources) }
        viewModel.isLoading.observe(this) { isLoading -> onLoading(isLoading) }
        viewModel.loosingResource.observe(this) { loosingResource ->
            setLoosingResource(
                loosingResource
            )
        }
        viewModel.unexpectedError.observe(this) { onUnexpectedError() }
        viewModel.initialize()

        initResources()
        binding.returnToDashboard.setOnClickListener(this)
    }

    private fun onUnexpectedError() {
        Toast.makeText(this, R.string.unexpected_error_msg, Toast.LENGTH_SHORT).show()
        navigateToDashboard()
    }

    private fun setLoosingResource(loosingResource: String?) {
        binding.resourcesRecap.text = getString(R.string.end_game_resources_recap, loosingResource)
    }

    private fun onLoading(loading: Boolean?) {
        if (loading == true) {
            binding.root.loaderFl.visibility = View.VISIBLE
        } else {
            binding.root.loaderFl.visibility = View.GONE
        }
    }

    private fun updateResources(resources: List<IFileWrapper<ITotalValueResource>>) {
        runOnUiThread {
            viewModel.setResources(resources)
            binding.resourcesRecapRv.adapter?.notifyDataSetChanged()
        }
    }

    private fun setRounds(rounds: Int) {
        binding.turnRecap.text = resources.getQuantityString(R.plurals.turn_recap, rounds, rounds)
    }

    private fun initResources() {

        binding.resourcesRecapRv.apply {
            val orientation = RecyclerView.HORIZONTAL
            layoutManager = LinearLayoutManager(
                this@EndGameActivity,
                orientation,
                false
            )
            adapter = GameResourcesAdapter(viewModel.resources)
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.returnToDashboard -> navigateToDashboard()
        }
    }

    private fun navigateToDashboard() {
        DashboardActivity.start(this@EndGameActivity)
    }

}