package com.esgi.nova

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.esgi.nova.users.ui.LoginActivity
import com.esgi.nova.users.ui.LoginActivityModule
import com.esgi.nova.users.ui.LoginViewModelFactory
import com.esgi.nova.users.ui.view_models.LoginViewModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@Suppress("UNCHECKED_CAST")
@HiltAndroidTest
@UninstallModules(LoginActivityModule::class)
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    private val viewModel = mock<LoginViewModel>()
    {
        on{userNotFound} doReturn MutableLiveData()
        on{user} doReturn MutableLiveData()
        on{unavailableNetwork} doReturn MutableLiveData()
        on{unexpectedError} doReturn MutableLiveData()
        on{invalidUsername} doReturn MutableLiveData()
        on{invalidPassword} doReturn MutableLiveData()
        on{isLoading} doReturn MutableLiveData()
        on{navigateToInitSetup} doReturn MutableLiveData()
        on{navigateToDashboard} doReturn MutableLiveData()
    }

    @BindValue
    @JvmField
    val viewModelFactory: LoginViewModelFactory = object : LoginViewModelFactory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return viewModel as T
        }
    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun test_DisplaySum_WhenSumLiveDataChange() {
        //Given
        val scenario = launchActivity<LoginActivity>()
        //When
//        sum.postValue(10)
//        Then
        onView(withId(R.id.btn_login))
            .perform(click())
//
//        onView(withId(R.id.et_login)).check(matches(withText("")))
    }
}