package com.esgi.nova

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.esgi.nova.users.infrastructure.api.AuthApiRepository
import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import com.esgi.nova.users.ports.IHasConnectedUser
import com.esgi.nova.users.ui.LoginActivity
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

open class HasConnectedUserFakeTrue : IHasConnectedUser {
    override fun execute(): Boolean = true
}

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class LoginBehaviorTest {


    @get:Rule
    var activityRule: ActivityScenarioRule<LoginActivity>
            = ActivityScenarioRule(LoginActivity::class.java)



    @Test
    fun connection_needs_not_empty_string() {
//        // Type text and then press the button.
//        onView(withId(R.id.ti_login))
//            .perform(typeText(stringToBetyped), closeSoftKeyboard())
//        onView(withId(R.id.changeTextBt)).perform(click())
//
//        // Check that the text was changed.
//        onView(withId(R.id.textToBeChanged))
//            .check(matches(withText(stringToBetyped)))

        activityRule.

        onView(withId(R.id.btn_login)).perform(click())

        onView(withId(R.id.et_login)).check(matches(withText(R.string.invalid_username_msg)))




    }
}