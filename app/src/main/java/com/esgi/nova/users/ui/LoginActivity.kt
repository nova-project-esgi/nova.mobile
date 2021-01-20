package com.esgi.nova.users.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.esgi.nova.ui.init.InitSetupActivity
import com.esgi.nova.R
import com.esgi.nova.dtos.user.UserLoginDto
import com.esgi.nova.events.application.SynchronizeEvents
import com.esgi.nova.parameters.application.SetCurrentTheme
import com.esgi.nova.users.application.HasConnectedUser
import com.esgi.nova.users.application.LogInUser
import com.esgi.nova.users.application.LogOutUser
import com.esgi.nova.users.application.RetrieveUser
import com.esgi.nova.users.exceptions.InvalidPasswordException
import com.esgi.nova.users.exceptions.InvalidUsernameException
import com.esgi.nova.users.exceptions.UserNotFoundException
import com.esgi.nova.users.ui.view_models.LoginViewModel
import com.esgi.nova.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.doAsync
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {

    @Inject
    lateinit var service: SynchronizeEvents

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

    val loginViewModel by viewModels<LoginViewModel>()


    companion object {
        const val ReconnectionKey: String = "ReconnectionKey"
        fun startReconnection(context: Context): Context {
            val intent = Intent(context, LoginActivity::class.java)
            intent.putExtra(ReconnectionKey, true)
            context.startActivity(intent)
            return context
        }

        fun startLoginActivity(context: Context): Context {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
            return context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCurrentTheme.execute()
        setContentView(R.layout.activity_login)
        btn_login.setOnClickListener(this)

        if (!loginViewModel.initialized) {
            doAsync {
                if (intent.getBooleanExtra(ReconnectionKey, false)) {
                    logOutUser.execute()
                    initUser()
                } else if (hasConnectedUser.execute()) {
                    InitSetupActivity.start(this@LoginActivity)
                    finish()
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
        ti_password?.setText(loginViewModel.password)
        ti_login?.setText(loginViewModel.username)
        ti_password?.addTextChangedListener(this@LoginActivity)
        ti_login?.addTextChangedListener(this@LoginActivity)
    }


    override fun onClick(view: View?) {
        if (view == btn_login) {
            loginClick()
        }
    }

    private fun loginClick() {
        val userLoginDto = UserLoginDto(
            username = ti_login?.text.toString().trim(),
            password = ti_password?.text.toString().trim()
        )
        setViewVisibility(ProgressBar.VISIBLE)
        try{
            userLoginDto.validate()
            if(NetworkUtils.isNetworkAvailable(this)){
                return login(userLoginDto)
            } else {
                setViewVisibility(ProgressBar.GONE)
                val toast =
                    Toast.makeText(this, getString(R.string.network_not_available_msg), Toast.LENGTH_LONG)
                toast.show()
            }
        }catch (e: InvalidUsernameException){
            Toast.makeText(
                this,
                getString(R.string.invalid_username_msg),
                Toast.LENGTH_LONG
            ).show()
        }catch(e: InvalidPasswordException){
            Toast.makeText(
                this,
                getString(R.string.invalid_password_msg),
                Toast.LENGTH_LONG
            ).show()
        }
        setViewVisibility(ProgressBar.GONE)

    }

    private fun login(user: UserLoginDto) {
        doAsync {
            try{
                logInUser.execute(user)
                runOnUiThread {
                    setViewVisibility(ProgressBar.GONE)
                    InitSetupActivity.start(this@LoginActivity)
                    finish()
                }
            } catch (e: UserNotFoundException){
                runOnUiThread {
                    setViewVisibility(ProgressBar.GONE)
                    val toast = Toast.makeText(
                        this@LoginActivity,
                        getString(R.string.user_not_exist_msg),
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                }

            }
        }
    }

    private fun setViewVisibility(state: Int) {
        if (state == ProgressBar.GONE) {
            btn_login.isEnabled = true
            btn_register.isEnabled = true
        } else if (state == ProgressBar.VISIBLE) {
            btn_login.isEnabled = false
            btn_register.isEnabled = false
        }
        progress_overlay.visibility = state
    }

    override fun afterTextChanged(s: Editable?) {
        when (s) {
            ti_password -> loginViewModel.password = ti_password?.text.toString()
            ti_login -> loginViewModel.username = ti_login?.text.toString()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }
}