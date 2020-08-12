package com.albuquerque.cryptoe_wallet.core.extensions

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.albuquerque.cryptoe_wallet.R
import com.google.android.material.snackbar.Snackbar

fun View.setVisible() {
    this.visibility = View.VISIBLE
}

fun View.setInisible() {
    this.visibility = View.INVISIBLE
}

fun View.setGone() {
    this.visibility = View.GONE
}

fun View.showSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).setBackgroundTint(ContextCompat.getColor(this.context, R.color.colorAccent)).show()
}