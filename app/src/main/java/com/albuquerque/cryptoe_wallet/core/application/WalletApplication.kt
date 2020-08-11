package com.albuquerque.cryptoe_wallet.core.application

import android.app.Application
import com.albuquerque.cryptoe_wallet.app.repository.*
import com.albuquerque.cryptoe_wallet.app.usecase.CheckHasLoggedUserUseCase
import com.albuquerque.cryptoe_wallet.app.usecase.GetCurrenciesUseCase
import com.albuquerque.cryptoe_wallet.app.usecase.SignInUseCase
import com.albuquerque.cryptoe_wallet.app.usecase.SignUpUseCase
import com.albuquerque.cryptoe_wallet.app.viewmodel.LoginViewModel
import com.albuquerque.cryptoe_wallet.app.viewmodel.RegisterViewModel
import com.albuquerque.cryptoe_wallet.app.viewmodel.SplashViewModel
import com.albuquerque.cryptoe_wallet.core.database.AppDatabase
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class WalletApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        setupStetho()
        setupRoom()
        setupKoin()

    }

    private fun setupStetho() {
        Stetho.initialize(
            Stetho.newInitializerBuilder(this).apply {
                enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this@WalletApplication))
            }.build()
        )
    }

    private fun setupRoom() {
        AppDatabase.getInstance(this)
    }

    private fun setupKoin() {

        startKoin {
            androidContext(this@WalletApplication)

            val databaseModule = module {
                single { AppDatabase.getInstance(get()) }
                single { get<AppDatabase>().userDao }
                single { get<AppDatabase>().sessionDao }
                single { get<AppDatabase>().cryptocurrencyDao }
            }

            val repositoryModule = module {
                factory<RemoteRepository> { RemoteRepositoryImpl() }
                factory<LocalRepository> { LocalRepositoryImpl(userDao = get(), sessionDao = get(), cryptocurrencyDao = get()) }
                factory<Repository> { RepositoryImpl(remote = get(), local = get()) }
            }

            val useCaseModule = module {
                factory { GetCurrenciesUseCase(repository = get()) }
                factory { CheckHasLoggedUserUseCase(repository = get()) }
                factory { SignInUseCase(repository = get()) }
                factory { SignUpUseCase(repository = get()) }
            }

            val viewModelModule = module {
                viewModel { SplashViewModel(checkHasLoggedUserUseCase = get(), getCurrenciesUseCase = get()) }
                viewModel { RegisterViewModel(signUpUseCase = get()) }
                viewModel { LoginViewModel(signInUseCase = get()) }
            }

            modules(listOf(databaseModule, repositoryModule, useCaseModule, viewModelModule))

        }

    }

}