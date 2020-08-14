package com.albuquerque.cryptoe_wallet.app.usecase

import com.albuquerque.cryptoe_wallet.app.model.toEntity
import com.albuquerque.cryptoe_wallet.app.model.ui.CryptocurrencyUI
import com.albuquerque.cryptoe_wallet.app.model.ui.UserUI
import com.albuquerque.cryptoe_wallet.app.repository.Repository
import com.albuquerque.cryptoe_wallet.app.utils.TypeTransaction
import java.math.BigDecimal

class CreateTransactionUseCase(
    private val repository: Repository
) {

    suspend fun invoke(typeTransaction: TypeTransaction, user: UserUI, sourceCurrency: CryptocurrencyUI, targetCurrency: CryptocurrencyUI, amountRequested: BigDecimal) {
        repository.createTransaction(typeTransaction, user.toEntity(), sourceCurrency.toEntity(), targetCurrency.toEntity(), amountRequested)
    }

}