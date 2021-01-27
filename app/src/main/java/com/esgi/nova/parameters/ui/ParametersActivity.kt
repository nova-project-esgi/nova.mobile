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
//        setContent()

        binding.btnSaveOption.setOnClickListener(this)
        binding.btnDisconnectOption.setOnClickListener(this)
        binding.sDarkModeOption.setOnCheckedChangeListener(this)
        binding.sDownloadOption.setOnCheckedChangeListener(this)
        binding.sNotificationOption.setOnCheckedChangeListener(this)
        binding.sMusicOption.setOnCheckedChangeListener(this)

        viewModel.selectedLanguage.observe(this) { selectedLanguage ->
            binding.tvLanguageOption.setText(selectedLanguage?.tag, false)
        }
        viewModel.parameters.observe(this) { parameters ->
            setUpParameters(parameters)
        }
        viewModel.parametersSaved.observe(this) {
            Toast.makeText(this, R.string.settings_saved, Toast.LENGTH_LONG).show()
        }
        viewModel.startResynchronize.observe(this) {
            InitSetupActivity.startWithUserConfirmation(this)
        }
        viewModel.startDashboard.observe(this) {
            DashboardActivity.start(this)
        }
        viewModel.initialize()

        setLanguageAutocomplete()
    }


    private fun setUpParameters(parameters: Parameters) {
        binding.sDownloadOption.isChecked = parameters.hasDailyEvents
        binding.sDarkModeOption.isChecked = parameters.isDarkMode
        binding.sNotificationOption.isChecked = parameters.hasNotifications
        binding.sMusicOption.isChecked = parameters.hasSound
    }

    private fun setLanguageAutocomplete() {
        val arrayAdapter = ArrayAdapter(
            this,
            R.layout.list_item,
            viewModel.languages
        )
        binding.tvLanguageOption.setAdapter(arrayAdapter)
        binding.tvLanguageOption.isEnabled = false
        binding.tvLanguageOption.onItemClickListener = this
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