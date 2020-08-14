package com.albuquerque.cryptoe_wallet.app.usecase

import com.albuquerque.cryptoe_wallet.app.model.toUI
import com.albuquerque.cryptoe_wallet.app.repository.Repository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetTransactionsUseCase(
    private val repository: Repository
) {

    suspend fun invoke() = flow {
        emitAll(
            repository.getTransactions().map { list ->
                list.flatMap { it.transactions.map { transaction -> transaction.toUI() } }
            }
        )
    }

}