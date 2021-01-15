package com.esgi.nova

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.esgi.nova.infrastructure.data.AppDatabase
import com.esgi.nova.dtos.user.ConnectedUserDto
import com.esgi.nova.events.application.SynchronizeEventsToLocalStorage
import com.esgi.nova.infrastructure.preferences.PreferenceConstants
import com.esgi.nova.users.application.LogUser
import com.esgi.nova.dtos.user.UserLoginDto
import com.esgi.nova.users.application.LogUserWithToken
import com.esgi.nova.users.exceptions.InvalidPasswordException
import com.esgi.nova.users.exceptions.InvalidUsernameException
import com.esgi.nova.users.exceptions.UserNotFoundException
import com.esgi.nova.utils.NetworkUtils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.doAsync
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
    @Inject
    lateinit var logUserWithToken: LogUserWithToken


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        checkExistingToken()

        btn_login.setOnClickListener(this)
        btn_register.setOnClickListener(this)
        val test = AppDatabase.getAppDataBase(this)
        println(test)
    }

    private fun checkExistingToken() {
        val sharedPref = this@Login.getSharedPreferences(PreferenceConstants.UserKey, MODE_PRIVATE)
        val token = sharedPref.getString(PreferenceConstants.TokenKey, null)
        if (token !== null) {
            setViewVisibility(ProgressBar.VISIBLE)
            if(NetworkUtils.isNetworkAvailable(this)){
                doAsync {
                    try{
                        logUserWithToken.execute(token)
                        runOnUiThread {
                            setViewVisibility(ProgressBar.GONE)
                            navigateToHomePage()
                        }
                    } catch (e: UserNotFoundException){
                        runOnUiThread {
                            setViewVisibility(ProgressBar.GONE)
                            val toast = Toast.makeText(
                                this@Login,
                                getString(R.string.user_have_to_re_auth_msg),
                                Toast.LENGTH_LONG
                            )
                            toast.show()
                        }

                    }
                }
            }
        }
    }

    override fun onClick(view: View?) {
        if (view == btn_login) {
            loginClick()
        } else if(view == btn_register) {
            registerClick();
        }
    }

    private fun registerClick() {
        val uri: Uri = Uri.parse("http://freenetaccess.freeboxos.fr:8002/register")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
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
                    navigateToHomePage()
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


    private fun navigateToHomePage() {
        val intent = Intent(this, Dashboard::class.java)
        startActivity(intent)
        finish()
    }

}