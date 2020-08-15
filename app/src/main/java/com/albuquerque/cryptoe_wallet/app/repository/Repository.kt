package com.albuquerque.cryptoe_wallet.app.repository

import androidx.lifecycle.LiveData
import com.albuquerque.cryptoe_wallet.app.model.dto.CryptocurrencyDTO
import com.albuquerque.cryptoe_wallet.app.model.entity.CryptocurrencyEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserCryptocurrencyEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserWithTransactions
import com.albuquerque.cryptoe_wallet.app.model.toEntity
import com.albuquerque.cryptoe_wallet.app.utils.TypeCryptocurrency
import com.albuquerque.cryptoe_wallet.app.utils.TypeCryptocurrency.BITCOIN
import com.albuquerque.cryptoe_wallet.app.utils.TypeCryptocurrency.BRITA
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

    suspend fun fetchCriptoCurrency(typeCryptocurrency: TypeCryptocurrency): Result<CryptocurrencyDTO> {
        return when (typeCryptocurrency) {
            BITCOIN -> remote.fetchBicoinInfo()
                .onSuccess { bitcoin ->
                    local.saveCurrency(bitcoin.toEntity())
                }

            BRITA -> remote.fetchBritaInfo()
                .onSuccess { brita ->
                    if(brita.buyValue != null || brita.sellValue != null)
                        local.saveCurrency(brita.toEntity())
                }
        }
    }

    fun getCriptoCurrencies(): Flow<List<UserCryptocurrencyEntity>> = local.getCurrencies()

    fun getTransactions(): Flow<List<UserWithTransactions>> = local.getTransactions()

    fun getCriptoCurrencyByName(name: String): LiveData<UserCryptocurrencyEntity?> = local.getCriptoCurrencyByNameAsLiveData(name)

    suspend fun clearSession() {
        local.clearSession()
    }

    suspend fun createTransaction(
        typeTransaction: TypeTransaction,
        user: UserEntity,
        sourceCurrency: UserCryptocurrencyEntity,
        targetCurrency: UserCryptocurrencyEntity,
        value: BigDecimal,
        exchangeAmount: BigDecimal
    ) {
        //  atualiza usuário com sua moeda (UserCurrency) e insere uma transacao (TransactionEntity e UserTransaction)
        when(typeTransaction) {
            SALE -> {
                sourceCurrency.apply { amount -= value }
                local.saveUserCurrency(sourceCurrency)
                local.saveUser(user)
                local.saveTransaction(user.email, sourceCurrency.currency, sourceCurrency.currency, typeTransaction.id, value)
            }

            PURCHASE -> {
                sourceCurrency.apply { amount += value }
                local.saveUserCurrency(sourceCurrency)
                local.saveUser(user)
                local.saveTransaction(user.email, sourceCurrency.currency, sourceCurrency.currency, typeTransaction.id, value)
            }

            EXCHANGE -> {
                sourceCurrency.apply { amount -= exchangeAmount }
                targetCurrency.apply { amount += value }
                local.saveUserCurrency(sourceCurrency)
                local.saveUserCurrency(targetCurrency)
                local.saveTransaction(user.email, sourceCurrency.currency, targetCurrency.currency, typeTransaction.id, value)
            }
        }

    }

}

