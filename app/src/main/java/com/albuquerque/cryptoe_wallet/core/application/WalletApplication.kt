package com.albuquerque.cryptoe_wallet.core.application

import android.app.Application
import com.albuquerque.cryptoe_wallet.app.repository.*
import com.albuquerque.cryptoe_wallet.app.usecase.*
import com.albuquerque.cryptoe_wallet.app.viewmodel.*
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
                single { get<AppDatabase>().transactionDao }
            }

            val repositoryModule = module {
                factory { RemoteRepository() }
                factory { LocalRepository(userDao = get(), sessionDao = get(), cryptocurrencyDao = get(), transactionDao = get()) }
                factory { Repository(remote = get(), local = get()) }
            }

            val useCaseModule = module {
                factory { GetCurrenciesUseCase(repository = get()) }
                factory { CheckHasLoggedUserUseCase(repository = get()) }
                factory { SignInUseCase(repository = get()) }
                factory { SignUpUseCase(repository = get()) }
                factory { ClearSessionUseCase(repository = get()) }
                factory { GetLoggedUserUseCase(repository = get()) }
                factory { GetUserCurrenciesUseCase(repository = get()) }
                factory { GetCurrencyByName(repository = get()) }
                factory { CreateTransactionUseCase(repository = get()) }
                factory { GetTransactionsUseCase(repository = get()) }
            }

            val viewModelModule = module {
                viewModel { SplashViewModel(checkHasLoggedUserUseCase = get(), getCurrenciesUseCase = get()) }
                viewModel { RegisterViewModel(signUpUseCase = get()) }
                viewModel { LoginViewModel(signInUseCase = get()) }
                viewModel { SessionViewModel(clearSessionUseCase = get()) }
                viewModel { WalletViewModel(getLoggedUserUseCase = get(), getUserCurrenciesUseCase = get()) }
                viewModel { TransactionViewModel(getLoggedUserUseCase = get(), getCurrencyByName = get(), createTransactionUseCase = get()) }
                viewModel { BankStatementViewModel(getTransactionsUseCase = get()) }
            }

            modules(listOf(databaseModule, repositoryModule, useCaseModule, viewModelModule))

        }

    }

}