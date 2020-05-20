package com.lavreniuk.campassistant

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.lavreniuk.campassistant.onboarding.OnboardingActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class OnboardingActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityTestRule(OnboardingActivity::class.java, false, false)

    @Rule
    @JvmField
    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @Test
    fun checkOnBoardingActivityTexts() {
        ActivityScenario.launch(OnboardingActivity::class.java)

        onView(withId(R.id.onboarding_activity_next_button)).check(matches(withText("Next")))
        onView(withId(R.id.onboarding_activity_next_button)).perform(click())

        onView(withId(R.id.onboarding_activity_next_button)).check(matches(withText("Next")))
        onView(withId(R.id.onboarding_activity_next_button)).perform(click())

        onView(withId(R.id.onboarding_activity_next_button)).check(matches(withText("Next")))
        onView(withId(R.id.onboarding_activity_next_button)).perform(click())

        onView(withId(R.id.onboarding_activity_next_button)).check(matches(withText("Finish")))
    }
}
