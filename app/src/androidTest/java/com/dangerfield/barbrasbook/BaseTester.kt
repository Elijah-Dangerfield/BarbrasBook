package com.dangerfield.barbrasbook


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.dangerfield.barbrasbook.view.latest.NewsAdapter
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BaseTester {

    @Rule
    @JvmField
    val rule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun test_can_useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.dangerfield.barbrasbook", appContext.packageName)
    }

    @Test
    fun test_user_can_click_article() {
        onView(withId(R.id.rv_articles)).perform(actionOnItemAtPosition<NewsAdapter.ViewHolder>(0,click()))
    }

    @Test
    fun test_barb_loves_me_back() {
        onView(withId(R.id.rv_articles)).perform(actionOnItemAtPosition<NewsAdapter.ViewHolder>(0,click()))
        onView(withId(R.id.btn_heart)).perform(click())
        onView(withId(R.id.tv_heart)).check(matches(withText("Barbra loves you back")))
    }
}