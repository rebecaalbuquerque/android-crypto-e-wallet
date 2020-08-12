package com.albuquerque.cryptoe_wallet.app.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.albuquerque.cryptoe_wallet.app.model.ui.UserUI
import com.albuquerque.cryptoe_wallet.app.usecase.SignInUseCase
import com.albuquerque.cryptoe_wallet.core.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val signInUseCase: SignInUseCase
) : BaseViewModel() {

    var email = ObservableField<String>()
    var password = ObservableField<String>()

    val onLoginSucessfull = MutableLiveData<UserUI>()
    val onRegisterClicked = MutableLiveData<Any>()

    fun login() {
        onStartLoading.value = Any()

        viewModelScope.launch(Dispatchers.IO) {

            if (email.get() != null && password.get() != null) {

                try {
                    signInUseCase.invoke(email.get().toString(), password.get().toString())?.let {
                        onLoginSucessfull.postValue(it)
                    } ?: kotlin.run {
                        onError.postValue("Verifique as credenciais informadas.")
                    }

                } catch (e: Exception) {
                    onError.postValue(e.message)
                }

            } else {
                onError.postValue("Preencha todos os campos.")
            }
        }
    }

    fun handleRegisterClick() {
        onRegisterClicked.value = Any()
    }

}