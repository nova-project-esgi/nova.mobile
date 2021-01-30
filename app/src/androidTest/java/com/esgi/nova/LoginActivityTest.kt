package com.esgi.nova

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.esgi.nova.users.ui.LoginActivity
import com.esgi.nova.users.ui.LoginActivityModule
import com.esgi.nova.users.ui.LoginViewModelFactory
import com.esgi.nova.users.ui.models.LogUser
import com.esgi.nova.users.ui.view_models.BaseLoginViewModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class LoginViewModelFake(
    private var _user: MutableLiveData<LogUser> = MutableLiveData<LogUser>(),
    private var _navigateToDashboard: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    private var _navigateToInitSetup: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    private var _invalidUsername: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    private var _invalidPassword: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    private var _userNotFound: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    private var _unavailableNetwork: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    override val unexpectedError: LiveData<Boolean>
) : BaseLoginViewModel() {

    override val user: LiveData<LogUser> get() = _user
    override val navigateToDashboard: LiveData<Boolean> get() = _navigateToDashboard
    override val navigateToInitSetup: LiveData<Boolean> get() = _navigateToInitSetup
    override val invalidUsername: LiveData<Boolean> get() = _invalidUsername
    override val invalidPassword: LiveData<Boolean> get() = _invalidPassword
    override val userNotFound: LiveData<Boolean> get() = _userNotFound
    override val unavailableNetwork: LiveData<Boolean> get() = _unavailableNetwork
    override fun initialize(isReconnection: Boolean) {

    }

    override fun updateUsername(username: String) {

    }

    override fun updatePassword(password: String) {

    }

    override fun tryLogin() {

    }

}


@Suppress("UNCHECKED_CAST")
@HiltAndroidTest
@UninstallModules(LoginActivityModule::class)
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    private lateinit var decorView: View

    val invalidPassword = MutableLiveData<Boolean>()
    val invalidUsername = MutableLiveData<Boolean>()
    val unexpectedError = MutableLiveData<Boolean>()
    val fakeViewModel =
        LoginViewModelFake(
            unexpectedError = unexpectedError,
            _invalidPassword = invalidPassword,
            _invalidUsername = invalidUsername
        )

    @BindValue
    @JvmField
    val viewModelFactory: LoginViewModelFactory = object : LoginViewModelFactory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return fakeViewModel as T
        }
    }

//    @Before
//    fun setUp() {
//        activityRule.scenario.onActivity {
//            decorView = it.window.decorView;
//        };
//    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    //Execute synchronously live data observables
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var activityRule: ActivityScenarioRule<LoginActivity> =
        ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun displayError_on_invalidPassword() {
        invalidPassword.value = true
        onView(withId(R.id.et_password)).check(matches(isDisplayed()))
    }

    @Test
    fun displayError_on_invalidUsername() {
        invalidUsername.value = true
        onView(withId(R.id.et_password)).check(matches(isDisplayed()))
    }

    @Test
    fun displayUnexpectedErrorToast_on_UnexpectedError() {
        activityRule.scenario.onActivity {
            unexpectedError.value = true

            onView(withText(R.string.unexpected_error_msg))
                .inRoot(withDecorView(not( it.window.decorView)))// Here we use decorView
                .check(matches(isDisplayed()))
            return@onActivity
        }

    }



    @After
    fun cleanup() {
        activityRule.scenario.close()
    }
}