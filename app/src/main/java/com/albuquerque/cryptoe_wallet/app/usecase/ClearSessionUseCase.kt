package com.albuquerque.cryptoe_wallet.app.usecase

import com.albuquerque.cryptoe_wallet.app.repository.Repository
import com.albuquerque.cryptoe_wallet.app.utils.Session

class ClearSessionUseCase(
    private val repository: Repository
) {
    suspend fun invoke() {
        repository.clearSession()
        Session.userLogged = ""
    }
}