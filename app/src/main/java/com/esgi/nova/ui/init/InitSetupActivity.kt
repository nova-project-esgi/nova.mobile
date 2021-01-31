package com.esgi.nova.ui.init

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.esgi.nova.R
import com.esgi.nova.databinding.ActivityInitSetupBinding
import com.esgi.nova.games.application.*
import com.esgi.nova.ui.dashboard.DashboardActivity
import com.esgi.nova.ui.init.view_models.BaseInitViewModel
import com.esgi.nova.ui.snackbars.IconSnackBar.Companion.networkErrorSnackBar
import com.esgi.nova.ui.snackbars.IconSnackBar.Companion.unexpectedErrorSnackBar
import com.esgi.nova.users.ui.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.alert
import org.jetbrains.anko.textResource
import javax.inject.Inject


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
        binding.root.networkErrorSnackBar()?.show()
        LoginActivity.start(this)
    }

    private fun handleUnexpectedError() {
        binding.root.unexpectedErrorSnackBar()?.show()
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