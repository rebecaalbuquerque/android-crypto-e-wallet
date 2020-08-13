package com.albuquerque.cryptoe_wallet.app.repository


import androidx.lifecycle.LiveData
import com.albuquerque.cryptoe_wallet.app.model.dto.CryptocurrencyDTO
import com.albuquerque.cryptoe_wallet.app.model.entity.CryptocurrencyEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserWithCurrencies
import com.albuquerque.cryptoe_wallet.app.utils.TypeCriptocurrency
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun hasLoggedUser(): LiveData<Boolean>

    fun getLoggedUser(): LiveData<UserEntity?>

    suspend fun signUp(user: UserEntity): UserEntity

    suspend fun signIn(email: String, password: String): UserEntity?

    suspend fun fetchCriptoCurrency(typeCriptocurrency: TypeCriptocurrency): Result<CryptocurrencyDTO>

    fun getCriptoCurrency(): Flow<List<UserWithCurrencies>>

    fun getCriptoCurrencyByName(name: String): LiveData<CryptocurrencyEntity>

    suspend fun clearSession()

}