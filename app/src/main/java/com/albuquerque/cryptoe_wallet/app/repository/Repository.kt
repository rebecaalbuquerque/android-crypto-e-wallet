package com.albuquerque.cryptoe_wallet.app.repository

import androidx.lifecycle.LiveData
import com.albuquerque.cryptoe_wallet.app.model.dto.CryptocurrencyDTO
import com.albuquerque.cryptoe_wallet.app.model.entity.CryptocurrencyEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserWithCurrencies
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
                    local.saveCurrency(bitcoin.toEntity().apply { restoreFromDB() })
                }

            BRITA -> remote.fetchBritaInfo()
                .onSuccess { brita ->
                    if(brita.buyValue != null || brita.sellValue != null)
                        local.saveCurrency(brita.toEntity().apply { restoreFromDB() })
                }
        }
    }

    fun getCriptoCurrencies(): Flow<List<UserWithCurrencies>> = local.getCurrencies()

    fun getTransactions(): Flow<List<UserWithTransactions>> = local.getTransactions()

    fun getCriptoCurrencyByName(name: String): LiveData<CryptocurrencyEntity?> = local.getCriptoCurrencyByNameAsLiveData(name)

    suspend fun clearSession() {
        local.clearSession()
    }

    suspend fun createTransaction(typeTransaction: TypeTransaction, user: UserEntity, sourceCurrency: CryptocurrencyEntity, targetCurrency: CryptocurrencyEntity, amountRequested: BigDecimal) {
        //  atualiza usuário com sua moeda (UserCurrency) e insere uma transacao (TransactionEntity e UserTransaction)
        when(typeTransaction) {
            SALE -> {
                sourceCurrency.apply { amount -= amountRequested }
                local.saveCurrency(sourceCurrency)
                local.saveUser(user)
                local.saveUserCurrency(user.email, sourceCurrency.name, sourceCurrency.amount)
                local.saveTransaction(user.email, sourceCurrency.name, sourceCurrency.name, typeTransaction.id)
            }

            PURCHASE -> {
                sourceCurrency.apply { amount += amountRequested }
                local.saveCurrency(sourceCurrency)
                local.saveUser(user)
                local.saveUserCurrency(user.email, sourceCurrency.name, sourceCurrency.amount)
                local.saveTransaction(user.email, sourceCurrency.name, sourceCurrency.name, typeTransaction.id)
            }

            EXCHANGE -> {
                sourceCurrency.apply { amount -= amountRequested }
                targetCurrency.apply { amount += amountRequested }
                local.saveCurrency(sourceCurrency)
                local.saveCurrency(targetCurrency)
                local.saveUserCurrency(user.email, sourceCurrency.name, targetCurrency.amount)
                local.saveTransaction(user.email, sourceCurrency.name, targetCurrency.name, typeTransaction.id)
            }
        }

    }

    private suspend fun CryptocurrencyEntity.restoreFromDB() {
        local.getCriptoCurrencyByName(this.name)?.let { currency -> this.amount = currency.amount }
    }

}

