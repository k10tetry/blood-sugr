package com.k10tetry.bloodsugr.presentation.utils

import android.app.Activity
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Activity.hideKeyboard(view: View?) {
    view?.let {
        val inputMethod =
            it.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethod.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Activity.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun View.snackbar(
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT,
    actionText: String? = null,
    action: View.OnClickListener? = null
) {
    Snackbar.make(this, message, duration).apply {
        actionText?.let {
            setAction(actionText, action)
        }
    }.show()
}

fun Float.toPx(resources: Resources): Float = this * resources.displayMetrics.density