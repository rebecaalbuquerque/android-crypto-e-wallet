package com.albuquerque.cryptoe_wallet.app.view.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.albuquerque.cryptoe_wallet.R
import com.albuquerque.cryptoe_wallet.app.viewmodel.RegisterViewModel
import com.albuquerque.cryptoe_wallet.core.extensions.showAlert
import com.albuquerque.cryptoe_wallet.core.view.activity.BaseActivity
import com.albuquerque.cryptoe_wallet.databinding.ActivityRegisterBinding
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        setupToolbar()
        setupDataBinding()
        subscribeUI()
    }

    private fun setupDataBinding() {
        with(binding) {
            lifecycleOwner = this@RegisterActivity
            viewModel = this@RegisterActivity.registerViewModel
            executePendingBindings()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }
    }

    private fun subscribeUI() {
        with(registerViewModel) {

            onRegisterSucessfull.observe(this@RegisterActivity) {
                showAlert(
                    { onBackPressed() },
                    {},
                    it
                )
            }

            onError.observe(this@RegisterActivity) {

            }
        }
    }

}