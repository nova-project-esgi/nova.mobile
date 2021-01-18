package com.esgi.nova

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.esgi.nova.parameters.application.GetParameters
import com.esgi.nova.parameters.application.SwitchMusic
import com.esgi.nova.parameters.application.SwitchNotifications
import com.esgi.nova.parameters.application.SwitchTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk27.coroutines.onClick
import javax.inject.Inject

@AndroidEntryPoint
class SettingActivity : AppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var getParameters: GetParameters

    @Inject
    lateinit var switchMusic: SwitchMusic

    @Inject
    lateinit var switchTheme: SwitchTheme

    @Inject
    lateinit var switchNotifications: SwitchNotifications

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SettingActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        doAsync {
            val parameters = getParameters.execute()
            runOnUiThread {
                music_btn?.isChecked = parameters.hasMusic
                dark_mode_btn?.isChecked = parameters.isDarkMode
                notification_btn?.isChecked = parameters.hasNotifications
                music_btn.setOnClickListener(this@SettingActivity)
                dark_mode_btn.setOnClickListener(this@SettingActivity)
                notification_btn.setOnClickListener(this@SettingActivity)
            }

        }

    }

    override fun onClick(v: View?) {
        when (v) {
            music_btn -> switchMusic.execute(music_btn.isChecked)
            dark_mode_btn -> switchTheme.execute(music_btn.isChecked)
            notification_btn -> switchNotifications.execute(music_btn.isChecked)
        }
    }

}