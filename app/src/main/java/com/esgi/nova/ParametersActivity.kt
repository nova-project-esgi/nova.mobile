package com.esgi.nova

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.esgi.nova.dtos.difficulty.DetailedDifficultyDto
import com.esgi.nova.dtos.languages.AppLanguageDto
import com.esgi.nova.languages.application.GetAllLanguages
import com.esgi.nova.languages.ports.IAppLanguage
import com.esgi.nova.utils.reflectMapCollection

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_leader_board.*
import kotlinx.android.synthetic.main.activity_parameters.*
import org.jetbrains.anko.doAsync
import javax.inject.Inject

@AndroidEntryPoint
class ParametersActivity : AppCompatActivity() {

    @Inject
    lateinit var getAllLanguages: GetAllLanguages

    private lateinit var languages: List<AppLanguageDto>

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
        doAsync {
            languages = getAllLanguages.execute().reflectMapCollection<IAppLanguage,AppLanguageDto>().toList()
            runOnUiThread{
                val arrayAdapter = ArrayAdapter(
                    this@ParametersActivity,
                    R.layout.list_item,
                    languages
                )
                tv_language_option.setAdapter(arrayAdapter)
                tv_language_option.inputType = 0
                if (languages.isNotEmpty()) {
                    val selectedLanguage = languages.firstOrNull { language -> language.isSelected }
                    if (selectedLanguage != null) {
                        tv_language_option.setText(selectedLanguage.code, false)
                    } else {
                        tv_language_option.setText(languages[0].code, false)
                    }
                } else {
                    tv_language_option.isEnabled = false
                }
            }
        }

    }
}