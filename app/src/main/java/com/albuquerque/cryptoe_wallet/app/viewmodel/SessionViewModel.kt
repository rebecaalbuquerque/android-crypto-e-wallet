package com.albuquerque.cryptoe_wallet.app.viewmodel

import androidx.lifecycle.viewModelScope
import com.albuquerque.cryptoe_wallet.app.usecase.ClearSessionUseCase
import com.albuquerque.cryptoe_wallet.core.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SessionViewModel(
    private val clearSessionUseCase: ClearSessionUseCase
): BaseViewModel() {

    fun clearSession() {
        viewModelScope.launch(Dispatchers.IO) {
            clearSessionUseCase.invoke()
        }
    }

}