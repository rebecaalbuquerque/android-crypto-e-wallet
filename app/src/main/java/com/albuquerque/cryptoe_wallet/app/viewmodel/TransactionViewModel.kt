package com.albuquerque.cryptoe_wallet.app.viewmodel

import androidx.lifecycle.viewModelScope
import com.albuquerque.cryptoe_wallet.app.model.ui.CryptocurrencyUI
import com.albuquerque.cryptoe_wallet.app.usecase.GetCurrencyByName
import com.albuquerque.cryptoe_wallet.app.usecase.GetLoggedUserUseCase
import com.albuquerque.cryptoe_wallet.app.utils.TypeTransaction
import com.albuquerque.cryptoe_wallet.core.mediator.SingleMediatorLiveData
import com.albuquerque.cryptoe_wallet.core.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class TransactionViewModel(
    getLoggedUserUseCase: GetLoggedUserUseCase,
    private val getCurrencyByName: GetCurrencyByName
) : BaseViewModel() {

    var user = getLoggedUserUseCase.invoke()

    var cryptocurrency = SingleMediatorLiveData<CryptocurrencyUI>()

    var typeTransaction: TypeTransaction? = null

    var nameCurrency: String? = null
        set(value) {
            if(value != null) {
                field = value
                viewModelScope.launch {
                    cryptocurrency.emit(getCurrencyByName.invoke(value))
                }
            }
        }
}