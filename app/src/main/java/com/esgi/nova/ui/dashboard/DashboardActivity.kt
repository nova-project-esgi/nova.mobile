package com.esgi.nova.ui.dashboard

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.R
import com.esgi.nova.databinding.ActivityDashboardBinding
import com.esgi.nova.difficulties.application.GetAllDetailedDifficultiesSortedByRank
import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.dtos.difficulty.DetailedDifficultyDto
import com.esgi.nova.games.application.CreateGame
import com.esgi.nova.games.infrastructure.data.game.models.CanLaunchGame
import com.esgi.nova.games.infrastructure.data.game.models.CanResumeGame
import com.esgi.nova.games.ui.game.GameActivity
import com.esgi.nova.games.ui.leaderboard.LeaderBoardActivity
import com.esgi.nova.parameters.ui.ParametersActivity
import com.esgi.nova.resources.application.GetImageStartValueResourceWrappersByDifficultyId
import com.esgi.nova.sound.services.BackgroundSoundService
import com.esgi.nova.ui.dashboard.adapters.DashBoardResourcesAdapter
import com.esgi.nova.ui.dashboard.view_models.DashboardViewModel
import com.esgi.nova.users.ui.LoginActivity
import com.esgi.nova.utils.reflectMapCollection
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.doAsync
import javax.inject.Inject


@AndroidEntryPoint
class DashboardActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemClickListener {

    @Inject
    lateinit var createGame: CreateGame

    @Inject
    lateinit var getAllDetailedDifficultiesSortedByRank: GetAllDetailedDifficultiesSortedByRank

    @Inject
    lateinit var getImageStartValueResourceWrappersByDifficultyId: GetImageStartValueResourceWrappersByDifficultyId

    @Inject
    lateinit var canLaunchGame: CanLaunchGame

    @Inject
    lateinit var canResumeGame: CanResumeGame

    val dashBoardViewModel by viewModels<DashboardViewModel>()

    private lateinit var binding: ActivityDashboardBinding


    companion object {
        fun start(context: Context) {
            val intent = Intent(context, DashboardActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onBackPressed() {
        LoginActivity.startReconnection(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.initNewGameBtn.setOnClickListener(this)
        binding.resumeGameBtn.setOnClickListener(this)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        BackgroundSoundService.start(this)

        changeActionsButtonsStates()
        initResourcesRecyclerView()
        generateDifficulties()
    }

    private fun changeActionsButtonsStates() {
        doAsync {
            val canLaunch = canLaunchGame.execute()
            val canResume = canResumeGame.execute()
            runOnUiThread {
                binding.initNewGameBtn.isEnabled = canLaunch
                binding.resumeGameBtn.isEnabled = canResume
            }
        }
    }

    override fun onResume() {
        changeActionsButtonsStates()
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.dashboard_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.leaderboard_btn -> LeaderBoardActivity.start(
                this
            )
            R.id.settings_btn -> ParametersActivity.start(
                this
            )
        }
        return super.onOptionsItemSelected(item)
    }

    private fun generateDifficulties() {

        if (!dashBoardViewModel.initialized) {
            binding.root.loaderFl.visibility = VISIBLE
            doAsync {
                if (!dashBoardViewModel.initialized) {
                    dashBoardViewModel.difficulties = getAllDetailedDifficultiesSortedByRank
                        .execute()
                        .reflectMapCollection<IDetailedDifficulty, DetailedDifficultyDto>()
                        .toMutableList()
                    dashBoardViewModel.initialized = true
                    dashBoardViewModel.selectedDifficulty = dashBoardViewModel.difficulties.first()
                    updateResources()
                }
                runOnUiThread {
                    setDifficultyAutoComplete()
                    binding.root.loaderFl.visibility = GONE
                }
            }
        } else {
            setDifficultyAutoComplete()
        }

    }

    private fun setDifficultyAutoComplete() {
        val arrayAdapter = ArrayAdapter(
            this,
            R.layout.list_item,
            dashBoardViewModel.difficulties
        )
        binding.difficultyTv.setAdapter(arrayAdapter)
        if (dashBoardViewModel.selectedDifficulty != null) {
            binding.difficultyTv.setText(dashBoardViewModel.selectedDifficulty.toString(), false)
            binding.difficultyTv.isEnabled = true
        } else {
            binding.difficultyTv.isEnabled = false
        }
        binding.difficultyTv.onItemClickListener = this
    }


    override fun onClick(view: View?) {
        when (view) {
            binding.initNewGameBtn -> dashBoardViewModel.selectedDifficulty?.let { selectedDifficulty ->
                GameActivity.newGame(
                    this@DashboardActivity,
                    selectedDifficulty.id
                )
            }

            binding.resumeGameBtn -> GameActivity.start(this@DashboardActivity)
        }

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        dashBoardViewModel.selectedDifficulty =
            parent?.getItemAtPosition(position) as DetailedDifficultyDto
        updateResources()

    }

    private fun updateResources() {
        doAsync {
            dashBoardViewModel.selectedDifficulty?.let { selectedDifficulty ->
                val resources =
                    getImageStartValueResourceWrappersByDifficultyId.execute(selectedDifficulty.id)
                runOnUiThread {
                    dashBoardViewModel.wrapperResources.clear()
                    dashBoardViewModel.wrapperResources.addAll(resources)
                    binding.resourcesRv.adapter?.notifyDataSetChanged()
                }
            }

        }
    }

    private fun initResourcesRecyclerView() {
        binding.resourcesRv.apply {
            layoutManager =
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    LinearLayoutManager(
                        this@DashboardActivity, RecyclerView.HORIZONTAL, false
                    )
                } else {
                    GridLayoutManager(
                        this@DashboardActivity,
                        3
                    )
                }
            adapter =
                DashBoardResourcesAdapter(
                    dashBoardViewModel.wrapperResources
                )
        }
    }


}