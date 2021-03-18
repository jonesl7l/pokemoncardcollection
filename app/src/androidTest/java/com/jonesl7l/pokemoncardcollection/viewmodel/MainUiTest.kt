package com.jonesl7l.pokemoncardcollection.viewmodel

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.jonesl7l.pokemoncardcollection.R
import com.jonesl7l.pokemoncardcollection.ui.MainActivity
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test

@LargeTest
class MainUiTest {

    @Rule
    @JvmField
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    //region Layout

    @Test
    fun testOnLoad() {
        //Added delays to allow network calls to complete
        onView(isRoot()).perform(waitFor(2500))
        onView(withId(R.id.main_recyclerview)).check(RecyclerViewItemCountAssertion(4))
    }

    @Test
    fun testOnSwipe() {
        //Added delays to allow network calls to complete
        onView(isRoot()).perform(waitFor())
        onSwipeToRefresh()
        onView(isRoot()).perform(waitFor())
        onView(withId(R.id.main_recyclerview)).check(RecyclerViewItemCountAssertion(4))
    }

    //endregion

    //region UI interactions

    private fun onSwipeToRefresh() {
        onView(withId(R.id.main_swipe_layout)).perform(swipeDown())
    }


    //endregion

    //region Utils

    private fun waitFor(delay: Long = 2500): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }

    inner class RecyclerViewItemCountAssertion(private val expectedCount: Int) : ViewAssertion {

        override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }
            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter
            assertThat(adapter?.itemCount, `is`(expectedCount))
        }
    }

    //endregion
}
