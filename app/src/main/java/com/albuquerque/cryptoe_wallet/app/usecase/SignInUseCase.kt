package com.albuquerque.cryptoe_wallet.app.usecase

import com.albuquerque.cryptoe_wallet.app.model.toUI
import com.albuquerque.cryptoe_wallet.app.model.ui.UserUI
import com.albuquerque.cryptoe_wallet.app.repository.Repository

class SignInUseCase(
    private val repository: Repository
) {

    suspend fun invoke(email: String, password: String): UserUI? {
        return repository.signIn(email, password)?.toUI()

    }

}