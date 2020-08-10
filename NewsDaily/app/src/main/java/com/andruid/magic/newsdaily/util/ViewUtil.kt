package com.andruid.magic.newsdaily.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.toast(@StringRes stringRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    toast(getString(stringRes), duration)
}

fun Fragment.toast(@StringRes stringRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    requireContext().toast(stringRes, duration)
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}