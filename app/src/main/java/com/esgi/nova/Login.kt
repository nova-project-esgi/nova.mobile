package com.esgi.nova

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.esgi.nova.network.auth.AuthRepository
import com.esgi.nova.utils.NetworkUtils
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view == btn_login) {
            loginClick()
        }
    }

    private fun loginClick() {
        val login = ti_login.text.toString().trim()
        val password = ti_password.text.toString().trim()
        setViewVisibility(ProgressBar.VISIBLE)
        if (login.isNotEmpty() && login.length >= 6 && login.length <= 20 && password.isNotEmpty() && password.length >= 8) {
            if (NetworkUtils.isNetworkAvailable(this)) {
                login(login, password)
            } else {
                setViewVisibility(ProgressBar.GONE)
                val toast =
                    Toast.makeText(this, "Le réseau n'est pas disponible", Toast.LENGTH_LONG)
                toast.show()
            }
        } else {
            val toast: Toast
            if (login.length < 6 || login.length > 20) {
                toast = Toast.makeText(
                    this,
                    "L'identifiant doit avoir entre 6 et 20 caractères",
                    Toast.LENGTH_LONG
                )
            } else {
                toast = Toast.makeText(
                    this,
                    "le mot de passe doit avoir au minimum 8 caractères",
                    Toast.LENGTH_LONG
                )
            }
            setViewVisibility(ProgressBar.GONE)
            toast.show()
        }
    }

    private fun login(login: String, password: String) {
        AuthRepository.logWithUsernameAndPassword(login, password, object :
            Callback<ConnectedUserDTO> {
            override fun onResponse(
                call: Call<ConnectedUserDTO>,
                response: Response<ConnectedUserDTO>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val sharedPref = this@Login.getSharedPreferences("user",MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putString("token", it.token)
                            apply()
                        }
                    }
                    setViewVisibility(ProgressBar.GONE)
                    navigateToHomePage()
                } else {
                    setViewVisibility(ProgressBar.GONE)
                    val toast = Toast.makeText(
                        this@Login,
                        "l'identifiant ou le mot de passe ne correspond pas",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                }
            }

            override fun onFailure(call: Call<ConnectedUserDTO>, t: Throwable) {
                setViewVisibility(ProgressBar.GONE)
                val toast = Toast.makeText(
                    this@Login,
                    "Une erreur est survenue lors de la connexion",
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