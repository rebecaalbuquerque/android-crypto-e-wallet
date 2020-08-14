package com.albuquerque.cryptoe_wallet.app.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.albuquerque.cryptoe_wallet.app.usecase.CheckHasLoggedUserUseCase
import com.albuquerque.cryptoe_wallet.app.usecase.GetCurrenciesUseCase
import com.albuquerque.cryptoe_wallet.app.utils.TypeCryptocurrency
import com.albuquerque.cryptoe_wallet.core.mediator.SingleMediatorLiveData
import com.albuquerque.cryptoe_wallet.core.utils.ResultUtils
import com.albuquerque.cryptoe_wallet.core.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class SplashViewModel(
    private val checkHasLoggedUserUseCase: CheckHasLoggedUserUseCase,
    private val getCurrenciesUseCase: GetCurrenciesUseCase
) : BaseViewModel() {

    val hasLoggedUser = SingleMediatorLiveData<Boolean>()

    init {
        fetchCurrencies()
    }

    fun fetchCurrencies() {
        viewModelScope.launch {

            try {

                getCurrenciesUseCase.invoke(TypeCryptocurrency.BITCOIN)
                    .onSuccess {

                        getCurrenciesUseCase.invoke(TypeCryptocurrency.BRITA)
                            .onSuccess {
                                hasLoggedUser.emit(checkHasLoggedUserUseCase.invoke())
                            }
                            .onFailure { onError.value = it.message }

                    }
                    .onFailure { onError.value = it.message }

            } catch (e: Exception) {
                Log.d("okhttp", e.message ?: "")
                onError.value = ResultUtils.defaultThrowableCurrencies.message
            }


        }
    }

}