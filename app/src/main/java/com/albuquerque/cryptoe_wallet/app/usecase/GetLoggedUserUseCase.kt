package com.albuquerque.cryptoe_wallet.app.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.albuquerque.cryptoe_wallet.app.model.toUI
import com.albuquerque.cryptoe_wallet.app.model.ui.UserUI
import com.albuquerque.cryptoe_wallet.app.repository.Repository

class GetLoggedUserUseCase(
    private val repository: Repository
) {

    fun invoke(): LiveData<UserUI?> {
        return repository.getLoggedUser().map {
            it?.toUI()
        }
    }

}