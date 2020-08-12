package com.albuquerque.cryptoe_wallet.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.albuquerque.cryptoe_wallet.app.dao.CryptocurrencyDao
import com.albuquerque.cryptoe_wallet.app.dao.SessionDao
import com.albuquerque.cryptoe_wallet.app.dao.UserDao
import com.albuquerque.cryptoe_wallet.app.model.entity.*
import com.albuquerque.cryptoe_wallet.app.utils.Session
import kotlinx.coroutines.flow.Flow


class LocalRepositoryImpl(
    private val userDao: UserDao,
    private val sessionDao: SessionDao,
    private val cryptocurrencyDao: CryptocurrencyDao
): LocalRepository {

    override fun hasLoggedUser(): LiveData<Boolean> {
        return sessionDao.hasUserLogged().map { list ->
            if(list.isNotEmpty()) Session.userLogged = list.first().userId
            list.isNotEmpty()
        }
    }

    override fun getLoggedUser(): LiveData<UserEntity?> {
        return userDao.getUserByEmail(Session.userLogged)
    }

    override suspend fun signUp(user: UserEntity): UserEntity {
        return cryptocurrencyDao.getCurrenciesAndInsertWithUser(user) {
            cryptocurrencyDao.getAll().forEach {
                userDao.insert(user)
                cryptocurrencyDao.insertUserWithCurrencies(UserCurrency(user.email, it.name, 0))
            }
        }
    }

    override suspend fun signIn(email: String, password: String): UserEntity? {
        return userDao.checkUserAndDoLogin(email, password) { user ->
            user?.let {
                sessionDao.deleteAll()
                sessionDao.insert(SessionEntity(it.email))
            }
        }
    }

    override suspend fun saveSession(userId: String) {
        sessionDao.insert(SessionEntity(userId))
    }

    override suspend fun clearSession() {
        sessionDao.deleteAll()
    }

    override suspend fun saveUser(user: UserEntity): UserEntity? {
        getUserByEmailAndPassword(user.email, user.password)?.let {
            return it
        } ?: kotlin.run {
            userDao.insert(user)
            return null
        }

    }

    override suspend fun getUserByEmailAndPassword(email: String, password: String): UserEntity? = userDao.getUserByEmailAndPassword(email, password)

    override suspend fun saveCurrencies(currencies: List<CryptocurrencyEntity>) {
        cryptocurrencyDao.insertAll(currencies)
    }

    override suspend fun saveCurrency(currency: CryptocurrencyEntity) {
        cryptocurrencyDao.insert(currency)
    }

    override fun getCurrencies(): Flow<List<UserWithCurrencies>> = cryptocurrencyDao.getUserWithCurrencies()

    override fun getCriptoCurrencyByName(name: String): LiveData<CryptocurrencyEntity> = cryptocurrencyDao.getByName(name)
}