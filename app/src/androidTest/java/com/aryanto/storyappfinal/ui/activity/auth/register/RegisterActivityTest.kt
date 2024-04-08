package com.aryanto.storyappfinal.ui.activity.auth.register

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.aryanto.storyappfinal.R
import com.aryanto.storyappfinal.ui.activity.auth.login.LoginActivity
import com.aryanto.storyappfinal.utils.EspressoIdlingResource
import org.hamcrest.CoreMatchers.endsWith
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterActivityTest {

    /*
    * always change valid email and password
    * when success in test
    * when the valid email, password success registered
    * */

    private val validName = "wili qweqwe 01"
    private val validEmail = "wiliqweqwe01@gmail.com"
    private val validPass = "qweasdzxc"

    private val wrongName = "wili"
    private val wrongEmail = "wili@gmail.com"
    private val wrongPass = "password"

    private val invalidEmail = "wiliqweqwe01@l.com"
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
    fun emptyNameEmailPassRegister() {
        onView(withId(R.id.tv_register_here))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.name_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(emptyInput))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.name_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), clearText())
        Espresso.closeSoftKeyboard()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.name_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(wrongName))
        Espresso.closeSoftKeyboard()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(emptyInput))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), clearText())
        Espresso.closeSoftKeyboard()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(wrongEmail))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.password_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(emptyInput))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

    }

    @Test
    fun invalidNameEmailPassRegister() {
        onView(withId(R.id.tv_register_here))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.name_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(emptyInput))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.name_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), clearText())
        Espresso.closeSoftKeyboard()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.name_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(wrongName))
        Espresso.closeSoftKeyboard()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(emptyInput))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), clearText())
        Espresso.closeSoftKeyboard()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(invalidEmail))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), clearText())
        Espresso.closeSoftKeyboard()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(wrongEmail))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.password_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(emptyInput))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.password_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), clearText())
        Espresso.closeSoftKeyboard()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.password_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(invalidPass))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

    }

    @Test
    fun wrongNameEmailPassRegister() {
        onView(withId(R.id.tv_register_here))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.name_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(emptyInput))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.name_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), clearText())
        Espresso.closeSoftKeyboard()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.name_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(wrongName))
        Espresso.closeSoftKeyboard()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(emptyInput))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), clearText())
        Espresso.closeSoftKeyboard()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(wrongEmail))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.password_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(emptyInput))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.password_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), clearText())
        Espresso.closeSoftKeyboard()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.password_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(invalidPass))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.password_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), clearText())
        Espresso.closeSoftKeyboard()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.password_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(wrongPass))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

    }

    @Test
    fun successNameEmailPassRegister() {
        onView(withId(R.id.tv_register_here))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.name_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(emptyInput))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.name_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), clearText())
        Espresso.closeSoftKeyboard()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.name_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(validName))
        Espresso.closeSoftKeyboard()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(emptyInput))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), clearText())
        Espresso.closeSoftKeyboard()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.email_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(validEmail))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.password_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(emptyInput))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.password_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), clearText())
        Espresso.closeSoftKeyboard()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.password_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(invalidPass))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.password_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), clearText())
        Espresso.closeSoftKeyboard()

        onView(
            allOf(
                isDescendantOfA(withId(R.id.password_edt_register)),
                withClassName(endsWith("EditText"))
            )
        )
            .check(matches(isDisplayed()))
            .perform(click(), typeText(validPass))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btn_submit_register))
            .check(matches(isDisplayed()))
            .perform(click())

    }

}