package com.example.memoryfinder.views


import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.example.memoryfinder.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runner.manipulation.Ordering


@RunWith(AndroidJUnit4::class)
class MemoryViewerTest {

    private lateinit var stringToBetyped: String
    private lateinit var invalidString: String

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun initValidString() {
        // Specify a valid string.
        stringToBetyped = "Espresso"
        invalidString = "aaasdddsdsd"
    }


    @Test
    fun testSearchinput() {
        onView(withId(R.id.searchAction)).perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform((typeText(stringToBetyped)))
            .check(matches(withText(stringToBetyped)))

    }

    @Test
    fun testDialogShow(){
        onView(withId(R.id.memoryView)).check(matches(isDisplayed()))
        onView(withId(R.id.memoryView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
        onView(withId(R.id.img_dialog)).check(matches(isDisplayed()))
    }

    @Test
    fun searchInvalidString(){
        onView(withId(R.id.searchAction)).perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform((typeText(invalidString)))
        onView(withId(R.id.noResults)).check(matches(isDisplayed()))
    }

    @Test
    fun testNoInternetConnection(){


    }

}
