package com.esgi.nova

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.esgi.nova.adapters.ChoiceAdapter
import com.esgi.nova.adapters.ResourcesAdapter
import com.esgi.nova.games.application.GetCurrentGame
import com.esgi.nova.games.application.GetNextEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_event.*
import org.jetbrains.anko.doAsync
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class EventActivity : AppCompatActivity() {

    @Inject
    lateinit var getCurrentGame: GetCurrentGame

    @Inject
    lateinit var getNextEvent: GetNextEvent


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

        doAsync {

            val localGame = getCurrentGame.execute()
            val localResources = localGame?.resources

            val currentEvent = localGame?.id?.let { getNextEvent.execute(it) }
            val currentChoices = currentEvent?.choices

            runOnUiThread {
                eventTitleView?.text = currentEvent?.title
                eventDescriptionView?.text = currentEvent?.description

                resourcesRecyclerView?.apply {
                    val orientation = resources.configuration.orientation

                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

                    } else {
                        layoutManager = LinearLayoutManager(
                            this@EventActivity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )

                    }

                    adapter = localResources?.let { ResourcesAdapter(it) }
                }

                choicesRecyclerView?.apply {
                    layoutManager = LinearLayoutManager(this@EventActivity)
                    adapter = currentChoices?.let { ChoiceAdapter(it) }
                }
            }
        }


    }


}