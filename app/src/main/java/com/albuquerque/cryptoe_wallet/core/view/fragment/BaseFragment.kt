package com.albuquerque.cryptoe_wallet.core.view.fragment

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {

    override fun onPause() {
        super.onPause()
        (activity as AppCompatActivity?)?.supportActionBar?.title = ""
    }

}