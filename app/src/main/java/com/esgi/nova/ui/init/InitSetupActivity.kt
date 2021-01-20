package com.esgi.nova.ui.init

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.esgi.nova.R
import com.esgi.nova.application_state.application.ClearState
import com.esgi.nova.application_state.application.IsSynchronized
import com.esgi.nova.application_state.application.SetSynchronized
import com.esgi.nova.difficulties.application.GetAllDetailedDifficulties
import com.esgi.nova.difficulties.application.SynchronizeDifficulties
import com.esgi.nova.events.application.GetAllDetailedEvents
import com.esgi.nova.events.application.GetAllImageDetailedEventWrappers
import com.esgi.nova.events.application.SynchronizeEvents
import com.esgi.nova.games.application.*
import com.esgi.nova.languages.application.SynchronizeLanguages
import com.esgi.nova.ports.Synchronize
import com.esgi.nova.resources.application.GetAllImageResourceWrappers
import com.esgi.nova.resources.application.SynchronizeResources
import com.esgi.nova.ui.dashboard.DashboardActivity
import com.esgi.nova.ui.init.view_models.InitViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_init_setup.*
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
    lateinit var getAllImageResourceWrappers: GetAllImageResourceWrappers

    @Inject
    lateinit var getAllImageDetailedEventWrappers: GetAllImageDetailedEventWrappers

    @Inject
    lateinit var getAllDetailedDifficulties: GetAllDetailedDifficulties

    @Inject
    lateinit var getAllDetailedEvents: GetAllDetailedEvents


    @Inject
    lateinit var createGame: CreateGame

    @Inject
    lateinit var isSynchronized: IsSynchronized

    @Inject
    lateinit var setSynchronized: SetSynchronized

    @Inject
    lateinit var getCurrentGame: GetCurrentGame

    @Inject
    lateinit var getNextEvent: GetNextEvent

    @Inject
    lateinit var confirmChoice: ConfirmChoice

    @Inject
    lateinit var clearState: ClearState

    val initViewModel by viewModels<InitViewModel>()

    lateinit var stepsList: List<Synchronize>

    companion object {
        const val ResynchronizeKey = "ResynchronizeKey"
        const val SynchronizeStepsTotal = 6

        fun start(context: Context): Context {
            val intent = Intent(context, InitSetupActivity::class.java)
            context.startActivity(intent)
            return context
        }

        fun startResynchronize(context: Context): Context {
            val intent = Intent(context, InitSetupActivity::class.java)
            intent.putExtra(ResynchronizeKey, true)
            context.startActivity(intent)
            return context
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init_setup)
        initViewModel.stepLimit = SynchronizeStepsTotal + 1

        stepsList = listOf(
            synchronizeLanguages,
            synchronizeResources,
            synchronizeDifficulties,
            synchronizeEvents,
            synchronizeLastActiveGame
        )


        if (!isSynchronized.execute() || intent.getBooleanExtra(ResynchronizeKey, false)) {
            loadData()
        } else {
            DashboardActivity.start(this@InitSetupActivity)
            finish()
        }

    }

    private fun loadData() {


        doAsync {
            stepsList.slice(initViewModel.currentStep until stepsList.size).forEach { sync ->
                initViewModel.currentStep
                runOnUiThread { setLoadingText(initViewModel.currentStep + 1) }
                sync.execute()
                initViewModel.currentStep++
            }

            runOnUiThread { setLoadingText(initViewModel.currentStep) }
            setSynchronized.execute()
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


    private fun navigateToDashboardPage() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }




}