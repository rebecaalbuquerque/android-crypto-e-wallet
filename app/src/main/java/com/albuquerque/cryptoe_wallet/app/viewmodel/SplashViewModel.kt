package com.albuquerque.cryptoe_wallet.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.albuquerque.cryptoe_wallet.app.usecase.CheckHasLoggedUserUseCase
import com.albuquerque.cryptoe_wallet.app.usecase.GetCurrenciesUseCase
import com.albuquerque.cryptoe_wallet.app.utils.TypeCryptocurrency
import com.albuquerque.cryptoe_wallet.core.mediator.SingleMediatorLiveData
import com.albuquerque.cryptoe_wallet.core.remote.BritaApiHelper
import com.albuquerque.cryptoe_wallet.core.viewmodel.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val checkHasLoggedUserUseCase: CheckHasLoggedUserUseCase,
    private val getCurrenciesUseCase: GetCurrenciesUseCase
) : BaseViewModel() {

    val hasLoggedUser = SingleMediatorLiveData<Boolean>()

    fun fetchCurrencies() {
        onStartLoading.value = Any()

        viewModelScope.launch {

            try {
                delay(1000)
                getCurrenciesUseCase.invoke(TypeCryptocurrency.BITCOIN)
                    .onSuccess {

                        getCurrenciesUseCase.invoke(TypeCryptocurrency.BRITA)
                            .onSuccess {
                                BritaApiHelper.isRequestSucessful = true
                                hasLoggedUser.emit(checkHasLoggedUserUseCase.invoke())
                            }
                            .onFailure {
                                onError.value = it.message
                            }

                    }
                    .onFailure {
                        onError.value = it.message
                    }

            } catch (e: Exception) {
                Log.d("okhttp", e.message ?: "")
                onError.value = "Não foi possível recuperar as informações das criptomoedas. Tente novamente mais tarde."
            }


        }
    }

}