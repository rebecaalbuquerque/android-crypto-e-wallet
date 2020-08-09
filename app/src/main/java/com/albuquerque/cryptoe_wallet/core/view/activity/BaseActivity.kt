package com.albuquerque.cryptoe_wallet.core.view.activity

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {

    /**
     * Sample function of what it could be done with a custom BaseActivity in all project, where
     * [BaseActivity] could be inherited in all the activities of the project.
     * */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){

            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}