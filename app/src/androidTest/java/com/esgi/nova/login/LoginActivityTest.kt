package com.esgi.nova.login

import android.os.IBinder
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Root
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.RootMatchers.withDecorView
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
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


open class ToastMatcher : TypeSafeMatcher<Root>() {

    override fun describeTo(description: Description) {
        description?.appendText("is toast")
    }

    override fun matchesSafely(item: Root): Boolean {
        val type: Int? = item.windowLayoutParams?.get()?.type
        if (type == WindowManager.LayoutParams.TYPE_TOAST) {
            val windowToken: IBinder = item.decorView.windowToken
            val appToken: IBinder = item.decorView.applicationWindowToken
            if (windowToken === appToken) { // means this window isn't contained by any other windows.
                return true
            }
        }
        return false
    }

}

@Suppress("UNCHECKED_CAST")
@HiltAndroidTest
@UninstallModules(LoginActivityModule::class)
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

//    private lateinit var decorView: View

    private lateinit var decorView: View

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

//    @BindValue
//    @JvmField
//    val dashboardFactory: IDashboardViewModelFactory = item.windowLayoutParams?.get()object : IDashboardViewModelFactory {
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            return fakeViewModel as T
//        }
//    }

//
//    @Before
//    fun setUp() {
//        activityRule.scenario.onActivity {
//            decorView = it.window.decorView
//        }
//    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    //Execute synchronously live data observables
//    @get:Rule
//    val rule = InstantTaskExecutorRule()

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
//        unexpectedError.value = true

//        Toast.makeText(rule.scR.string.unexpected_error_msg)
//        ShadowToast
        onView(withText(R.string.unexpected_error_msg))
            .check(matches(isDisplayed()))

//        onView(withText(R.string.unexpected_error_msg))
//            .inRoot(ToastMatcher())// Here we use decorView
//            .check(matches(isDisplayed()))

//        activityRule.scenario.onActivity {
//            unexpectedError.value = true
//
//            onView(withText(R.string.unexpected_error_msg))
//                .inRoot(withDecorView(not(it.window.decorView)))// Here we use decorView
//                .check(matches(isDisplayed()))
//            return@onActivity
//        }

    }
//    @Test
//    @Ignore
//    fun displayNetworkError_on_unavailableNetwork() {
//
//        activityRule.scenario.onActivity {
//            unavailableNetwork.value = true
//
//            onView(withText(R.string.network_not_available_msg))
//                .inRoot(withDecorView(not(it.window.decorView)))// Here we use decorView
//                .check(matches(isDisplayed()))
//            return@onActivity
//        }
//    }


}

