package com.esgi.nova

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.esgi.nova.infrastructure.data.AppDatabase
import com.esgi.nova.events.application.SynchronizeEventsToLocalStorage
import com.esgi.nova.users.application.LogUser
import com.esgi.nova.dtos.user.UserLoginDto
import com.esgi.nova.users.exceptions.InvalidPasswordException
import com.esgi.nova.users.exceptions.InvalidUsernameException
import com.esgi.nova.users.exceptions.UserNotFoundException
import com.esgi.nova.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.doAsync
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var service: SynchronizeEventsToLocalStorage
    @Inject
    lateinit var logUser: LogUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_login.setOnClickListener(this)
        val test = AppDatabase.getAppDataBase(this)
        println(test)
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
                    navigateToInitSetupPage()
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
        } else if (state == ProgressBar.VISIBLE) {
            btn_login.isEnabled = false
        }
        progress_overlay.visibility = state
    }


    private fun navigateToInitSetupPage() {
        val intent = Intent(this, InitSetupActivity::class.java)
        startActivity(intent)
        finish()
    }

}