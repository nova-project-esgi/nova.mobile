package com.esgi.nova

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esgi.nova.difficulties.application.GetAllDetailedDifficulties
import com.esgi.nova.difficulties.application.SynchronizeDifficultiesToLocalStorage
import com.esgi.nova.events.application.GetAllImageDetailedEventWrappers
import com.esgi.nova.events.application.SynchronizeEventsToLocalStorage
import com.esgi.nova.games.application.CreateGame
import com.esgi.nova.languages.application.SynchronizeLanguagesToLocalStorage
import com.esgi.nova.resources.application.GetAllImageResourceWrappers
import com.esgi.nova.resources.application.SynchronizeResourceToLocalStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_init_setup.*
import org.jetbrains.anko.doAsync
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class InitSetupActivity : AppCompatActivity() {

    @Inject
    lateinit var synchronizeEventsToLocalStorage: SynchronizeEventsToLocalStorage

    @Inject
    lateinit var synchronizeDifficultiesToLocalStorage: SynchronizeDifficultiesToLocalStorage

    @Inject
    lateinit var synchronizeLanguagesToLocalStorage: SynchronizeLanguagesToLocalStorage

    @Inject
    lateinit var synchronizeResourcesToLocalStorage: SynchronizeResourceToLocalStorage

    @Inject
    lateinit var getAllImageResourceWrappers: GetAllImageResourceWrappers

    @Inject
    lateinit var getAllImageDetailedEventWrappers: GetAllImageDetailedEventWrappers

    @Inject
    lateinit var getAllDetailedDifficulties: GetAllDetailedDifficulties

    @Inject
    lateinit var createGame: CreateGame


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init_setup)
        loadData()
    }


    private fun loadData() {
        loadingText?.text = getString(R.string.resourceLoadingPrompt)

        doAsync {
            val language = Locale.getDefault().toLanguageTag() // TODO : chosen Language
            synchronizeLanguagesToLocalStorage.execute()
            synchronizeResourcesToLocalStorage.execute(language)
            synchronizeDifficultiesToLocalStorage.execute(language)
            synchronizeEventsToLocalStorage.execute(language)
            navigateToDashboardPage()
        }
    }


    private fun navigateToDashboardPage() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }




}