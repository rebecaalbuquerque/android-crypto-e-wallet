package com.albuquerque.cryptoe_wallet.app.usecase

import com.albuquerque.cryptoe_wallet.app.model.toEntity
import com.albuquerque.cryptoe_wallet.app.model.toUI
import com.albuquerque.cryptoe_wallet.app.model.ui.CryptocurrencyUI
import com.albuquerque.cryptoe_wallet.app.repository.Repository
import com.albuquerque.cryptoe_wallet.app.utils.TypeCriptocurrency

class GetCurrenciesUseCase(
    private val repository: Repository
) {

    suspend fun invoke(typeCriptocurrency: TypeCriptocurrency): Result<CryptocurrencyUI>{

        return repository.fetchCriptoCurrency(typeCriptocurrency).map {
            it.toEntity().toUI()
        }

    }

}