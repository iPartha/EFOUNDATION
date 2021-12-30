package com.ipartha.efoundation.ui

import android.view.View
import android.widget.ProgressBar

fun View.enable() {
   isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

