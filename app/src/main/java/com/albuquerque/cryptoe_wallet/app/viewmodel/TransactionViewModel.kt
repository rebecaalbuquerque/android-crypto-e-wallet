package com.albuquerque.cryptoe_wallet.app.viewmodel

import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.albuquerque.cryptoe_wallet.app.extensions.toBigDecimal
import com.albuquerque.cryptoe_wallet.app.model.ui.CryptocurrencyUI
import com.albuquerque.cryptoe_wallet.app.usecase.CreateTransactionUseCase
import com.albuquerque.cryptoe_wallet.app.usecase.GetCurrencyByName
import com.albuquerque.cryptoe_wallet.app.usecase.GetLoggedUserUseCase
import com.albuquerque.cryptoe_wallet.app.utils.StatusTransaction
import com.albuquerque.cryptoe_wallet.app.utils.StatusTransaction.*
import com.albuquerque.cryptoe_wallet.app.utils.TypeTransaction
import com.albuquerque.cryptoe_wallet.core.mediator.SingleMediatorLiveData
import com.albuquerque.cryptoe_wallet.core.utils.WalletCalculator
import com.albuquerque.cryptoe_wallet.core.viewmodel.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class TransactionViewModel(
    getLoggedUserUseCase: GetLoggedUserUseCase,
    private val getCurrencyByName: GetCurrencyByName,
    private val createTransactionUseCase: CreateTransactionUseCase
) : BaseViewModel() {

    var onHideKeyboard = MutableLiveData<Any>()
    var onFinishedTransaction = MutableLiveData<Any>()

    var amount = ObservableField<String>("")

    var user = getLoggedUserUseCase.invoke()
    var cryptocurrency = SingleMediatorLiveData<CryptocurrencyUI?>()
    var typeTransaction: TypeTransaction? = null

    private var totalValueStatus: StatusTransaction = AVAILABLE_TRANSACTION
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
                            val currentTotalValue = WalletCalculator.calculateTransactionTotalValue(cryptocurrency.value, currentAmount.toBigDecimal(), user.value?.balance, typeTransaction)

                            totalValueStatus = currentTotalValue.second
                            totalValue.value = currentTotalValue.first
                            newBalance.value = WalletCalculator.calculateTransactionNewBalance(cryptocurrency.value, currentAmount.toBigDecimal(), user.value?.balance, typeTransaction)
                        }
                    }

                }
            }
        })
    }

    fun finalizeTransaction() {
        onHideKeyboard.value = Any()
        onStartLoading.value = Any()

        when(totalValueStatus) {

            AVAILABLE_TRANSACTION -> {
                viewModelScope.launch {

                    if(typeTransaction == null || user.value == null || cryptocurrency.value == null || amount.get().isNullOrEmpty() || newBalance.value.isNullOrEmpty())
                        onError.value = "Impossível finalizar a operação."
                    else
                        createTransactionUseCase.invoke(typeTransaction!!, user.value!!.apply { balance = newBalance.value!!.toBigDecimal() }, cryptocurrency.value!!, amount.get()!!.toBigDecimal())

                    delay(1500)
                    onFinishLoading.postValue(Any())
                    onFinishedTransaction.postValue(Any())
                }
            }

            UNAVAILABLE_TRANSACTION -> onError.value = "Impossível finalizar a operação."

            INSUFFICIENT_FUNDS , INSUFFICIENT_CRYPTOCURRENCIES -> onError.value = totalValue.value

        }

    }

}