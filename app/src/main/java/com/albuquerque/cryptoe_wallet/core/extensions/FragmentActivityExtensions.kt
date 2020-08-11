package com.albuquerque.cryptoe_wallet.core.extensions

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar

fun FragmentActivity.showAlert(
    onPositiveAction: () -> Unit,
    onNegativeAction: () -> Unit,
    message: String,
    title: String? = null
) {
    AlertDialog.Builder(this).apply {
        title?.let { this.setTitle(it) }
        this.setMessage(message)

        this.setPositiveButton(android.R.string.yes) { dialog, _ ->
            dialog.dismiss()
            onPositiveAction.invoke()
        }

        this.setNegativeButton(android.R.string.no) { dialog, _ ->
            dialog.dismiss()
            onNegativeAction.invoke()
        }
    }.show()
}