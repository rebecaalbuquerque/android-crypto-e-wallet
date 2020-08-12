package com.albuquerque.cryptoe_wallet.app.usecase

import com.albuquerque.cryptoe_wallet.app.repository.Repository

class ClearSessionUseCase(
    private val repository: Repository
) {
    suspend fun invoke() {
        repository.clearSession()
    }
}