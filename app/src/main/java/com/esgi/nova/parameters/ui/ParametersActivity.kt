package com.esgi.nova.parameters.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.esgi.nova.R
import com.esgi.nova.databinding.ActivityParametersBinding
import com.esgi.nova.dtos.languages.AppLanguageDto
import com.esgi.nova.languages.application.GetAllLanguages
import com.esgi.nova.parameters.application.GetParameters
import com.esgi.nova.parameters.application.SaveParameters
import com.esgi.nova.parameters.ui.models.Parameters
import com.esgi.nova.parameters.ui.view_models.ParametersViewModel
import com.esgi.nova.ui.dashboard.DashboardActivity
import com.esgi.nova.ui.init.InitSetupActivity
import com.esgi.nova.users.ui.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ParametersActivity : AppCompatActivity(), View.OnClickListener,
    CompoundButton.OnCheckedChangeListener,
    AdapterView.OnItemClickListener {

    @Inject
    lateinit var getAllLanguages: GetAllLanguages

    @Inject
    lateinit var getParameters: GetParameters

    @Inject
    lateinit var saveParameters: SaveParameters

    private lateinit var binding: ActivityParametersBinding

    private val viewModel by viewModels<ParametersViewModel>()

    companion object {
        fun start(context: Context): Context {
            val intent = Intent(context, ParametersActivity::class.java)
            context.startActivity(intent)
            return context
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParametersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaveOption.setOnClickListener(this)
        binding.btnDisconnectOption.setOnClickListener(this)
        binding.sDarkModeOption.setOnCheckedChangeListener(this)
        binding.sDownloadOption.setOnCheckedChangeListener(this)
        binding.sNotificationOption.setOnCheckedChangeListener(this)
        binding.sMusicOption.setOnCheckedChangeListener(this)
        binding.tvLanguageOption.onItemClickListener = this

        viewModel.selectedLanguage.observe(this) { selectedLanguage ->
            selectLanguage(selectedLanguage)
        }
        viewModel.languages.observe(this) { languages ->
            setLanguageAutocomplete(languages)
        }
        viewModel.parameters.observe(this) { parameters ->
            setUpParameters(parameters)
        }
        viewModel.parametersSaved.observe(this) {
            showSavedMessage()
        }
        viewModel.startResynchronize.observe(this) {
            startInitSetupActivity()
        }
        viewModel.startDashboard.observe(this) {
            startDashBoardActivity()
        }
        viewModel.isLoading.observe(this) {
            handleLoading(it)
        }

        viewModel.initialize()

    }

    private fun handleLoading(isLoading: Boolean?) {
        if (isLoading == true) {
            binding.btnSaveOption.isEnabled = false
            binding.btnDisconnectOption.isEnabled = false
            binding.sDarkModeOption.isEnabled = false
            binding.sDownloadOption.isEnabled = false
            binding.sNotificationOption.isEnabled = false
            binding.sMusicOption.isEnabled = false
            binding.tvLanguageOption.isEnabled = false
            binding.root.loaderFl.visibility = View.VISIBLE
        } else {
            binding.root.loaderFl.visibility = View.GONE
            binding.btnSaveOption.isEnabled = true
            binding.btnDisconnectOption.isEnabled = true
            binding.sDarkModeOption.isEnabled = true
            binding.sDownloadOption.isEnabled = true
            binding.sNotificationOption.isEnabled = true
            binding.sMusicOption.isEnabled = true
            binding.tvLanguageOption.isEnabled = true
        }
    }

    private fun startDashBoardActivity() {
        DashboardActivity.start(this)
    }

    private fun startInitSetupActivity() {
        InitSetupActivity.startWithUserConfirmation(this)
    }

    private fun showSavedMessage() {
        Toast.makeText(this, R.string.settings_saved, Toast.LENGTH_SHORT).show()
    }

    private fun selectLanguage(selectedLanguage: AppLanguageDto?) {
        binding.tvLanguageOption.setText(selectedLanguage?.tag, false)
        binding.tvLanguageOption.isEnabled = true
    }


    private fun setUpParameters(parameters: Parameters) {
        binding.sDownloadOption.isChecked = parameters.hasDailyEvents
        binding.sDarkModeOption.isChecked = parameters.isDarkMode
        binding.sNotificationOption.isChecked = parameters.hasNotifications
        binding.sMusicOption.isChecked = parameters.hasSound
    }

    private fun setLanguageAutocomplete(languages: List<AppLanguageDto>) {
        val arrayAdapter = ArrayAdapter(
            this,
            R.layout.list_item,
            languages
        )
        binding.tvLanguageOption.setAdapter(arrayAdapter)
        binding.tvLanguageOption.isEnabled = false
    }

    override fun onClick(v: View?) {
        if (v == binding.btnSaveOption) {
            viewModel.persistParameters()
        } else if (v == binding.btnDisconnectOption) {
            LoginActivity.startReconnection(this@ParametersActivity)
        }
    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.setSelectedLanguage(parent?.getItemAtPosition(position) as AppLanguageDto)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView) {
            binding.sDarkModeOption -> viewModel.parameters.value?.isDarkMode = isChecked
            binding.sDownloadOption -> viewModel.parameters.value?.hasDailyEvents = isChecked
            binding.sNotificationOption -> viewModel.parameters.value?.hasNotifications = isChecked
            binding.sMusicOption -> viewModel.parameters.value?.hasSound = isChecked
        }
    }
}