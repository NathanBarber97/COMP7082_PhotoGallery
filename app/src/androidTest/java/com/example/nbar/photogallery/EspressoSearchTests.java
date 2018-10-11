package com.example.nbar.photogallery;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.EspressoKey;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class EspressoSearchTests {
    @Rule
    public ActivityTestRule<ImageSelectionPage> mActivityRule =
            new ActivityTestRule<>(ImageSelectionPage.class);
    @Test
    public void validSearch() {
        /*// Type text and then press the button.
        onView(withId(R.id.editText))
                .perform(typeText("HELLO"), closeSoftKeyboard());
                */
        onView(withId(R.id.search_button)).perform(click());
        onView(withId(R.id.time_start_edittext))
                .perform(typeText("01/01/2018"), closeSoftKeyboard());
        onView(withId(R.id.time_stop_edittext))
                .perform(typeText("28/09/2018"), closeSoftKeyboard());
        onView(withId(R.id.search_go_button)).perform(click());

    }
}
