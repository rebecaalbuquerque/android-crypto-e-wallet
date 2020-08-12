package com.albuquerque.cryptoe_wallet.app.repository

import com.albuquerque.cryptoe_wallet.app.model.dto.CryptocurrencyDTO


interface RemoteRepository {

    suspend fun fetchBicoinInfo(): Result<CryptocurrencyDTO>

    suspend fun fetchBritaInfo(): Result<CryptocurrencyDTO>

}