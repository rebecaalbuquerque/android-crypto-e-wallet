package com.albuquerque.cryptoe_wallet.app.repository


import androidx.lifecycle.LiveData
import com.albuquerque.cryptoe_wallet.app.model.entity.CryptocurrencyEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserWithCurrencies
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    fun hasLoggedUser(): LiveData<Boolean>

    fun getLoggedUser(): LiveData<UserEntity?>

    suspend fun saveSession(userId: String)

    suspend fun saveUser(user: UserEntity): UserEntity?

    suspend fun getUserByEmailAndPassword(email: String, password: String): UserEntity?

    suspend fun saveCurrencies(currencies: List<CryptocurrencyEntity>)

    suspend fun saveCurrency(currency: CryptocurrencyEntity)

    suspend fun signUp(user: UserEntity): UserEntity

    suspend fun signIn(email: String, password: String): UserEntity?

    suspend fun clearSession()

    fun getCurrencies(): Flow<List<UserWithCurrencies>>

    fun getCriptoCurrencyByName(name: String): LiveData<CryptocurrencyEntity>

}