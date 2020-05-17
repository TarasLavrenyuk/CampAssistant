package com.lavreniuk.campassistant

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.lavreniuk.campassistant.onboarding.OnboardingActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class OnboardingTest {

    @get:Rule
    var activityScenarioRule = ActivityTestRule(OnboardingActivity::class.java, false, false)

    @Test
    fun launchOnboardingActivity() {
        ActivityScenario.launch(OnboardingActivity::class.java)
        onView(withId(R.id.onboarding_activity_next_button)).perform(click())
        onView(withId(R.id.onboarding_activity_next_button)).perform(click())
        onView(withId(R.id.onboarding_activity_next_button)).perform(click())
        onView(withId(R.id.onboarding_activity_next_button)).perform(click())

        onView(withId(R.id.registration_activity_fname_input)).perform(typeText("Taras"))
        onView(withId(R.id.registration_activity_start_label)).perform(click())

        onView(withId(R.id.fragment_home_user_greetings)).check(matches(withText("Hi, Taras")))
    }
}
