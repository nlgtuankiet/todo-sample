package com.sample.todo.ui


import android.view.View
import android.view.ViewGroup
import com.sample.todo.splash.R
import com.sample.todo.splash.SplashActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SplashActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashActivity::class.java)

    @Test
    fun splashActivityTest() {
        val textView = onView(
            allOf(
                withId(R.id.no_task_text_view), withText("You have no tasks!"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.main_coordinator_layout),
                        1
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("You have no tasks!")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
