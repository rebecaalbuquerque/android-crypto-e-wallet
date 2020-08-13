package com.albuquerque.cryptoe_wallet.app.usecase

import com.albuquerque.cryptoe_wallet.app.repository.Repository

class CreateTransactionUseCase(
    private val repository: Repository
) {

    fun invoke() {
        // TODO: atualizar usuário com suas moedas (UserCurrency)
        // TODO: inserir uma transacao (TransactionEntity e UserTransaction)
    }

}