package com.albuquerque.cryptoe_wallet.app.repository

import androidx.lifecycle.LiveData
import com.albuquerque.cryptoe_wallet.app.model.dto.CryptocurrencyDTO
import com.albuquerque.cryptoe_wallet.app.model.entity.CryptocurrencyEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserWithCurrencies
import com.albuquerque.cryptoe_wallet.app.model.toEntity
import com.albuquerque.cryptoe_wallet.app.utils.TypeCriptocurrency
import com.albuquerque.cryptoe_wallet.app.utils.TypeCriptocurrency.*
import kotlinx.coroutines.flow.Flow


/**
 * @see RemoteRepositoryImpl
 * @see LocalRepositoryImpl
 * */

class RepositoryImpl(
    private val remote: RemoteRepository,
    private val local: LocalRepository
) : Repository {

    override fun hasLoggedUser(): LiveData<Boolean> = local.hasLoggedUser()

    override fun getLoggedUser(): LiveData<UserEntity?> = local.getLoggedUser()

    override suspend fun signUp(user: UserEntity): UserEntity {
        return local.signUp(user)
    }

    override suspend fun signIn(email: String, password: String): UserEntity? {
        return local.signIn(email, password)
    }

    override suspend fun fetchCriptoCurrency(typeCriptocurrency: TypeCriptocurrency): Result<CryptocurrencyDTO> {
        return when (typeCriptocurrency) {
            BITCOIN -> remote.fetchBicoinInfo()
                .onSuccess { bitcoin ->
                    local.saveCurrency(bitcoin.toEntity())
                }

            BRITA -> remote.fetchBritaInfo()
                .onSuccess { brita ->
                    local.saveCurrency(brita.toEntity())
                }
        }
    }

    override fun getCriptoCurrency(): Flow<List<UserWithCurrencies>> = local.getCurrencies()

    override fun getCriptoCurrencyByName(name: String): LiveData<CryptocurrencyEntity> = local.getCriptoCurrencyByName(name)

    override suspend fun clearSession() {
        local.clearSession()
    }

}