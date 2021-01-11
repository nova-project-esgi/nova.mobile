package com.esgi.nova

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esgi.nova.infrastructure.data.entities.Resource
import com.esgi.nova.difficulties.application.SynchronizeDifficultiesToLocalStorage
import com.esgi.nova.events.application.SynchronizeEventsToLocalStorage
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
//        toLoginActivity()
        doAsync {
            loadDifficulties()
            //loadRessources()
            //loadEvents()
            //loadChoices()
        }
    }

    private fun loadRessources() {
        val apiCall = URL("https://next.json-generator.com/api/json/get/VydTXyeqY?delay=2000")
        val response = apiCall.readText()        //SynchronizeEventsToLocalStorage.execute()


        val itemType = object : TypeToken<List<Resource>>() {}.type
        val resources = Gson().fromJson<List<Resource>>(response, itemType) // should work ?

        resources.forEach{
            //resource: Resource -> db?.resourceDAO()?.insertAll(resource)
        }

        runOnUiThread {
            loadingText?.text = "nova"
        }
    }

    private fun loadEvents() {
//        synchronizeEventsToLocalStorage.execute()
    }

    private fun loadChoices() {

    }

    private fun loadDifficulties() {
        synchronizeDifficultiesToLocalStorage.execute()
    }


}