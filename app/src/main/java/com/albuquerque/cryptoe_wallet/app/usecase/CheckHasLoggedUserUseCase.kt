package com.albuquerque.cryptoe_wallet.app.usecase

import androidx.lifecycle.LiveData
import com.albuquerque.cryptoe_wallet.app.repository.Repository

class CheckHasLoggedUserUseCase(
    private val repository: Repository
) {

    fun invoke(): LiveData<Boolean> = repository.hasLoggedUser()

}