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
import com.albuquerque.cryptoe_wallet.app.utils.TypeCryptocurrency.*
import com.albuquerque.cryptoe_wallet.app.utils.TypeTransaction
import com.albuquerque.cryptoe_wallet.core.mediator.SingleMediatorLiveData
import com.albuquerque.cryptoe_wallet.app.utils.WalletCalculator
import com.albuquerque.cryptoe_wallet.core.viewmodel.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal


class TransactionViewModel(
    getLoggedUserUseCase: GetLoggedUserUseCase,
    private val getCurrencyByName: GetCurrencyByName,
    private val createTransactionUseCase: CreateTransactionUseCase
) : BaseViewModel() {

    var onHideKeyboard = MutableLiveData<Any>()
    var onFinishedTransaction = MutableLiveData<Any>()

    var amount = ObservableField<String>("")

    var user = getLoggedUserUseCase.invoke()
    var sourceCurrency = SingleMediatorLiveData<CryptocurrencyUI?>()
    var targetCurrency = SingleMediatorLiveData<CryptocurrencyUI?>()
    var typeTransaction: TypeTransaction? = null

    private var totalValueStatus: StatusTransaction = AVAILABLE_TRANSACTION
    var totalValue = MutableLiveData<String>("")
    var newBalance = MutableLiveData<String>("")

    var nameCurrency: String? = null
        set(value) {
            if(value != null) {
                field = value
                viewModelScope.launch {
                    sourceCurrency.emit(getCurrencyByName.invoke(value))

                        if(typeTransaction != TypeTransaction.EXCHANGE) {
                            targetCurrency.emit(getCurrencyByName.invoke(value))
                        } else {
                            if(value == BITCOIN.value)
                                targetCurrency.emit(getCurrencyByName.invoke(BRITA.value))
                            else
                                targetCurrency.emit(getCurrencyByName.invoke(BITCOIN.value))
                        }

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
                            val currentTotalValue = WalletCalculator.calculateTransactionTotalValue(sourceCurrency.value, targetCurrency.value, currentAmount.toBigDecimal(), user.value?.balance, typeTransaction)

                            totalValueStatus = currentTotalValue.second
                            totalValue.value = currentTotalValue.first
                            newBalance.value = WalletCalculator.calculateTransactionNewBalance(sourceCurrency.value, currentAmount.toBigDecimal(), user.value?.balance, typeTransaction)
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

                    if(typeTransaction == null || user.value == null || sourceCurrency.value == null || amount.get().isNullOrEmpty() || newBalance.value.isNullOrEmpty() || totalValue.value == null)
                        onError.value = "Impossível finalizar a operação."
                    else {
                        val exchangeAmount: BigDecimal
                        val currentAmount : BigDecimal

                        if(typeTransaction == TypeTransaction.EXCHANGE) {
                            currentAmount = totalValue.value!!.toBigDecimal()
                            exchangeAmount = amount.get()!!.toBigDecimal()
                        } else {
                            currentAmount = amount.get()!!.toBigDecimal()
                            exchangeAmount = BigDecimal.ZERO
                        }

                        createTransactionUseCase.invoke(
                            typeTransaction!!,
                            user.value!!.apply { balance = newBalance.value!!.toBigDecimal() },
                            sourceCurrency.value!!,
                            targetCurrency.value!!,
                            currentAmount,
                            exchangeAmount
                        )
                    }

                    delay(1500)
                    onFinishLoading.postValue(Any())
                    onFinishedTransaction.postValue(Any())
                }
            }

            UNAVAILABLE_TRANSACTION -> onError.value = "Impossível finalizar a operação."

            INSUFFICIENT_FUNDS -> onError.value = totalValue.value

        }

    }

}