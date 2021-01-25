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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.esgi.nova.R
import com.esgi.nova.application_state.application.IsSynchronized
import com.esgi.nova.databinding.ActivityLoginBinding
import com.esgi.nova.dtos.user.UserLoginDto
import com.esgi.nova.events.application.SynchronizeEvents
import com.esgi.nova.parameters.application.SetCurrentTheme
import com.esgi.nova.sound.application.SwitchSound
import com.esgi.nova.ui.dashboard.DashboardActivity
import com.esgi.nova.ui.init.InitSetupActivity
import com.esgi.nova.users.application.HasConnectedUser
import com.esgi.nova.users.application.LogInUser
import com.esgi.nova.users.application.LogOutUser
import com.esgi.nova.users.application.RetrieveUser
import com.esgi.nova.users.exceptions.InvalidPasswordException
import com.esgi.nova.users.exceptions.InvalidUsernameException
import com.esgi.nova.users.ui.view_models.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
//class LoginViewModelFactory(
//    private val dataSource: SleepDatabaseDao,
//    private val application: Application) : ViewModelProvider.Factory {
//    @Suppress("unchecked_cast")
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
//            return LoginViewModel(dataSource, application) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), View.OnClickListener, TextWatcher, CoroutineScope  {


    @Inject
    lateinit var logInUser: LogInUser

    @Inject
    lateinit var hasConnectedUser: HasConnectedUser

    @Inject
    lateinit var setCurrentTheme: SetCurrentTheme

    @Inject
    lateinit var logOutUser: LogOutUser

    @Inject
    lateinit var retrieveUser: RetrieveUser

    @Inject
    lateinit var switchSound: SwitchSound

    @Inject
    lateinit var isSynchronized: IsSynchronized

    val loginViewModel by viewModels<LoginViewModel>()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job
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
        job = Job()

        if (!loginViewModel.initialized) {
            doAsync {
                if (intent.getBooleanExtra(ReconnectionKey, false)) {
                    logOutUser.execute()
                    initUser()
                } else if (hasConnectedUser.execute()) {
                    if (isSynchronized.execute()) {
                        DashboardActivity.start(this@LoginActivity)
                    } else {
                        InitSetupActivity.startWithUserConfirmation(this@LoginActivity)
                    }
                } else {
                    initUser()
                }
                loginViewModel.initialized = true
            }
        } else {
            initInputs()
        }

    }

    private fun initUser() {
        retrieveUser.execute()?.let { user -> loginViewModel.copyUser(user) }
        runOnUiThread {
            initInputs()
        }
    }

    private fun initInputs() {
        binding.tiPassword.setText(loginViewModel.password)
        binding.tiLogin.setText(loginViewModel.username)
        binding.tiPassword.addTextChangedListener(this@LoginActivity)
        binding.tiLogin.addTextChangedListener(this@LoginActivity)
    }


    override fun onClick(view: View?) {
        when (view) {
            binding.btnLogin -> loginClick()
            binding.btnRegister -> openBrowserForRegister()
        }
    }

    private fun loginClick() {
        val userLoginDto = UserLoginDto(
            username = binding.tiLogin.text.toString().trim(),
            password = binding.tiPassword.text.toString().trim()
        )
        resetTextviewColors()
        setViewVisibility(ProgressBar.VISIBLE)
        try {
            userLoginDto.validate()
//            if(NetworkUtils.isNetworkAvailable(this)){
            return login(userLoginDto)
//            } else {
//                setViewVisibility(ProgressBar.GONE)
//                val toast =
//                    Toast.makeText(this, getString(R.string.network_not_available_msg), Toast.LENGTH_LONG)
//                toast.show()
//            }
        } catch (e: InvalidUsernameException) {
            binding.etLogin.error = resources.getString(R.string.invalid_username_msg)
        }catch(e: InvalidPasswordException){
            binding.etPassword.error = resources.getString(R.string.invalid_password_msg)
        }
        setViewVisibility(ProgressBar.GONE)

    }

    private fun resetTextviewColors() {
        binding.etLogin.error = null
        binding.etPassword.error = null
        binding.tvErrorString.visibility = TextView.GONE
    }

    private fun login(user: UserLoginDto) {

            launch {
                try{
                    logInUser.execute(user)
                }
                catch (e: Exception){
                    println(e)
                }
                val isSynchronized = isSynchronized.execute()
                runOnUiThread {
                    setViewVisibility(ProgressBar.GONE)
                    if (isSynchronized) {
                        DashboardActivity.start(this@LoginActivity)
                    } else {
                        InitSetupActivity.startWithUserConfirmation(this@LoginActivity)
                    }
                }
            }
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
        when (s) {
            binding.tiPassword -> loginViewModel.password = binding.tiPassword.text.toString()
            binding.tiLogin -> loginViewModel.username = binding.tiLogin.text.toString()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }


}