package com.esgi.nova

import AppDatabase
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.esgi.nova.data.dao.ResourceDAO
import com.esgi.nova.data.entities.Event
import com.esgi.nova.data.entities.Resource
import com.esgi.nova.dto.CompleteEventsDTO
import com.esgi.nova.dto.EventDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_init_setup.*
import org.jetbrains.anko.doAsync
import java.net.URL
import kotlin.concurrent.timer

class InitSetup : AppCompatActivity() {


    val db = AppDatabase.getAppDataBase(this)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init_setup)

        loadData()
    }

    private fun loadData() {
        loadingText?.text = "default"

        doAsync {
            loadRessources()
            loadEvents()
            loadChoices()
        }
    }

    private fun loadRessources() {
        val apiCall = URL("https://next.json-generator.com/api/json/get/VydTXyeqY?delay=2000")
        val response = apiCall.readText()

        val itemType = object : TypeToken<List<Resource>>() {}.type
        val resources = Gson().fromJson<List<Resource>>(response, itemType) // should work ?

        resources.forEach{
            resource: Resource -> db?.resourceDAO()?.insertAll(resource)
        }

        runOnUiThread {
            loadingText?.text = "nova"
        }
    }

    private fun loadEvents() {
        val apiCall = URL("http://www.json-generator.com/api/json/get/cgAZQsKGIy?indent=2")
        val response = apiCall.readText()

        val itemType = object : TypeToken<CompleteEventsDTO>() {}.type
        val completeEvents = Gson().fromJson<CompleteEventsDTO>(response, itemType) // should work ?

        completeEvents?.events?.forEach{
                event: EventDTO -> db?.eventDAO()?.insertAll(event)
        }

        runOnUiThread {
            loadingText?.text = "blabla"
        }
    }

    private fun loadChoices() {

    }


}