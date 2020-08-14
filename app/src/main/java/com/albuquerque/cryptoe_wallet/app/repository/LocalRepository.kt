package com.albuquerque.cryptoe_wallet.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.albuquerque.cryptoe_wallet.app.dao.*
import com.albuquerque.cryptoe_wallet.app.model.entity.*
import com.albuquerque.cryptoe_wallet.app.utils.Session
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal


class LocalRepository(
    private val userDao: UserDao,
    private val sessionDao: SessionDao,
    private val cryptocurrencyDao: CryptocurrencyDao,
    private val userCryptocurrencyDao: UserCryptocurrencyDao,
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
            userDao.insert(user)

            cryptocurrencyDao.getAll().forEach {
                userCryptocurrencyDao.insert(UserCryptocurrencyEntity(it.name, user.email, it.buy, it.sell, BigDecimal.ZERO))
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
    }

    suspend fun saveUser(user: UserEntity) {
        userDao.insert(user)
    }

    suspend fun saveCurrency(currency: CryptocurrencyEntity) {
        cryptocurrencyDao.insert(currency)
        userCryptocurrencyDao.updateValues(currency)
    }

    suspend fun saveUserCurrency(userCurrency: UserCryptocurrencyEntity){
        userCryptocurrencyDao.insert(userCurrency)
    }

    fun getCurrencies(): Flow<List<UserCryptocurrencyEntity>> = cryptocurrencyDao.getUserCurrencies()

    fun getTransactions(): Flow<List<UserWithTransactions>> = transactionDao.getUserWithTransactions()

    fun getCriptoCurrencyByNameAsLiveData(name: String): LiveData<UserCryptocurrencyEntity?> = cryptocurrencyDao.getByNameAsLiveData(name)

    suspend fun saveTransaction(userId: String, sourceCurrency: String, targetCurrency: String, typeTransaction: Int, amount: BigDecimal) {
        val transaction = TransactionEntity(sourceCurrency, targetCurrency, typeTransaction, amount)
        transactionDao.insert(transaction)
        transactionDao.insertUserWithTransaction(UserTransaction(userId, transaction.date))
    }

}