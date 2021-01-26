package com.esgi.nova.users.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.esgi.nova.R
import com.esgi.nova.databinding.ActivityLoginBinding
import com.esgi.nova.parameters.application.SetCurrentTheme
import com.esgi.nova.sound.application.SwitchSound
import com.esgi.nova.ui.dashboard.DashboardActivity
import com.esgi.nova.ui.init.InitSetupActivity
import com.esgi.nova.users.ui.view_models.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {


    @Inject
    lateinit var setCurrentTheme: SetCurrentTheme

    @Inject
    lateinit var switchSound: SwitchSound

    val loginViewModel by viewModels<LoginViewModel>()

    private lateinit var binding: ActivityLoginBinding

    companion object {
        const val ReconnectionKey: String = "ReconnectionKey"
        const val RegisterUrl: String = "http://freenetaccess.freeboxos.fr:8002/register"
        fun startReconnection(context: Context): Context {
            val intent = Intent(context, LoginActivity::class.java)
            intent.putExtra(ReconnectionKey, true)
            context.startActivity(intent)
            return context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCurrentTheme.execute()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)


        loginViewModel.navigateToDashboard.observe(this) {
            DashboardActivity.start(this@LoginActivity)
        }
        loginViewModel.navigateToInitSetup.observe(this) {
            InitSetupActivity.startWithUserConfirmation(this@LoginActivity)
        }
        loginViewModel.initialize(intent.getBooleanExtra(ReconnectionKey, false))

        loginViewModel.invalidPassword.observe(this) {
            binding.etPassword.error = resources.getString(R.string.invalid_password_msg)
        }
        loginViewModel.invalidUsername.observe(this) {
            binding.etLogin.error = resources.getString(R.string.invalid_username_msg)
        }
        loginViewModel.unavailableNetwork.observe(this) {
            Toast.makeText(this, R.string.network_not_available_msg, Toast.LENGTH_LONG).show()
        }
        loginViewModel.userNotFound.observe(this) {
            Toast.makeText(this, R.string.user_not_exist_msg, Toast.LENGTH_LONG).show()
        }
        loginViewModel.unexpectedError.observe(this) {
            Toast.makeText(this, R.string.unexpected_error_msg, Toast.LENGTH_LONG).show()
        }

        loginViewModel.isLogging.observe(this) { isLogging ->
            if (isLogging) {
                removeInputsErrors()
                setViewVisibility(ProgressBar.VISIBLE)
            } else {
                setViewVisibility(ProgressBar.GONE)
            }
        }

        initInputs()
    }

    private fun initInputs() {
        binding.tiPassword.setText(loginViewModel.user.value?.password)
        binding.tiLogin.setText(loginViewModel.user.value?.username)
        binding.tiPassword.addTextChangedListener(this@LoginActivity)
        binding.tiLogin.addTextChangedListener(this@LoginActivity)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.btnLogin -> loginViewModel.tryLogin()
            binding.btnRegister -> openBrowserForRegister()
        }
    }


    private fun removeInputsErrors() {
        binding.etLogin.error = null
        binding.etPassword.error = null
        binding.tvErrorString.visibility = TextView.GONE
    }


    private fun openBrowserForRegister() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(RegisterUrl))
        startActivity(browserIntent)
    }

    private fun setViewVisibility(state: Int) {
        if (state == ProgressBar.GONE) {
            binding.btnLogin.isEnabled = true
            binding.btnRegister.isEnabled = true
        } else if (state == ProgressBar.VISIBLE) {
            binding.btnLogin.isEnabled = false
            binding.btnRegister.isEnabled = false
        }
        binding.loader.visibility = state
    }

    override fun afterTextChanged(s: Editable?) {
        loginViewModel.updatePassword(binding.tiPassword.text.toString())
        loginViewModel.updateUsername(binding.tiLogin.text.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }


}