package com.esgi.nova.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.esgi.nova.R
import com.esgi.nova.users.exceptions.InvalidUsernameException
import com.esgi.nova.users.exceptions.UserNotFoundException
import com.esgi.nova.users.infrastructure.api.models.LogUser
import com.esgi.nova.users.ports.ILogUser
import com.esgi.nova.users.ui.LoginActivity
import com.esgi.nova.users.ui.LoginActivityModule
import com.esgi.nova.users.ui.LoginViewModelFactory
import com.esgi.nova.users.ui.view_models.LoginViewModel
import com.nhaarman.mockitokotlin2.*
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@Suppress("UNCHECKED_CAST")
@HiltAndroidTest
@UninstallModules(LoginActivityModule::class)
@RunWith(AndroidJUnit4::class)
class LoginActionTest {

    private val fakeUser = LogUser("Johnathan", "Doeby")
    private val shortNameFakeUser = LogUser("jonnnny", "Dowyvjjy")


    val fakeViewModel = LoginViewModel(
        logInUser = mock {
            onBlocking { execute(fakeUser) } doAnswer {}
            onBlocking { execute(shortNameFakeUser) } doThrow(UserNotFoundException())
        },
        hasConnectedUser = mock { on { execute() } doReturn (false) },
        logOutUser = mock { on { execute() } doAnswer {} },
        retrieveUser = mock { on { execute() } doAnswer { null } },
        isSynchronized = mock { on { execute() } doReturn (false) },
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
    fun myTestWorks() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        //Assert.assertEquals(true, true)
    }

    @Test
    fun login_doesnt_work_with_bad_credentials() {

        onView(ViewMatchers.withId(R.id.ti_login)).perform(ViewActions.clearText())
        onView(ViewMatchers.withId(R.id.ti_login)).perform(ViewActions.typeText(shortNameFakeUser.username))
        onView(ViewMatchers.withId(R.id.ti_password)).perform(ViewActions.clearText())
        onView(ViewMatchers.withId(R.id.ti_password)).perform(ViewActions.typeText(shortNameFakeUser.password))
        onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click())


        onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.message_tv),
                ViewMatchers.withText(R.string.user_not_exist_msg)
            )
        )
            .check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()
                )
            )

    }
}