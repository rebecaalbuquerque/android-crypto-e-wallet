package com.albuquerque.cryptoe_wallet.app.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.albuquerque.cryptoe_wallet.R
import com.albuquerque.cryptoe_wallet.app.viewmodel.SplashViewModel
import com.albuquerque.cryptoe_wallet.core.extensions.setGone
import com.albuquerque.cryptoe_wallet.core.extensions.setVisible
import com.albuquerque.cryptoe_wallet.core.view.activity.BaseActivity
import com.albuquerque.cryptoe_wallet.databinding.ActivitySplashBinding
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val activityScope = CoroutineScope(Dispatchers.Main)
    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        setupDataBinding()
        subscribeUI()
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }

    private fun subscribeUI() {

        with(splashViewModel) {

            hasLoggedUser.observe(this@SplashActivity) { hasLoggedUser ->
                hasLoggedUser
                activityScope.launch {
                    delay(2500)

                    val intent: Intent = if(hasLoggedUser) {
                        Intent(this@SplashActivity, MainActivity::class.java)
                    } else {
                        Intent(this@SplashActivity, LoginActivity::class.java)
                    }

                    startActivity(intent)
                    finish()
                }

            }

            onError.observe(this@SplashActivity) {
                logo.setGone()
                messageError.setVisible()
                tryAgain.setVisible()
            }

        }

    }

    private fun setupDataBinding() {
        with(binding) {
            lifecycleOwner = this@SplashActivity
            viewModel = this@SplashActivity.splashViewModel
            executePendingBindings()
        }
    }

}