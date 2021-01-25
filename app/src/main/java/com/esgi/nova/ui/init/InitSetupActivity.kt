package com.esgi.nova.ui.init

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.esgi.nova.R
import com.esgi.nova.application_state.application.IsSynchronized
import com.esgi.nova.application_state.application.SetSynchronizeState
import com.esgi.nova.difficulties.application.SynchronizeDifficulties
import com.esgi.nova.events.application.SynchronizeEvents
import com.esgi.nova.games.application.*
import com.esgi.nova.languages.application.SynchronizeLanguages
import com.esgi.nova.ports.Synchronize
import com.esgi.nova.resources.application.SynchronizeResources
import com.esgi.nova.ui.dashboard.DashboardActivity
import com.esgi.nova.ui.init.view_models.InitViewModel
import com.esgi.nova.users.ui.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_init_setup.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import javax.inject.Inject

@AndroidEntryPoint
class InitSetupActivity : AppCompatActivity() {

    @Inject
    lateinit var synchronizeEvents: SynchronizeEvents

    @Inject
    lateinit var synchronizeDifficulties: SynchronizeDifficulties

    @Inject
    lateinit var synchronizeLanguages: SynchronizeLanguages

    @Inject
    lateinit var synchronizeResources: SynchronizeResources

    @Inject
    lateinit var synchronizeLastActiveGame: SynchronizeLastActiveGame

    @Inject
    lateinit var isSynchronized: IsSynchronized

    @Inject
    lateinit var setSynchronizeState: SetSynchronizeState


    private val viewModel by viewModels<InitViewModel>()

    lateinit var stepsList: List<Synchronize>

    companion object {
        const val SynchronizeStepsTotal = 6

        fun startWithUserConfirmation(context: Context): Context {
            context.alert {
                val intent = Intent(context, InitSetupActivity::class.java)
                messageResource = R.string.resource_fetching_msg
                titleResource = R.string.network_warning
                iconResource = R.drawable.baseline_warning_amber_400_24dp
                negativeButton(R.string.cancel) { }
                positiveButton(R.string.yes) { context.startActivity(intent) }
            }.show()
            return context
        }




    }

    override fun onBackPressed() {
        LoginActivity.startReconnection(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init_setup)
        viewModel.stepLimit = SynchronizeStepsTotal + 1

        stepsList = listOf(
            synchronizeLanguages,
            synchronizeResources,
            synchronizeDifficulties,
            synchronizeEvents,
            synchronizeLastActiveGame
        )

        setSynchronizeState.execute(false)
        loadData()
    }

    private fun loadData() {


        doAsync {
            stepsList.slice(viewModel.currentStep until stepsList.size).forEach { sync ->
                viewModel.currentStep
                runOnUiThread { setLoadingText(viewModel.currentStep + 1) }
                sync.execute()
                viewModel.currentStep++
            }

            runOnUiThread { setLoadingText(viewModel.currentStep) }
            setSynchronizeState.execute(true)
            DashboardActivity.start(this@InitSetupActivity)
            finish()
        }
    }

    private fun setLoadingText(index: Int) {
        val loadingTextString = getString(R.string.resourceLoadingPrompt) + " $index / $SynchronizeStepsTotal"
        loading_tv?.text = loadingTextString
        when(index){
            1 -> loading_description_tv?.text = getString(R.string.loading_step_1)
            2 -> loading_description_tv?.text = getString(R.string.loading_step_2)
            3 -> loading_description_tv?.text = getString(R.string.loading_step_3)
            4 -> loading_description_tv?.text = getString(R.string.loading_step_4)
            5 -> loading_description_tv?.text = getString(R.string.loading_step_5)
            6 -> loading_description_tv?.text = getString(R.string.loading_step_6)
        }
    }



}