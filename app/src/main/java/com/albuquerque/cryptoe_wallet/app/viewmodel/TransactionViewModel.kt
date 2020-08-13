package com.albuquerque.cryptoe_wallet.app.viewmodel

import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.albuquerque.cryptoe_wallet.app.model.ui.CryptocurrencyUI
import com.albuquerque.cryptoe_wallet.app.usecase.GetCurrencyByName
import com.albuquerque.cryptoe_wallet.app.usecase.GetLoggedUserUseCase
import com.albuquerque.cryptoe_wallet.app.utils.TypeTransaction
import com.albuquerque.cryptoe_wallet.core.mediator.SingleMediatorLiveData
import com.albuquerque.cryptoe_wallet.core.utils.WalletCalculator
import com.albuquerque.cryptoe_wallet.core.viewmodel.BaseViewModel
import kotlinx.coroutines.launch


class TransactionViewModel(
    getLoggedUserUseCase: GetLoggedUserUseCase,
    private val getCurrencyByName: GetCurrencyByName
) : BaseViewModel() {

    var user = getLoggedUserUseCase.invoke()

    var cryptocurrency = SingleMediatorLiveData<CryptocurrencyUI>()
    var typeTransaction: TypeTransaction? = null

    var amount = ObservableField<String>("")

    var totalValue = MutableLiveData<String>("")
    var newBalance = MutableLiveData<String>("")

    var nameCurrency: String? = null
        set(value) {
            if(value != null) {
                field = value
                viewModelScope.launch {
                    cryptocurrency.emit(getCurrencyByName.invoke(value))
                }
            }
        }

    init {
        amount.addOnPropertyChangedCallback(object : OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                if (sender === amount && amount.get() != null) {

                    amount.get()?.let { currentAmount ->

                        if(currentAmount.isEmpty()) {
                            totalValue.value = ""
                            newBalance.value = ""
                        } else {
                            newBalance.value = WalletCalculator.calculateTransactionNewBalance(cryptocurrency.value, currentAmount.toBigDecimal(), user.value?.balance, typeTransaction)
                            totalValue.value = WalletCalculator.calculateTransactionTotalValue(cryptocurrency.value, currentAmount.toBigDecimal(), user.value?.balance, typeTransaction)
                        }
                    }

                }
            }
        })
    }

}