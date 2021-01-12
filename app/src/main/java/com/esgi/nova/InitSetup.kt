package com.esgi.nova

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esgi.nova.resources.infrastructure.data.Resource
import com.esgi.nova.difficulties.application.SynchronizeDifficultiesToLocalStorage
import com.esgi.nova.events.application.SynchronizeEventsToLocalStorage
import com.esgi.nova.languages.application.SynchronizeLanguagesToLocalStorage
import com.esgi.nova.resources.application.SynchronizeResourceToLocalStorage
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
            synchronizeLanguagesToLocalStorage.execute()
            synchronizeResourcesToLocalStorage.execute()
            synchronizeDifficultiesToLocalStorage.execute()
//            synchronizeEventsToLocalStorage.execute()

            //loadRessources()
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




}