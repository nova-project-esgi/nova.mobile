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
import com.esgi.nova.languages.ports.IAppLanguage
import com.esgi.nova.parameters.application.GetParameters
import com.esgi.nova.parameters.application.SaveParameters
import com.esgi.nova.parameters.application.toLanguageParameters
import com.esgi.nova.parameters.ports.ILanguageParameters
import com.esgi.nova.parameters.ui.view_models.ParametersViewModel
import com.esgi.nova.ui.dashboard.DashboardActivity
import com.esgi.nova.ui.init.InitSetupActivity
import com.esgi.nova.users.ui.LoginActivity
import com.esgi.nova.utils.reflectMapCollection
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.doAsync
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

    val parametersViewModel by viewModels<ParametersViewModel>()

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
        setContent()

        binding.btnSaveOption.setOnClickListener(this)
        binding.btnDisconnectOption.setOnClickListener(this)
        binding.sDarkModeOption.setOnCheckedChangeListener(this)
        binding.sDownloadOption.setOnCheckedChangeListener(this)
        binding.sNotificationOption.setOnCheckedChangeListener(this)
        binding.sMusicOption.setOnCheckedChangeListener(this)
    }

    private fun setContent() {
        if (!parametersViewModel.initialized) {
            doAsync {
                parametersViewModel.languages =
                    getAllLanguages.execute().reflectMapCollection<IAppLanguage, AppLanguageDto>()
                        .toList()
                parametersViewModel.copyParameters(getParameters.execute())
                parametersViewModel.selectedLanguage =
                    parametersViewModel.languages.firstOrNull { language -> language.isSelected }
                parametersViewModel.initialized = true
                runOnUiThread {
                    setLanguageAutocomplete()
                    setUpParameters()
                }
            }

        } else {
            setLanguageAutocomplete()
            setUpParameters()
        }
    }

    private fun setUpParameters() {
        binding.sDownloadOption.isChecked = parametersViewModel.hasDailyEvents
        binding.sDarkModeOption.isChecked = parametersViewModel.isDarkMode
        binding.sNotificationOption.isChecked = parametersViewModel.hasNotifications
        binding.sMusicOption.isChecked = parametersViewModel.hasSound
    }

    private fun setLanguageAutocomplete() {
        val arrayAdapter = ArrayAdapter(
            this,
            R.layout.list_item,
            parametersViewModel.languages
        )
        binding.tvLanguageOption.setAdapter(arrayAdapter)
        if (parametersViewModel.selectedLanguage != null) {
            binding.tvLanguageOption.setText(parametersViewModel.selectedLanguage?.tag, false)
        } else {
            binding.tvLanguageOption.isEnabled = false
        }
        binding.tvLanguageOption.onItemClickListener = this
    }

    override fun onClick(v: View?) {
        if (v == binding.btnSaveOption) {
            saveParameters()
        } else if (v == binding.btnDisconnectOption) {
            disconnectUser()
        }
    }

    private fun disconnectUser() {
        doAsync {
            runOnUiThread {
                LoginActivity.startReconnection(this@ParametersActivity)
            }
        }
    }

    private fun saveParameters() {
        val updateParameters  = parametersViewModel.toLanguageParameters(parametersViewModel.selectedLanguage)
        doAsync {
            val previousParams = getParameters.execute()
            saveParameters.execute(updateParameters)
            runOnUiThread {
                val toast = Toast.makeText(
                    this@ParametersActivity,
                    "Paramètres sauvegardés",
                    Toast.LENGTH_LONG
                )
                toast.show()
                checkChanges(previousParams)
            }
        }
    }

    private fun checkChanges(previousParams: ILanguageParameters) {
        if (previousParams.selectedLanguage?.id != parametersViewModel.selectedLanguage?.id) {
            InitSetupActivity.startWithUserConfirmation(this)
        } else {
            DashboardActivity.start(this)
            finish()
        }
    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        parametersViewModel.selectedLanguage = parent?.getItemAtPosition(position) as AppLanguageDto
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView) {
            binding.sDarkModeOption -> parametersViewModel.isDarkMode = isChecked
            binding.sDownloadOption -> parametersViewModel.hasDailyEvents = isChecked
            binding.sNotificationOption -> parametersViewModel.hasNotifications = isChecked
            binding.sMusicOption -> parametersViewModel.hasSound = isChecked
        }
    }
}