package com.albuquerque.cryptoe_wallet.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.albuquerque.cryptoe_wallet.app.model.ui.TransactionUI
import com.albuquerque.cryptoe_wallet.app.usecase.GetTransactionsUseCase
import com.albuquerque.cryptoe_wallet.core.mediator.SingleMediatorLiveData
import com.albuquerque.cryptoe_wallet.core.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class BankStatementViewModel(
    private val getTransactionsUseCase: GetTransactionsUseCase
): BaseViewModel() {

    private val _transactions = SingleMediatorLiveData<List<TransactionUI>>().apply {
        viewModelScope.launch {
            this@apply.emit(getTransactionsUseCase.invoke().asLiveData())
        }
    }

    val transactions = _transactions as LiveData<List<TransactionUI>>

}