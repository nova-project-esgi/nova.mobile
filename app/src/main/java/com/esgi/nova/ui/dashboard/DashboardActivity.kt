package com.esgi.nova.ui.dashboard

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.LeaderBoardActivity
import com.esgi.nova.R
import com.esgi.nova.difficulties.application.GetAllDetailedDifficulties
import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.dtos.difficulty.DetailedDifficultyDto
import com.esgi.nova.games.application.CreateGame
import com.esgi.nova.games.infrastructure.data.game.models.CanLaunchGame
import com.esgi.nova.games.infrastructure.data.game.models.CanResumeGame
import com.esgi.nova.games.ui.GameActivity
import com.esgi.nova.parameters.ui.ParametersActivity
import com.esgi.nova.resources.application.GetImageResourceWrappersByDifficultyId
import com.esgi.nova.ui.dashboard.adapters.DashBoardResourcesAdapter
import com.esgi.nova.ui.dashboard.view_models.DashboardViewModel
import com.esgi.nova.utils.reflectMapCollection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.jetbrains.anko.doAsync
import javax.inject.Inject


@AndroidEntryPoint
class DashboardActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemClickListener {

    @Inject
    lateinit var createGame: CreateGame

    @Inject
    lateinit var getAllDetailedDifficulties: GetAllDetailedDifficulties

    @Inject
    lateinit var getImageResourceWrappersByDifficultyId: GetImageResourceWrappersByDifficultyId

    @Inject
    lateinit var canLaunchGame: CanLaunchGame

    @Inject
    lateinit var canResumeGame: CanResumeGame

    val dashBoardViewModel by viewModels<DashboardViewModel>()



    companion object {
        fun start(context: Context) {
            val intent = Intent(context, DashboardActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        init_new_game_btn.setOnClickListener(this)
        resume_game_btn.setOnClickListener(this)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        doAsync {
            val canLaunch = canLaunchGame.execute()
            val canResume = canResumeGame.execute()
            runOnUiThread {
                init_new_game_btn.isEnabled = canLaunch
                resume_game_btn.isEnabled = canResume
            }
        }
        initResourcesRecyclerView()
        generateDifficulties()
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
            doAsync {
                if (!dashBoardViewModel.initialized) {
                    dashBoardViewModel.difficulties = getAllDetailedDifficulties
                        .execute()
                        .reflectMapCollection<IDetailedDifficulty, DetailedDifficultyDto>()
                        .toMutableList()
                    dashBoardViewModel.initialized = true
                    dashBoardViewModel.selectedDifficulty = dashBoardViewModel.difficulties.first()
                    updateResources()
                }
                runOnUiThread {
                    setDifficultyAutoComplete()
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
        difficulty_tv?.setAdapter(arrayAdapter)
        if (dashBoardViewModel.selectedDifficulty != null) {
            difficulty_tv?.setText(dashBoardViewModel.selectedDifficulty?.name, false)
        } else {
            difficulty_tv?.isEnabled = false
        }
        difficulty_tv?.onItemClickListener = this
    }


    override fun onClick(view: View?) {
        when (view) {
            init_new_game_btn -> dashBoardViewModel.selectedDifficulty?.let { selectedDifficulty ->
                GameActivity.newGame(
                    this@DashboardActivity,
                    selectedDifficulty.id
                )
            }

            resume_game_btn -> GameActivity.start(this@DashboardActivity)
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
                    getImageResourceWrappersByDifficultyId.execute(selectedDifficulty.id)
                runOnUiThread {
                    dashBoardViewModel.wrapperResources.clear()
                    dashBoardViewModel.wrapperResources.addAll(resources)
                    resources_rv?.adapter?.notifyDataSetChanged()
                }
            }

        }
    }

    private fun initResourcesRecyclerView() {
        resources_rv?.apply {
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