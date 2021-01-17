package com.esgi.nova

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_dashboard.*
import android.view.View
import com.esgi.nova.difficulties.application.GetAllDetailedDifficulties
import com.esgi.nova.games.application.CreateGame
import com.esgi.nova.models.Resource
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.doAsync
import javax.inject.Inject

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var createGame: CreateGame

    @Inject
    lateinit var getAllDetailedDifficulties: GetAllDetailedDifficulties


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val difficulties = listOf("Facile", "Moyen", "Difficile")
        tv_difficulty.setAdapter(ArrayAdapter(this, R.layout.list_item, difficulties))
        tv_difficulty.inputType = 0
        tv_difficulty.setText(difficulties[0], false)
        btn_to_leaderboard.setOnClickListener(this)
        btn_init_new_game.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

        Log.d("test", view.toString())

        if (view == btn_to_leaderboard) {
            val intent = Intent(this, LeaderBoardActivity::class.java)
            startActivity(intent)
        } else if (view == btn_init_new_game) {

            doAsync {
                createGame.execute(getAllDetailedDifficulties.execute().first().id) // TEMP
                EventActivity.startEventActivity(this@DashboardActivity)

            }

        }
    }
}