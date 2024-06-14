package com.k10tetry.bloodsugr.presentation.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.k10tetry.bloodsugr.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun check_radio_button_click() {
        onView(withId(R.id.radioMg)).perform(click())
        onView(withId(R.id.textViewUnits)).check(matches(withText("mg/dL")))
        onView(withId(R.id.textViewUnitLabel)).check(matches(withText("mg/dL")))
    }

    @Test
    fun check_input_conversion() {
        onView(withId(R.id.radioMmol)).perform(click()).check(matches(isChecked()))
        onView(withId(R.id.editTextMeasurement)).perform(typeText("10.0"), closeSoftKeyboard())
        onView(withId(R.id.radioMg)).perform(click()).check(matches(isChecked()))
        onView(withId(R.id.editTextMeasurement)).check(matches(withText("180.18")))
    }

    @Test
    fun check_on_click_save() {
        onView(withId(R.id.radioMmol)).perform(click())
        onView(withId(R.id.editTextMeasurement)).perform(typeText("10.0"), closeSoftKeyboard())
        onView(withId(R.id.buttonSave)).perform(click())
    }
}