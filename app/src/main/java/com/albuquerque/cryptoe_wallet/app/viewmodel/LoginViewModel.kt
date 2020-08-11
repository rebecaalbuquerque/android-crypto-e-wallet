package com.albuquerque.cryptoe_wallet.app.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.albuquerque.cryptoe_wallet.app.model.ui.UserUI
import com.albuquerque.cryptoe_wallet.app.usecase.SignInUseCase
import com.albuquerque.cryptoe_wallet.core.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val signInUseCase: SignInUseCase
): BaseViewModel() {

    var email = ObservableField<String>()
    var password = ObservableField<String>()

    val onLoginSucessfull = MutableLiveData<UserUI>()
    val onRegisterClicked = MutableLiveData<Any>()

    fun login() = viewModelScope.launch {
        onStartLoading.value = Any()

        if(email.get() != null && password.get() != null) {

            withContext(Dispatchers.IO) {
                try {
                    signInUseCase.invoke(email.get().toString(), password.get().toString())
                        .onSuccess { onLoginSucessfull.postValue(it) }
                        .onFailure { onError.postValue(it.message) }

                } catch (e: Exception) {
                    onError.value = e.message
                }
            }
        } else {
            onError.value = "Preencha todos os campos."
        }
    }

    fun handleRegisterClick() {
        onRegisterClicked.value = Any()
    }

}