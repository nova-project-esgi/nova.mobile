package com.esgi.nova

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        btn_to_leaderboard.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view == btn_to_leaderboard) {
            val intent = Intent(this, LeaderBoard::class.java)
            startActivity(intent);
        }
    }
}