package com.esgi.nova

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.esgi.nova.application_state.storage.models.Parameters
import com.esgi.nova.dtos.languages.AppLanguageDto
import com.esgi.nova.languages.application.GetAllLanguages
import com.esgi.nova.languages.ports.IAppLanguage
import com.esgi.nova.parameters.application.GetParameters
import com.esgi.nova.parameters.application.SaveParameters
import com.esgi.nova.parameters.application.toLanguageParameters
import com.esgi.nova.parameters.ports.ILanguageParameters
import com.esgi.nova.users.application.LogOutUser
import com.esgi.nova.utils.reflectMapCollection

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_leader_board.*
import kotlinx.android.synthetic.main.activity_parameters.*
import org.jetbrains.anko.doAsync
import javax.inject.Inject

@AndroidEntryPoint
class ParametersActivity : AppCompatActivity(), View.OnClickListener,
    AdapterView.OnItemClickListener {

    @Inject
    lateinit var getAllLanguages: GetAllLanguages

    @Inject
    lateinit var getParameters: GetParameters

    @Inject
    lateinit var saveParameters: SaveParameters

    @Inject
    lateinit var logOutUser: LogOutUser

    private lateinit var languages: List<AppLanguageDto>
    private var selectedLanguage: AppLanguageDto? = null

    private lateinit var parametersState: ILanguageParameters

    companion object {
        fun startParametersActivity(context: Context): Context {
            val intent = Intent(context, ParametersActivity::class.java)
            context.startActivity(intent)
            return context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parameters)
        btn_save_option.setOnClickListener(this)
        btn_disconnect_option.setOnClickListener(this)
        loadData()

    }

    private fun loadData() {
        doAsync {
            languages =
                getAllLanguages.execute().reflectMapCollection<IAppLanguage, AppLanguageDto>()
                    .toList()
            parametersState = getParameters.execute()
            runOnUiThread {
                generateLanguageOptions()
                setUpParameters()
            }
        }
    }

    private fun setUpParameters() {
        s_download_option.isChecked = parametersState.hasDailyEvents
        s_dark_mode_option.isChecked = parametersState.isDarkMode
        s_notification_option.isChecked = parametersState.hasNotifications
        s_music_option.isChecked = parametersState.hasMusic
    }

    private fun generateLanguageOptions() {
        val arrayAdapter = ArrayAdapter(
            this@ParametersActivity,
            R.layout.list_item,
            languages
        )
        tv_language_option.setAdapter(arrayAdapter)
        tv_language_option.inputType = 0
        if (languages.isNotEmpty()) {
            selectedLanguage = languages.firstOrNull(){ language -> language.isSelected }
            selectedLanguage?.let {
                tv_language_option.setText(it.toString(), false)
            } ?: run {
                selectedLanguage = languages[0]
                tv_language_option.setText(languages[0].toString(), false)
            }

        } else {
            tv_language_option.isEnabled = false
        }
        tv_language_option.onItemClickListener = this
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
            logOutUser.execute()
            runOnUiThread {
                LoginActivity.startLoginActivity(this@ParametersActivity)
                finish()
            }
        }
    }

    private fun saveParameters() {
        val updateParameters: ILanguageParameters = updateParameters()
        doAsync {
            val savedParameters = saveParameters.execute(updateParameters)
            runOnUiThread {
                val toast = Toast.makeText(
                    this@ParametersActivity,
                    "Paramètres sauvegardés",
                    Toast.LENGTH_LONG
                )
                toast.show()
                checkChanges(savedParameters)
            }
        }
    }

    private fun checkChanges(savedParameters: ILanguageParameters) {
        if (savedParameters.selectedLanguage?.id != parametersState.selectedLanguage?.id || (savedParameters.hasDailyEvents && !parametersState.hasDailyEvents)) {
            InitSetupActivity.startResynchronize(this)
            finish()
        } else {
            DashboardActivity.startDashBoardActivity(this)
            finish()
        }
    }

    private fun updateParameters(): ILanguageParameters {

        return Parameters(
            isDarkMode = s_dark_mode_option.isChecked,
            hasDailyEvents = s_download_option.isChecked,
            hasMusic = s_music_option.isChecked,
            hasNotifications = s_notification_option.isChecked
        ).toLanguageParameters(selectedLanguage)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedLanguage = parent?.getItemAtPosition(position) as AppLanguageDto
    }
}