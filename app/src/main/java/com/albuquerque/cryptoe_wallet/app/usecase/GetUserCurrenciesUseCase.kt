package com.albuquerque.cryptoe_wallet.app.usecase

import com.albuquerque.cryptoe_wallet.app.model.toUI
import com.albuquerque.cryptoe_wallet.app.repository.Repository
import com.albuquerque.cryptoe_wallet.app.utils.TypeCriptocurrency
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.Exception

class GetUserCurrenciesUseCase(
    private val repository: Repository
) {
    /**
     * Execute retrieve from database
     * */
    suspend fun executeFromDb() = flow {
        emitAll(
            repository.getCriptoCurrency().map { list ->
                list.flatMap { it.currencies.map { currency -> currency.toUI() } }
            }
        )
    }

    /**
     * Execute retrieve from API
     * */
    suspend fun executeFromApi(): Result<String> {

        return try {
            repository.fetchCriptoCurrency(TypeCriptocurrency.BITCOIN)
            repository.fetchCriptoCurrency(TypeCriptocurrency.BRITA)
            Result.success("")
        } catch (e: Exception) {
            Result.failure(e.cause ?: Throwable("Erro ao atualizar as criptomoedas."))
        }

    }
}