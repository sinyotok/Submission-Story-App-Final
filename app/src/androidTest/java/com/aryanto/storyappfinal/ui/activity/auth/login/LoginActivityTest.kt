package com.aryanto.storyappfinal.ui.activity.auth.login

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.aryanto.storyappfinal.R
import com.aryanto.storyappfinal.utils.EspressoIdlingResource
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.endsWith
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class LoginActivityTest {

    private val validEmail = "wili@gmail.com"
    private val validPass = "qweasdzxc"

    private val wrongEmail = "awilia@gmail.com"
    private val wrongPass = "password"

    private val invalidEmail = "wili.com"
    private val invalidPass = "pass123"

    private val emptyInput = ""

    @get:Rule
    val activity = ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        Intents.release()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun emptyPasswordAndEmailLogin() {
        onView(withId(R.id.btn_submit_login))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_login)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(emptyInput))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_login))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_login)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(validEmail))
        Espresso.closeSoftKeyboard()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.password_edt_login)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(emptyInput))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_login))
            .check(matches(isDisplayed()))
            .perform(click())

    }

    @Test
    fun invalidPasswordAndEmailLogin() {
        onView(withId(R.id.btn_submit_login))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_login)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(invalidEmail))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_login))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_login)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), clearText())
        Espresso.closeSoftKeyboard()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_login)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(validEmail))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_login))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.password_edt_login)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(invalidPass))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_login))
            .check(matches(isDisplayed()))
            .perform(click())

    }

    @Test
    fun wrongPasswordAndEmailLogin() {
        onView(withId(R.id.btn_submit_login))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_login)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(wrongEmail))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_login))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.password_edt_login)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(wrongPass))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_login))
            .check(matches(isDisplayed()))
            .perform(click())

    }

    @Test
    fun successPasswordAndEmailLogin() {
        onView(withId(R.id.btn_submit_login))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_login)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(validEmail))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_login))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.password_edt_login)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(validPass))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_login))
            .check(matches(isDisplayed()))
            .perform(click())

    }

}