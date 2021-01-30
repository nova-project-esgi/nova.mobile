package com.esgi.nova.ui.init

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esgi.nova.R
import com.esgi.nova.application_state.application.SetSynchronizeState
import com.esgi.nova.databinding.ActivityInitSetupBinding
import com.esgi.nova.difficulties.application.SynchronizeDifficulties
import com.esgi.nova.events.application.DeleteOrphansDailyEvents
import com.esgi.nova.events.application.SynchronizeEvents
import com.esgi.nova.games.application.*
import com.esgi.nova.languages.application.SynchronizeLanguages
import com.esgi.nova.resources.application.SynchronizeResources
import com.esgi.nova.ui.dashboard.DashboardActivity
import com.esgi.nova.ui.init.view_models.BaseInitViewModel
import com.esgi.nova.ui.init.view_models.InitViewModel
import com.esgi.nova.users.ui.LoginActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import org.jetbrains.anko.alert
import org.jetbrains.anko.textResource
import javax.inject.Inject


interface IInitViewModelFactory : ViewModelProvider.Factory

@Suppress("UNCHECKED_CAST")
class InitViewModelFactory(
    private val synchronizeEvents: SynchronizeEvents,
    private val synchronizeDifficulties: SynchronizeDifficulties,
    private val synchronizeLanguages: SynchronizeLanguages,
    private val synchronizeResources: SynchronizeResources,
    private val synchronizeLastActiveGame: SynchronizeLastActiveGame,
    private val deleteOrphansDailyEvents: DeleteOrphansDailyEvents,
    private val setSynchronizeState: SetSynchronizeState,
) : IInitViewModelFactory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return InitViewModel(
            synchronizeEvents = synchronizeEvents,
            synchronizeDifficulties = synchronizeDifficulties,
            synchronizeLanguages = synchronizeLanguages,
            synchronizeResources = synchronizeResources,
            synchronizeLastActiveGame = synchronizeLastActiveGame,
            deleteOrphansDailyEvents = deleteOrphansDailyEvents,
            setSynchronizeState = setSynchronizeState
        ) as T
    }
}

@Module
@InstallIn(ActivityComponent::class)
class InitSetupActivityModule {

    @Provides
    fun provideCalculatorViewModelFactory(
        synchronizeEvents: SynchronizeEvents,
        synchronizeDifficulties: SynchronizeDifficulties,
        synchronizeLanguages: SynchronizeLanguages,
        synchronizeResources: SynchronizeResources,
        synchronizeLastActiveGame: SynchronizeLastActiveGame,
        deleteOrphansDailyEvents: DeleteOrphansDailyEvents,
        setSynchronizeState: SetSynchronizeState,
    ): IInitViewModelFactory =
        InitViewModelFactory(
            synchronizeEvents = synchronizeEvents,
            synchronizeDifficulties = synchronizeDifficulties,
            synchronizeLanguages = synchronizeLanguages,
            synchronizeResources = synchronizeResources,
            synchronizeLastActiveGame = synchronizeLastActiveGame,
            deleteOrphansDailyEvents = deleteOrphansDailyEvents,
            setSynchronizeState = setSynchronizeState
        )
}

@AndroidEntryPoint
class InitSetupActivity : AppCompatActivity() {

    private lateinit var viewModel: BaseInitViewModel

    private lateinit var binding: ActivityInitSetupBinding

    @Inject
    lateinit var viewModelFactory: IInitViewModelFactory

    companion object {
        const val SynchronizeStepsTotal = 7

        fun startWithUserConfirmation(context: Context): Context {
            context.alert {
                val intent = Intent(context, InitSetupActivity::class.java)
                messageResource = R.string.resource_fetching_msg
                titleResource = R.string.network_warning
                iconResource = R.drawable.baseline_warning_amber_400_24dp
                negativeButton(R.string.cancel) { }
                positiveButton(R.string.yes) { context.startActivity(intent) }
            }.show()
            return context
        }

    }

    override fun onBackPressed() {
        LoginActivity.startReconnection(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BaseInitViewModel::class.java)
        viewModel.currentInitStep.observe(this) { step -> setLoadingText(step) }
        viewModel.navigateToDashboard.observe(this) { navigateToDashboard() }
        viewModel.networkError.observe(this) { handleNetworkError() }
        viewModel.unexpectedError.observe(this) { handleUnexpectedError() }
        viewModel.loadContent()

    }

    private fun navigateToDashboard() {
        DashboardActivity.start(this@InitSetupActivity)
    }

    private fun handleNetworkError() {
        Toast.makeText(this, R.string.network_not_available_msg, Toast.LENGTH_SHORT).show()
        LoginActivity.start(this)
    }

    private fun handleUnexpectedError() {
        Toast.makeText(this, R.string.unexpected_error_msg, Toast.LENGTH_SHORT).show()
        LoginActivity.start(this)
    }

    private fun setLoadingText(index: Int) {
        val loadingTextString =
            getString(R.string.resourceLoadingPrompt) + " $index / $SynchronizeStepsTotal"
        binding.loadingTv.text = loadingTextString
        when (index) {
            1 -> binding.loadingDescriptionTv.textResource = R.string.loading_step_1
            2 -> binding.loadingDescriptionTv.textResource = R.string.loading_step_2
            3 -> binding.loadingDescriptionTv.textResource = R.string.loading_step_3
            4 -> binding.loadingDescriptionTv.textResource = R.string.loading_step_4
            5 -> binding.loadingDescriptionTv.textResource = R.string.loading_step_5
            6 -> binding.loadingDescriptionTv.textResource = R.string.loading_step_6
            7 -> binding.loadingDescriptionTv.textResource = R.string.loading_step_7
        }
    }


}