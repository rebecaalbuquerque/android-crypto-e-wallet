package com.albuquerque.cryptoe_wallet.app.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.albuquerque.cryptoe_wallet.R
import com.albuquerque.cryptoe_wallet.app.viewmodel.SessionViewModel
import com.albuquerque.cryptoe_wallet.core.view.activity.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private lateinit var appBarConfiguration : AppBarConfiguration
    private val sessionViewModel: SessionViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment? ?: return

        appBarConfiguration = AppBarConfiguration(setOf(R.id.wallet_destination, R.id.history_destination))

        with(host.navController) {
            setupActionBar(this, appBarConfiguration)
            setupBottomNavMenu(this)
        }

    }

    private fun setupBottomNavMenu(navController: NavController) {
        findViewById<BottomNavigationView>(R.id.bottom_navigation_view)?.apply {
            this.setupWithNavController(navController)
        }
    }

    private fun setupActionBar(navController: NavController, appBarConfig : AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragment_container_view).navigateUp(appBarConfiguration)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_logout) {
            sessionViewModel.clearSession()
            startActivity(Intent(this, LoginActivity::class.java))
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

}