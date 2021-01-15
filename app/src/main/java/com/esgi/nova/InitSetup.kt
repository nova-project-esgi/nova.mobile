package com.esgi.nova

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.esgi.nova.difficulties.application.GetAllDetailedDifficulties
import com.esgi.nova.difficulties.application.SynchronizeDifficultiesToLocalStorage
import com.esgi.nova.events.application.GetAllImageDetailedEventWrappers
import com.esgi.nova.events.application.SynchronizeEventsToLocalStorage
import com.esgi.nova.games.application.CreateGame
import com.esgi.nova.infrastructure.preferences.PreferenceConstants
import com.esgi.nova.languages.application.SynchronizeLanguagesToLocalStorage
import com.esgi.nova.resources.application.GetAllImageResourceWrappers
import com.esgi.nova.resources.application.SynchronizeResourceToLocalStorage
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
    lateinit var createGame: CreateGame


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init_setup)
        loadData()
    }

    private fun toLoginActivity() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }

    private fun loadData() {
        loadingText?.text = "default"
        doAsync {
            synchronizeLanguagesToLocalStorage.execute()
            synchronizeResourcesToLocalStorage.execute("en")
            synchronizeDifficultiesToLocalStorage.execute("en")
            synchronizeEventsToLocalStorage.execute("en")
            val resWrappers = getAllImageResourceWrappers.execute()
            val eventWrappers = getAllImageDetailedEventWrappers.execute()
            val difficulties = getAllDetailedDifficulties.execute()
            createGame.execute(difficulties.first().id)
            println("test")
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