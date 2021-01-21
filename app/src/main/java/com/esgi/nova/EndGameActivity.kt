package com.esgi.nova

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esgi.nova.games.application.GetLastEndedGame
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_end_game.*
import org.jetbrains.anko.doAsync
import javax.inject.Inject


@AndroidEntryPoint
class EndGameActivity : AppCompatActivity() {

    @Inject
    lateinit var getLastEndedGame: GetLastEndedGame

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, EndGameActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)

        doAsync {
            var game = getLastEndedGame.execute()

            var turnTotal = game?.rounds
            var resources = game?.resources

            runOnUiThread {
                end_title?.text = getResources().getString(R.string.end_game_title)
                end_message?.text = getResources().getString(R.string.end_game_message)
                turn_recap?.text = turnTotal?.let {
                    getResources().getQuantityString(R.plurals.turn_recap, it, turnTotal)
                }

            }
        }
    }
}