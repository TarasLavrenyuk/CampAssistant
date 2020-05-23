package com.lavreniuk.campassistant.utils

import android.view.View
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object TestHelpers {

    fun hasHintText(expectedText: String): Matcher<View> = hasTextInputLayoutText(
        expectedText
    ) { view: TextInputLayout -> view.hint.toString() }

    fun hasErrorText(expectedText: String): Matcher<View> = hasTextInputLayoutText(
        expectedText
    ) { view: TextInputLayout -> view.error.toString() }

    private fun hasTextInputLayoutText(
        expectedText: String,
        getActualText: (view: TextInputLayout) -> String?
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {

            override fun matchesSafely(view: View?): Boolean =
                expectedText == getActualText(view as TextInputLayout) ?: false

            override fun describeTo(description: Description?) {}
        }
    }
}
