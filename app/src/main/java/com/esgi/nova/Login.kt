package com.esgi.nova

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.esgi.nova.events.application.SynchronizeEventsToLocalStorage
import com.esgi.nova.users.application.LogUser
import com.esgi.nova.dtos.user.UserLoginDto
import com.esgi.nova.users.application.HasConnectedUser
import com.esgi.nova.users.exceptions.InvalidPasswordException
import com.esgi.nova.users.exceptions.InvalidUsernameException
import com.esgi.nova.users.exceptions.UserNotFoundException
import com.esgi.nova.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.doAsync
import javax.inject.Inject

@AndroidEntryPoint
class Login : AppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var service: SynchronizeEventsToLocalStorage;
    @Inject
    lateinit var logUser: LogUser
    @Inject
    lateinit var hasConnectedUser: HasConnectedUser

    companion object {
        const val ReconnectionKey: String = "ReconnectionKey"
        fun startReconnection(context: Context): Context{
            val intent = Intent(context, Login::class.java)
            intent.extras?.putBoolean(ReconnectionKey, true )
            context.startActivity(intent)
            return context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_login.setOnClickListener(this)

        if(hasConnectedUser.execute() && !intent.getBooleanExtra(ReconnectionKey, false)){
            InitSetup.startInitSetup(this@Login)
            finish()
        }
    }



    override fun onClick(view: View?) {
        if (view == btn_login) {
            loginClick()
        }
    }

    private fun loginClick() {
        val userLoginDto = UserLoginDto(
            username = ti_login.text.toString().trim(),
            password = ti_password.text.toString().trim()
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
                logUser.execute(user)
                runOnUiThread {
                    setViewVisibility(ProgressBar.GONE)
                    InitSetup.startInitSetup(this@Login)
                    finish()
                }
            } catch (e: UserNotFoundException){
                runOnUiThread {
                    setViewVisibility(ProgressBar.GONE)
                    val toast = Toast.makeText(
                        this@Login,
                        getString(R.string.user_not_exist_msg),
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                }

            }
        }
    }

    fun setViewVisibility(state: Int) {
        if (state == ProgressBar.GONE) {
            btn_login.isEnabled = true
            btn_register.isEnabled = true
        } else if (state == ProgressBar.VISIBLE) {
            btn_login.isEnabled = false
            btn_register.isEnabled = false
        }
        progress_overlay.visibility = state
    }





}