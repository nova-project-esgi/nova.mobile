package com.esgi.nova

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.esgi.nova.infrastructure.data.AppDatabase
import com.esgi.nova.users.dtos.ConnectedUserDto
import com.esgi.nova.events.application.SynchronizeEventsToLocalStorage
import com.esgi.nova.infrastructure.preferences.PreferenceConstants
import com.esgi.nova.users.application.LogUser
import com.esgi.nova.users.dtos.UserLoginDto
import com.esgi.nova.users.exceptions.InvalidPasswordException
import com.esgi.nova.users.exceptions.InvalidUsernameException
import com.esgi.nova.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class Login : AppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var service: SynchronizeEventsToLocalStorage;
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
        logUser.execute(user, object :
            Callback<ConnectedUserDto> {
            override fun onResponse(
                call: Call<ConnectedUserDto>,
                response: Response<ConnectedUserDto>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val sharedPref = this@Login.getSharedPreferences(PreferenceConstants.UserKey, MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putString(PreferenceConstants.TokenKey, it.token)
                            apply()
                        }
                    }
                    setViewVisibility(ProgressBar.GONE)
                    navigateToHomePage()
                } else {
                    setViewVisibility(ProgressBar.GONE)
                    val toast = Toast.makeText(
                        this@Login,
                        getString(R.string.user_not_exist_msg),
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                }
            }

            override fun onFailure(call: Call<ConnectedUserDto>, t: Throwable) {
                setViewVisibility(ProgressBar.GONE)
                val toast = Toast.makeText(
                    this@Login,
                    getString(R.string.connection_err_msg),
                    Toast.LENGTH_LONG
                )
                toast.show()
            }

        })
    }

    fun setViewVisibility(state: Int) {
        if (state == ProgressBar.GONE) {
            btn_login.isEnabled = true
        } else if (state == ProgressBar.VISIBLE) {
            btn_login.isEnabled = false
        }
        progress_overlay.visibility = state
    }


    private fun navigateToHomePage() {
        val intent = Intent(this, Dashboard::class.java)
        startActivity(intent)
        finish()
    }

}