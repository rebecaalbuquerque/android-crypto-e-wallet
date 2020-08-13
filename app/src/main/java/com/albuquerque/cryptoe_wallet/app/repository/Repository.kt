package com.albuquerque.cryptoe_wallet.app.repository

import androidx.lifecycle.LiveData
import com.albuquerque.cryptoe_wallet.app.model.dto.CryptocurrencyDTO
import com.albuquerque.cryptoe_wallet.app.model.entity.CryptocurrencyEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserWithCurrencies
import com.albuquerque.cryptoe_wallet.app.model.toEntity
import com.albuquerque.cryptoe_wallet.app.utils.TypeCriptocurrency
import com.albuquerque.cryptoe_wallet.app.utils.TypeCriptocurrency.BITCOIN
import com.albuquerque.cryptoe_wallet.app.utils.TypeCriptocurrency.BRITA
import com.albuquerque.cryptoe_wallet.app.utils.TypeTransaction
import com.albuquerque.cryptoe_wallet.app.utils.TypeTransaction.*
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal


/**
 * @see RemoteRepository
 * @see LocalRepository
 * */

class Repository(
    private val remote: RemoteRepository,
    private val local: LocalRepository
) {

    fun hasLoggedUser(): LiveData<Boolean> = local.hasLoggedUser()

    fun getLoggedUser(): LiveData<UserEntity?> = local.getLoggedUser()

    suspend fun signUp(user: UserEntity): UserEntity {
        return local.signUp(user)
    }

    suspend fun signIn(email: String, password: String): UserEntity? {
        return local.signIn(email, password)
    }

    suspend fun fetchCriptoCurrency(typeCriptocurrency: TypeCriptocurrency): Result<CryptocurrencyDTO> {
        return when (typeCriptocurrency) {
            BITCOIN -> remote.fetchBicoinInfo()
                .onSuccess { bitcoin ->
                    local.saveCurrency(bitcoin.toEntity().apply { restoreFromDB() })
                }

            BRITA -> remote.fetchBritaInfo()
                .onSuccess { brita ->
                    local.saveCurrency(brita.toEntity().apply { restoreFromDB() })
                }
        }
    }

    fun getCriptoCurrency(): Flow<List<UserWithCurrencies>> = local.getCurrencies()

    fun getCriptoCurrencyByName(name: String): LiveData<CryptocurrencyEntity?> = local.getCriptoCurrencyByNameAsLiveData(name)

    suspend fun clearSession() {
        local.clearSession()
    }

    suspend fun createTransaction(typeTransaction: TypeTransaction, user: UserEntity, cryptocurrency: CryptocurrencyEntity, amountRequested: BigDecimal) {
        //  atualiza usuário com sua moeda (UserCurrency) e insere uma transacao (TransactionEntity e UserTransaction)
        when(typeTransaction) {
            SALE -> cryptocurrency.apply { amount += amountRequested }
            PURCHASE -> cryptocurrency.apply { amount -= amountRequested }
            EXCHANGE -> {}
        }

        local.saveCurrency(cryptocurrency)
        local.saveUser(user)
        local.saveUserCurrency(user.email, cryptocurrency.name, cryptocurrency.amount)
        local.saveTransaction(user.email, cryptocurrency.name, cryptocurrency.name, typeTransaction.id)
    }

    private suspend fun CryptocurrencyEntity.restoreFromDB() {
        local.getCriptoCurrencyByName(this.name)?.let { currency -> this.amount = currency.amount }
    }

}

