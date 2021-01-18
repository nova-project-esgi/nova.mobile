package com.esgi.nova

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)

        doAsync {
            var game = getLastEndedGame.execute()

            var turnTotal = game?.rounds
            var resources = game?.resources

            runOnUiThread {
                end_message?.text = turnTotal?.let {
                    getResources().getQuantityString(R.plurals.turn_recap, it, turnTotal)
                }
            }
        }
    }
}