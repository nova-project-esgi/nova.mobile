package com.esgi.nova

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ParametersActivity : AppCompatActivity() {

    companion object {
        fun startParametersActivity(context: Context): Context {
            val intent = Intent(context, ParametersActivity::class.java)
            context.startActivity(intent)
            return context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parameters)
    }
}