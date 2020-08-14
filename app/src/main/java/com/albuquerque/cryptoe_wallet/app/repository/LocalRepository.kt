package com.albuquerque.cryptoe_wallet.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.albuquerque.cryptoe_wallet.app.dao.CryptocurrencyDao
import com.albuquerque.cryptoe_wallet.app.dao.SessionDao
import com.albuquerque.cryptoe_wallet.app.dao.TransactionDao
import com.albuquerque.cryptoe_wallet.app.dao.UserDao
import com.albuquerque.cryptoe_wallet.app.model.entity.*
import com.albuquerque.cryptoe_wallet.app.utils.Session
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal


class LocalRepository(
    private val userDao: UserDao,
    private val sessionDao: SessionDao,
    private val cryptocurrencyDao: CryptocurrencyDao,
    private val transactionDao: TransactionDao
) {

    fun hasLoggedUser(): LiveData<Boolean> {
        return sessionDao.hasUserLogged().map { list ->
            if(list.isNotEmpty()) Session.userLogged = list.first().userId
            list.isNotEmpty()
        }
    }

    fun getLoggedUser(): LiveData<UserEntity?> {
        return userDao.getUserByEmail(Session.userLogged)
    }

    suspend fun signUp(user: UserEntity): UserEntity {
        return cryptocurrencyDao.getCurrenciesAndInsertWithUser(user) {
            cryptocurrencyDao.getAll().forEach {
                userDao.insert(user)
                cryptocurrencyDao.insertUserWithCurrency(UserCurrency(user.email, it.name, BigDecimal(0)))
            }
        }
    }

    suspend fun signIn(email: String, password: String): UserEntity? {
        return userDao.checkUserAndDoLogin(email, password) { user ->
            user?.let {
                sessionDao.deleteAll()
                sessionDao.insert(SessionEntity(it.email))
            }
        }
    }

    suspend fun clearSession() {
        sessionDao.deleteAll()
        cryptocurrencyDao.deleteAll()
        transactionDao.deleteAll()
    }

    suspend fun saveUser(user: UserEntity) {
        userDao.insert(user)
    }

    suspend fun saveCurrency(currency: CryptocurrencyEntity) {
        cryptocurrencyDao.insert(currency)
    }

    fun getCurrencies(): Flow<List<UserWithCurrencies>> = cryptocurrencyDao.getUserWithCurrencies()

    fun getTransactions(): Flow<List<UserWithTransactions>> = transactionDao.getUserWithTransactions()

    fun getCriptoCurrencyByNameAsLiveData(name: String): LiveData<CryptocurrencyEntity?> = cryptocurrencyDao.getByNameAsLiveData(name)

    suspend fun getCriptoCurrencyByName(name: String): CryptocurrencyEntity? = cryptocurrencyDao.getByName(name)

    suspend fun saveUserCurrency(userId: String, currencyId: String, amount: BigDecimal) {
        cryptocurrencyDao.insertUserWithCurrency(UserCurrency(userId, currencyId, amount))
    }

    suspend fun saveTransaction(userId: String, sourceCurrency: String, targetCurrency: String, typeTransaction: Int) {
        val transaction = TransactionEntity(sourceCurrency, targetCurrency, typeTransaction)
        transactionDao.insert(transaction)
        transactionDao.insertUserWithTransaction(UserTransaction(userId, transaction.date))
    }

}