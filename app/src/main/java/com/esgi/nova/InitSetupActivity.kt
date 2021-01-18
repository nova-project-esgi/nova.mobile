package com.esgi.nova

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.esgi.nova.application_state.application.IsSynchronized
import com.esgi.nova.application_state.application.SetSynchronized
import com.esgi.nova.difficulties.application.GetAllDetailedDifficulties
import com.esgi.nova.difficulties.application.SynchronizeDifficultiesToLocalStorage
import com.esgi.nova.events.application.GetAllDetailedEvents
import com.esgi.nova.events.application.GetAllImageDetailedEventWrappers
import com.esgi.nova.events.application.SynchronizeEventsToLocalStorage
import com.esgi.nova.games.application.*
import com.esgi.nova.languages.application.SynchronizeLanguages
import com.esgi.nova.resources.application.GetAllImageResourceWrappers
import com.esgi.nova.resources.application.SynchronizeResourceToLocalStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_init_setup.*
import org.jetbrains.anko.doAsync
import javax.inject.Inject

@AndroidEntryPoint
class InitSetupActivity : AppCompatActivity() {

    @Inject
    lateinit var synchronizeEventsToLocalStorage: SynchronizeEventsToLocalStorage

    @Inject
    lateinit var synchronizeDifficultiesToLocalStorage: SynchronizeDifficultiesToLocalStorage

    @Inject
    lateinit var synchronizeLanguages: SynchronizeLanguages

    @Inject
    lateinit var synchronizeResourcesToLocalStorage: SynchronizeResourceToLocalStorage

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
            intent.extras?.putBoolean(ResynchronizeKey, true)
            context.startActivity(intent)
            return context
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init_setup)
//        if(!isSynchronized.execute() || intent.getBooleanExtra(ResynchronizeKey,false)){
        navigateToDashboardPage()
//        }
    }

    private fun loadData() {

        setLoadingText(1)

        doAsync {
            synchronizeLanguages.execute()
            runOnUiThread { setLoadingText(2) }
            synchronizeResourcesToLocalStorage.execute()
            runOnUiThread { setLoadingText(3) }
            synchronizeDifficultiesToLocalStorage.execute()
            runOnUiThread { setLoadingText(4) }
            synchronizeEventsToLocalStorage.execute()
            runOnUiThread { setLoadingText(5) }
            setSynchronized.execute()
            navigateToDashboardPage()
        }
    }

    private fun setLoadingText(index: Int) {
        val loadingTextString = getString(R.string.resourceLoadingPrompt) + " $index / $SynchronizeStepsTotal"
        loadingText?.text = loadingTextString
    }


    private fun navigateToDashboardPage() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }




}