package com.albuquerque.cryptoe_wallet.app.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.albuquerque.cryptoe_wallet.R
import com.albuquerque.cryptoe_wallet.app.viewmodel.LoginViewModel
import com.albuquerque.cryptoe_wallet.core.extensions.showSnackbar
import com.albuquerque.cryptoe_wallet.core.view.activity.BaseActivity
import com.albuquerque.cryptoe_wallet.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        setupDataBinding()
        subscribeUI()
    }

    private fun setupDataBinding() {
        with(binding) {
            lifecycleOwner = this@LoginActivity
            viewModel = this@LoginActivity.loginViewModel
            executePendingBindings()
        }
    }

    private fun subscribeUI() {
        with(loginViewModel) {

            onLoginSucessfull.observe(this@LoginActivity) {
                Toast.makeText(this@LoginActivity, "Login", Toast.LENGTH_LONG).show()
            }

            onRegisterClicked.observe(this@LoginActivity) {
                startActivity(
                    Intent(this@LoginActivity, RegisterActivity::class.java)
                )
            }

            onError.observe(this@LoginActivity) {
                container.showSnackbar(it)
            }

        }
    }

}