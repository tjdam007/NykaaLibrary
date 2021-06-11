package com.mjandroiddev.nykaalibrary.ui


import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mjandroiddev.nykaalibrary.R
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/*
* Ui test related to HomeFragment
*
* Created by Manoj Singh Rawal (mjAndroidDev@gmail.com) on 2021-06-11.
* */
@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @Before
    fun launchActivity() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun homeFragmentTest() {
        val textView = onView(
            allOf(
                withId(R.id.tvSessionTimer), withText("Click below to start"),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        textView.check(matches(isDisplayed()))

        val button = onView(
            allOf(
                withId(R.id.buttonSession), withText("ENTER TO LIBRARY"),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.sessionHistory), withContentDescription("Session History"),
                withParent(withParent(withId(R.id.action_bar))),
                isDisplayed()
            )
        )
        textView2.check(matches(isDisplayed()))
    }
}
