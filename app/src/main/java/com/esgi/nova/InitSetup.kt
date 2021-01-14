package com.esgi.nova

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.esgi.nova.application_state.application.IsSynchronized
import com.esgi.nova.application_state.application.SetSynchronized
import com.esgi.nova.difficulties.application.GetAllDetailedDifficulties
import com.esgi.nova.difficulties.application.SynchronizeDifficultiesToLocalStorage
import com.esgi.nova.events.application.GetAllDetailedEvents
import com.esgi.nova.events.application.GetAllImageDetailedEventWrappers
import com.esgi.nova.events.application.SynchronizeEventsToLocalStorage
import com.esgi.nova.games.application.CreateGame
import com.esgi.nova.games.application.LinkGameWithEvent
import com.esgi.nova.infrastructure.preferences.PreferenceConstants
import com.esgi.nova.languages.application.SynchronizeLanguagesToLocalStorage
import com.esgi.nova.resources.application.GetAllImageResourceWrappers
import com.esgi.nova.resources.application.SynchronizeResourceToLocalStorage
import com.esgi.nova.resources.infrastructure.data.ResourceDbRepository
import com.esgi.nova.resources.infrastructure.data.ResourceEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_init_setup.*
import org.jetbrains.anko.doAsync
import java.net.URL
import javax.inject.Inject

@AndroidEntryPoint
class InitSetup : AppCompatActivity() {

    /*
    temp
     */

    @Inject
    lateinit var fileStorageRepository: FileStorageRepository

    @Inject
    lateinit var resourceDbRepository: ResourceDbRepository

    //

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
    lateinit var linkGameWithEvent: LinkGameWithEvent

    @Inject
    lateinit var createGame: CreateGame

    @Inject
    lateinit var isSynchronized: IsSynchronized
    @Inject
    lateinit var setSynchronized: SetSynchronized

    companion object {
        const val ResynchronizeKey = "ResynchronizeKey"

        fun startInitSetup(context: Context): Context{
            val intent = Intent(context, InitSetup::class.java)
            context.startActivity(intent)
            return context
        }
        fun startResynchronize(context: Context): Context{
            val intent = Intent(context, InitSetup::class.java)
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
        doAsync {
            val difficulty = getAllDetailedDifficulties.execute().first()
            val event = getAllDetailedEvents.execute().first()
            createGame.execute(difficulty.id)?.let { game ->
                linkGameWithEvent.execute(game.id, event.id)
            }
//            synchronizeLanguagesToLocalStorage.execute()
//            synchronizeResourcesToLocalStorage.execute()
//            synchronizeDifficultiesToLocalStorage.execute()
//            synchronizeEventsToLocalStorage.execute()
//            setSynchronized.execute()

        }
    }


    private fun loadRessources() {
        val apiCall = URL("https://next.json-generator.com/api/json/get/VydTXyeqY?delay=2000")
        val response = apiCall.readText()        //SynchronizeEventsToLocalStorage.execute()


        val itemType = object : TypeToken<List<ResourceEntity>>() {}.type
        val resources = Gson().fromJson<List<ResourceEntity>>(response, itemType) // should work ?

        resources.forEach{
            //resource: Resource -> db?.resourceDAO()?.insertAll(resource)
        }

        runOnUiThread {
            loadingText?.text = "nova"
        }
    }




}