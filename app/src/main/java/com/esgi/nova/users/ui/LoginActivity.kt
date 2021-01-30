package com.esgi.nova.users.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esgi.nova.R
import com.esgi.nova.application_state.application.IsSynchronized
import com.esgi.nova.databinding.ActivityLoginBinding
import com.esgi.nova.parameters.application.SetCurrentTheme
import com.esgi.nova.sound.application.SwitchSound
import com.esgi.nova.ui.dashboard.DashboardActivity
import com.esgi.nova.ui.init.InitSetupActivity
import com.esgi.nova.users.application.HasConnectedUser
import com.esgi.nova.users.application.LogInUser
import com.esgi.nova.users.application.LogOutUser
import com.esgi.nova.users.application.RetrieveUser
import com.esgi.nova.users.ui.view_models.BaseLoginViewModel
import com.esgi.nova.users.ui.view_models.LoginViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import org.jetbrains.anko.longToast
import javax.inject.Inject

interface LoginViewModelFactory : ViewModelProvider.Factory

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactoryImpl (
    private val logInUser: LogInUser,
    private val hasConnectedUser: HasConnectedUser,
    private val logOutUser: LogOutUser,
    private val retrieveUser: RetrieveUser,
    private val isSynchronized: IsSynchronized,
) : LoginViewModelFactory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(
            logInUser = logInUser,
            hasConnectedUser = hasConnectedUser,
            logOutUser = logOutUser,
            retrieveUser = retrieveUser,
            isSynchronized = isSynchronized
        ) as T
    }
}

@Module
@InstallIn(ActivityComponent::class)
class LoginActivityModule {

    @Provides
    fun provideCalculatorViewModelFactory(
        logInUser: LogInUser,
        hasConnectedUser: HasConnectedUser,
        logOutUser: LogOutUser,
        retrieveUser: RetrieveUser,
        isSynchronized: IsSynchronized,
    ): LoginViewModelFactory =
        LoginViewModelFactoryImpl(
            logInUser = logInUser,
            hasConnectedUser = hasConnectedUser,
            logOutUser = logOutUser,
            retrieveUser = retrieveUser,
            isSynchronized = isSynchronized
        )
}

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {


    @Inject
    lateinit var setCurrentTheme: SetCurrentTheme

    @Inject
    lateinit var switchSound: SwitchSound


    @Inject lateinit var viewModelFactory: LoginViewModelFactory
    private lateinit var viewModel: BaseLoginViewModel

//    private val viewModel:ILoginViewModel by viewModels<LoginViewModel>()

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

        fun start(context: Context): Context {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
            return context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCurrentTheme.execute()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BaseLoginViewModel::class.java)

        setContentView(binding.root)

        binding.btnLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)


        viewModel.navigateToDashboard.observe(this) {
            DashboardActivity.start(this@LoginActivity)
        }
        viewModel.navigateToInitSetup.observe(this) {
            InitSetupActivity.startWithUserConfirmation(this@LoginActivity)
        }

        viewModel.invalidPassword.observe(this) {
            binding.etPassword.error = resources.getString(R.string.invalid_password_msg)
        }
        viewModel.invalidUsername.observe(this) {
            runOnUiThread {
                binding.etLogin.error = resources.getString(R.string.invalid_username_msg)
            }
        }

        viewModel.unavailableNetwork.observe(this) {
            Toast.makeText(this, R.string.network_not_available_msg, Toast.LENGTH_LONG).show()
        }
        viewModel.userNotFound.observe(this) {
            Toast.makeText(this, R.string.user_not_exist_msg, Toast.LENGTH_LONG).show()
        }
        viewModel.unexpectedError.observe(this) {
            runOnUiThread {
                Toast.makeText(this, R.string.unexpected_error_msg, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.isLoading.observe(this) { isLogging ->
            if (isLogging) {
                removeInputsErrors()
                setViewVisibility(VISIBLE)
            } else {
                setViewVisibility(GONE)
            }
        }
        viewModel.initialize(intent.getBooleanExtra(ReconnectionKey, false))
        initInputs()
    }

    private fun initInputs() {
        binding.tiPassword.setText(viewModel.user.value?.password)
        binding.tiLogin.setText(viewModel.user.value?.username)
        binding.tiPassword.addTextChangedListener(this@LoginActivity)
        binding.tiLogin.addTextChangedListener(this@LoginActivity)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.btnLogin -> viewModel.tryLogin()
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
        if (state == GONE) {
            binding.btnLogin.isEnabled = true
            binding.btnRegister.isEnabled = true
        } else if (state == VISIBLE) {
            binding.btnLogin.isEnabled = false
            binding.btnRegister.isEnabled = false
        }
        binding.root.loaderFl.visibility = state
    }

    override fun afterTextChanged(s: Editable?) {
        viewModel.updatePassword(binding.tiPassword.text.toString())
        viewModel.updateUsername(binding.tiLogin.text.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }


}