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
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esgi.nova.R
import com.esgi.nova.databinding.ActivityDashboardBinding
import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.dtos.difficulty.DetailedDifficultyDto
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.games.ui.game.GameActivity
import com.esgi.nova.games.ui.leaderboard.LeaderBoardActivity
import com.esgi.nova.parameters.ui.ParametersActivity
import com.esgi.nova.ui.dashboard.adapters.DashBoardResourcesAdapter
import com.esgi.nova.ui.dashboard.view_models.BaseDashboardViewModel
import com.esgi.nova.ui.init.InitSetupActivity
import com.esgi.nova.ui.snackbars.IconSnackBar.Companion.confirmSnackBar
import com.esgi.nova.users.ui.LoginActivity
import com.esgi.nova.utils.clear
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DashboardActivity : AppCompatActivity(), View.OnClickListener,
    AdapterView.OnItemClickListener {


    private var menu: Menu? = null

    @Inject
    lateinit var viewModelFactory: IDashboardViewModelFactory
    private lateinit var viewModel: BaseDashboardViewModel

    private lateinit var binding: ActivityDashboardBinding


    companion object {
        const val ParameterSavedKey = "ParameterSaved"
        fun start(context: Context) {
            val intent = Intent(context, DashboardActivity::class.java)
            context.startActivity(intent)
        }

        fun startFromSavedParameters(context: Context) {
            val intent = Intent(context, DashboardActivity::class.java)
            intent.putExtra(ParameterSavedKey, true)
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

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(BaseDashboardViewModel::class.java)

        binding.initNewGameBtn.setOnClickListener(this)
        binding.resumeGameBtn.setOnClickListener(this)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        viewModel.canLaunch.observe(this) { canLaunch ->
            binding.initNewGameBtn.isEnabled = canLaunch
        }
        viewModel.showParameterSaved.observe(this) {
            showParameterSaved()
        }
        viewModel.canResume.observe(this) { canResume ->
            binding.resumeGameBtn.isEnabled = canResume
        }
        viewModel.newResources.observe(this) { newResources ->
            updateResources(newResources)
        }
        viewModel.isLoading.observe(this) { isLoading ->
            onLoading(isLoading)
        }
        viewModel.difficulties.observe(this) { difficulties ->
            setDifficultyAutoComplete(
                difficulties
            )
        }
        viewModel.selectedDifficulty.observe(this) { selectedDifficulty ->
            setSelectedDifficulty(
                selectedDifficulty
            )
        }
        viewModel.initialize(intent.getBooleanExtra(ParameterSavedKey, false))
        intent.clear()
        initResourcesRecyclerView()
    }

    private fun setSelectedDifficulty(selectedDifficulty: DetailedDifficultyDto) {
        binding.difficultyTv.setText(selectedDifficulty.toString(), false)
    }

    private fun changeActionsButtonsStates() {
        binding.initNewGameBtn.isEnabled = viewModel.canLaunch.value == true
        binding.resumeGameBtn.isEnabled = viewModel.canResume.value == true
    }

    override fun onResume() {
        updateUpdateBtnVisibility()
        viewModel.initialize(intent.getBooleanExtra(ParameterSavedKey, false))
        super.onResume()
    }

    private fun showParameterSaved() {
        binding.root.confirmSnackBar(R.string.settings_saved)?.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(m: Menu?): Boolean {
        menu = m
        updateUpdateBtnVisibility()
        return super.onPrepareOptionsMenu(menu)
    }

    private fun updateUpdateBtnVisibility() {
        menu?.findItem(R.id.update_btn)?.let { btn ->
            btn.isVisible = !viewModel.isSynchronized()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.leaderboard_btn -> LeaderBoardActivity.start(
                this
            )
            R.id.settings_btn -> ParametersActivity.start(
                this
            )
            R.id.update_btn -> InitSetupActivity.start(
                this
            )
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setDifficultyAutoComplete(difficulties: List<DetailedDifficultyDto>) {

        val arrayAdapter = ArrayAdapter(
            this,
            R.layout.list_item,
            difficulties
        )
        binding.difficultyTv.setAdapter(arrayAdapter)
        binding.difficultyTv.isEnabled = true
        binding.difficultyTv.onItemClickListener = this

    }


    override fun onClick(view: View?) {
        when (view) {
            binding.initNewGameBtn -> viewModel.selectedDifficulty.value?.let { selectedDifficulty ->
                GameActivity.newGame(
                    this@DashboardActivity,
                    selectedDifficulty.id
                )
            }
            binding.resumeGameBtn -> GameActivity.start(this@DashboardActivity)
        }

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedDifficulty = parent?.getItemAtPosition(position) as DetailedDifficultyDto
        viewModel.selectDifficulty(selectedDifficulty)
    }

    private fun updateResources(newResources: List<IFileWrapper<IDetailedDifficulty.IStartValueResource>>) {
        runOnUiThread {
            viewModel.setResourcesWrappers(newResources)
            binding.resourcesRv.adapter?.notifyDataSetChanged()
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
            adapter = DashBoardResourcesAdapter(
                viewModel.wrapperResources
            )
        }
    }

    private fun onLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.resumeGameBtn.isEnabled = false
            binding.initNewGameBtn.isEnabled = false
        } else {
            changeActionsButtonsStates()
        }
        binding.root.loaderFl.visibility = if (isLoading) VISIBLE else GONE
    }


}