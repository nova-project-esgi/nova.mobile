package com.esgi.nova

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.esgi.nova.adapters.ResourcesAdapter
import com.esgi.nova.games.infrastructure.dto.GameResourceView
import kotlinx.android.synthetic.main.activity_event.*
import java.util.*

class EventActivity : AppCompatActivity() {


    private val localResources = listOf(
        GameResourceView(UUID.randomUUID(), 20, "Ammo"),
        GameResourceView(UUID.randomUUID(), 20, "Morale"),
        GameResourceView(UUID.randomUUID(), 20, "Money"),
        GameResourceView(UUID.randomUUID(), 20, "Fuel")
    )

    companion object {
        fun startEventActivity(context: Context): Context {
            val intent = Intent(context, EventActivity::class.java)
            context.startActivity(intent)
            return context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        resourcesRecyclerView?.apply {
            layoutManager = LinearLayoutManager(this@EventActivity)
            adapter = ResourcesAdapter(localResources)
        }


    }


}