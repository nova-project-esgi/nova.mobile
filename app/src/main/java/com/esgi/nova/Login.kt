package com.esgi.nova

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view == btn_login) {
            Log.d("TEST", ti_login.text.toString())
            Log.d("TEST", ti_password.text.toString())

            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent);
        }
    }
}