package com.esgi.nova

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esgi.nova.models.Game
import com.esgi.nova.models.Resource

class EventActivity : AppCompatActivity() {


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
    }


}