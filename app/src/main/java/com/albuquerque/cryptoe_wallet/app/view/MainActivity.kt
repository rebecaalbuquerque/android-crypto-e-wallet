package com.albuquerque.cryptoe_wallet.app.view

import android.os.Bundle
import com.albuquerque.cryptoe_wallet.R
import com.albuquerque.cryptoe_wallet.core.view.activity.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}