package com.esgi.nova

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.esgi.nova.R
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        spn_difficulty.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listOf("Facile","Moyen","Difficile"))
        spn_resources.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listOf("30","50","70"))
    }
}