package com.albuquerque.cryptoe_wallet.app.repository


import androidx.lifecycle.LiveData
import com.albuquerque.cryptoe_wallet.app.model.dto.CryptocurrencyDTO
import com.albuquerque.cryptoe_wallet.app.model.entity.UserEntity
import com.albuquerque.cryptoe_wallet.app.utils.TypeCriptocurrency

interface Repository {

    fun hasLoggedUser(): LiveData<Boolean>

    suspend fun signUp(user: UserEntity)

    suspend fun signIn(email: String, password: String): Result<UserEntity>

    suspend fun fetchCriptoCurrency(typeCriptocurrency: TypeCriptocurrency): Result<CryptocurrencyDTO>

}