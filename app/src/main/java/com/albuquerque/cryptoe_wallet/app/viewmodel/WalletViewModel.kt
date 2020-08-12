package com.albuquerque.cryptoe_wallet.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.albuquerque.cryptoe_wallet.app.model.ui.CryptocurrencyUI
import com.albuquerque.cryptoe_wallet.app.usecase.GetLoggedUserUseCase
import com.albuquerque.cryptoe_wallet.app.usecase.GetUserCurrenciesUseCase
import com.albuquerque.cryptoe_wallet.core.mediator.SingleMediatorLiveData
import com.albuquerque.cryptoe_wallet.core.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class WalletViewModel(
    getLoggedUserUseCase: GetLoggedUserUseCase,
    private val getUserCurrenciesUseCase: GetUserCurrenciesUseCase
): BaseViewModel() {

    private val _currencies = SingleMediatorLiveData<List<CryptocurrencyUI>>().apply {
        viewModelScope.launch {
            this@apply.emit(getUserCurrenciesUseCase.executeFromDb().asLiveData())
        }
    }
    val currencies = _currencies as LiveData<List<CryptocurrencyUI>>

    var user = getLoggedUserUseCase.invoke()

    fun fetchCurrencies() {

        viewModelScope.launch() {
            try {
                getUserCurrenciesUseCase.executeFromApi()
                    .onSuccess {  }
                    .onFailure {  }
            } catch (e: Exception) {

            }
        }

    }

}