package com.lavreniuk.campassistant

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.lavreniuk.campassistant.activities.RegistrationActivity
import com.lavreniuk.campassistant.models.User
import com.lavreniuk.campassistant.utils.Helpers
import kotlinx.android.synthetic.main.activity_registration.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class RegistrationActivityTest {

    @get:Rule
    var activityScenarioRule = ActivityTestRule(RegistrationActivity::class.java, false, false)

    @Rule
    @JvmField
    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @Test
    fun checkRegistrationActivityInputs() {
        ActivityScenario.launch(RegistrationActivity::class.java)

        onView(withId(R.id.registration_activity_fname_input)).perform(typeText(""))
        onView(withId(R.id.registration_activity_start_label)).perform(click())
        onView(withId(R.id.registration_activity_fname_label)).check(matches(Helpers.hasErrorText("Field name cannot be empty")))

        onView(withId(R.id.registration_activity_fname_input)).perform(replaceText("   Taras         "))
        onView(withId(R.id.registration_activity_lname_input)).perform(typeText("Lavreniuk"))

        onView(withId(R.id.registration_activity_start_label)).perform(click())
        onView(withId(R.id.fragment_home_user_greetings)).check(matches(withText("Hi, Taras")))
    }

    @Test
    fun checkRegistrationActivityFirstAndLastNameInputs() {
        ActivityScenario.launch(RegistrationActivity::class.java)

        val user = User(firstName = "Taras", lastName = "Lavreniuk")

        onView(withId(R.id.registration_activity_fname_input)).perform(replaceText(user.firstName))
        onView(withId(R.id.registration_activity_lname_input)).perform(typeText(user.lastName))

        onView(withId(R.id.registration_activity_start_label)).perform(click())
        onView(withId(R.id.fragment_home_user_avatar)).perform(click())

        onView(withId(R.id.user_settings_user_name)).check(matches(withText(user.getFullName())))
    }
}
