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
import com.esgi.nova.languages.application.SynchronizeLanguagesToLocalStorage
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
    lateinit var synchronizeLanguagesToLocalStorage: SynchronizeLanguagesToLocalStorage

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
        loadData()
//        }
    }

    private fun loadData() {
        loadingText?.text = getString(R.string.resourceLoadingPrompt)

        doAsync {


//            getCurrentGame.execute()?.let { game ->
//                linkGameWithEvent.execute(game.id, getAllDetailedEvents.execute().first().id)
//            }

            synchronizeLanguagesToLocalStorage.execute()
            synchronizeResourcesToLocalStorage.execute()
            synchronizeDifficultiesToLocalStorage.execute()
            synchronizeEventsToLocalStorage.execute()
//            createGame.execute(
//                getAllDetailedDifficulties.execute().first().id
//            )
//            getCurrentGame.execute()?.let { game ->
//                while(true){
//                    getNextEvent.execute(game.id)?.let { event ->
//                        val res = confirmChoice.execute(game.id, event.choices.first().id, duration = 12)
//                        val currGame = getCurrentGame.execute()
//                        println(currGame)
//                        println(res)
//                    }
//                }
//            }

//            setSynchronized.execute()
//            getCurrentGame.execute()?.let { game ->
//                val nextEvent = getNextEvent.execute(game.id)
//            }
            navigateToDashboardPage()


        }
    }


    private fun navigateToDashboardPage() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }




}