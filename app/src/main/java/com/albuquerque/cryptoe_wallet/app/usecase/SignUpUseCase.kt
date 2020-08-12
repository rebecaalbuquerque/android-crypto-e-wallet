package com.albuquerque.cryptoe_wallet.app.usecase

import com.albuquerque.cryptoe_wallet.app.model.entity.UserEntity
import com.albuquerque.cryptoe_wallet.app.model.toUI
import com.albuquerque.cryptoe_wallet.app.model.ui.UserUI
import com.albuquerque.cryptoe_wallet.app.repository.Repository

class SignUpUseCase(
    private val repository: Repository
) {

    suspend fun invoke(user: UserEntity): UserUI {
        return repository.signUp(user).toUI()
    }

}