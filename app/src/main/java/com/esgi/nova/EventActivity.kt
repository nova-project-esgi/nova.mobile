package com.esgi.nova

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
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

    val choicesListViewModel by viewModels<ChoicesListViewModel>()

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


            getCurrentGame.execute()?.let { game ->

                getNextEvent.execute(game.id)?.let { event ->

                    choicesListViewModel.choices.clear()
                    choicesListViewModel.choices.addAll(event.choices)

                    runOnUiThread {
                        eventTitleView?.text = event.title
                        eventDescriptionView?.text = event.description

                        resourcesRecyclerView?.apply {
                            val orientation = resources.configuration.orientation

                            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                                layoutManager = LinearLayoutManager(
                                    this@EventActivity,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                            } else {
                                layoutManager = LinearLayoutManager(
                                    this@EventActivity,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )

                            }
                            adapter = ResourcesAdapter(game.resources)
                        }
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, ChoicesListFragment.newInstance())
                            .commitNow()

                    }
                }
            }


        }


    }


}