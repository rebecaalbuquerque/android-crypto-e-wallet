package com.albuquerque.cryptoe_wallet.app.usecase

import com.albuquerque.cryptoe_wallet.app.model.toEntity
import com.albuquerque.cryptoe_wallet.app.model.toUI
import com.albuquerque.cryptoe_wallet.app.model.ui.CryptocurrencyUI
import com.albuquerque.cryptoe_wallet.app.repository.Repository
import com.albuquerque.cryptoe_wallet.app.utils.TypeCryptocurrency

class GetCurrenciesUseCase(
    private val repository: Repository
) {

    suspend fun invoke(typeCryptocurrency: TypeCryptocurrency): Result<CryptocurrencyUI>{

        return repository.fetchCriptoCurrency(typeCryptocurrency).map {
            it.toEntity().toUI()
        }

    }

}