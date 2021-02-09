package com.esgi.nova.login

import android.os.IBinder
import android.view.View
import android.view.WindowManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.esgi.nova.R
import com.esgi.nova.users.ui.LoginActivity
import com.esgi.nova.users.ui.LoginActivityModule
import com.esgi.nova.users.ui.LoginViewModelFactory
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.Description
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("UNCHECKED_CAST")
@HiltAndroidTest
@UninstallModules(LoginActivityModule::class)
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    val invalidPassword = MutableLiveData<Boolean>()
    val invalidUsername = MutableLiveData<Boolean>()
    val unexpectedError = MutableLiveData<Boolean>()
    val unavailableNetwork = MutableLiveData<Boolean>()

    val fakeViewModel =
        LoginViewModelFake(
            unexpectedError = unexpectedError,
            _invalidPassword = invalidPassword,
            _invalidUsername = invalidUsername,
            _unavailableNetwork = unavailableNetwork
        )

    @BindValue
    @JvmField
    val viewModelFactory: LoginViewModelFactory = object : LoginViewModelFactory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return fakeViewModel as T
        }
    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

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
        fakeViewModel.isUsernameValid = false
        onView(withId(R.id.btn_login)).perform(click())
        onView(withId(R.id.et_login)).check(matches(isDisplayed()))
        onView(withText(R.string.invalid_username_msg))
            .check(matches(isDisplayed()))
    }


    @Test
    fun displayUnexpectedErrorToast_on_UnexpectedError() {
        unexpectedError.postValue(true)
        onView(allOf(withId(R.id.message_tv), withText(R.string.unexpected_error_msg)))
            .check(
                matches(
                    isDisplayed()
                )
            )

    }

    @Test
    fun displayNetworkError_on_unavailableNetwork() {
       unavailableNetwork.value = true
        onView(allOf(withId(R.id.message_tv), withText(R.string.network_not_available_msg)))
            .check(
                matches(
                    isDisplayed()
                )
            )
    }


}
