package com.albuquerque.cryptoe_wallet.app.repository


import androidx.lifecycle.LiveData
import com.albuquerque.cryptoe_wallet.app.model.dto.CryptocurrencyDTO
import com.albuquerque.cryptoe_wallet.app.model.entity.UserEntity
import com.albuquerque.cryptoe_wallet.app.utils.TypeCriptocurrency

interface Repository {

    fun hasLoggedUser(): LiveData<Boolean>

    suspend fun signUp(user: UserEntity): UserEntity

    suspend fun signIn(email: String, password: String): UserEntity?

    suspend fun fetchCriptoCurrency(typeCriptocurrency: TypeCriptocurrency): Result<CryptocurrencyDTO>

    suspend fun clearSession()

}