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
import kotlinx.android.synthetic.main.activity_parameters.*
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
        setContentView(R.layout.activity_parameters)
        setContent()

        btn_save_option?.setOnClickListener(this)
        btn_disconnect_option?.setOnClickListener(this)
        s_dark_mode_option?.setOnCheckedChangeListener(this)
        s_download_option?.setOnCheckedChangeListener(this)
        s_notification_option?.setOnCheckedChangeListener(this)
        s_music_option?.setOnCheckedChangeListener(this)
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
        s_download_option?.isChecked = parametersViewModel.hasDailyEvents
        s_dark_mode_option?.isChecked = parametersViewModel.isDarkMode
        s_notification_option?.isChecked = parametersViewModel.hasNotifications
        s_music_option?.isChecked = parametersViewModel.hasSound
    }

    private fun setLanguageAutocomplete() {
        val arrayAdapter = ArrayAdapter(
            this,
            R.layout.list_item,
            parametersViewModel.languages
        )
        tv_language_option?.setAdapter(arrayAdapter)
        if (parametersViewModel.selectedLanguage != null) {
            tv_language_option?.setText(parametersViewModel.selectedLanguage?.androidLocale, false)
        } else {
            tv_language_option?.isEnabled = false
        }
        tv_language_option?.onItemClickListener = this
    }

    override fun onClick(v: View?) {
        if (v == btn_save_option) {
            saveParameters()
        } else if (v == btn_disconnect_option) {
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
            InitSetupActivity.startResynchronize(this)
            finish()
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
            s_dark_mode_option -> parametersViewModel.isDarkMode = isChecked
            s_download_option -> parametersViewModel.hasDailyEvents = isChecked
            s_notification_option -> parametersViewModel.hasNotifications = isChecked
            s_music_option -> parametersViewModel.hasSound = isChecked
        }
    }
}