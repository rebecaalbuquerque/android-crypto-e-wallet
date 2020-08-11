package com.albuquerque.cryptoe_wallet.app.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.albuquerque.cryptoe_wallet.app.model.entity.UserEntity
import com.albuquerque.cryptoe_wallet.app.usecase.SignUpUseCase
import com.albuquerque.cryptoe_wallet.core.viewmodel.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class RegisterViewModel(
    private val signUpUseCase: SignUpUseCase
): BaseViewModel() {

    var fullName = ObservableField<String>()
    var email = ObservableField<String>()
    var password = ObservableField<String>()

    val onRegisterSucessfull = MutableLiveData<String>()

    fun register() {

        if(email.get() != null && password.get() != null && fullName.get() != null) {

            viewModelScope.launch {
                delay(1500)

                try {
                    signUpUseCase.invoke(
                        UserEntity(email.get().toString(), password.get().toString(), fullName.get().toString())
                    )

                    onRegisterSucessfull.value = "Registro finalizado! Você já está pronto para utilizar a Crypto E-wallet!"
                } catch (e: Exception) {
                    onError.value = "Erro ao criar novo usuário"
                }

            }
        }
    }

}