package com.albuquerque.cryptoe_wallet.app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.albuquerque.cryptoe_wallet.app.dao.CryptocurrencyDao
import com.albuquerque.cryptoe_wallet.app.dao.SessionDao
import com.albuquerque.cryptoe_wallet.app.dao.UserDao
import com.albuquerque.cryptoe_wallet.app.model.entity.CryptocurrencyEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.SessionEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserEntity


class LocalRepositoryImpl(
    private val userDao: UserDao,
    private val sessionDao: SessionDao,
    private val cryptocurrencyDao: CryptocurrencyDao
): LocalRepository {

    override fun hasLoggedUser(): LiveData<Boolean> {
        return sessionDao.hasUserLogged().map { size ->
            size > 0
        }
    }

    override suspend fun saveSession(userId: String) {
        sessionDao.insert(SessionEntity(userId))
    }

    override suspend fun signUp(user: UserEntity) {
        userDao.insert(user)
    }

    override suspend fun signIn(email: String, password: String): UserEntity? = userDao.getUserByEmailAndPassword(email, password)

    override suspend fun saveCurrencies(currencies: List<CryptocurrencyEntity>) {
        cryptocurrencyDao.insertAll(currencies)
    }

    override suspend fun saveCurrency(currency: CryptocurrencyEntity) {
        cryptocurrencyDao.insert(currency)
    }

}