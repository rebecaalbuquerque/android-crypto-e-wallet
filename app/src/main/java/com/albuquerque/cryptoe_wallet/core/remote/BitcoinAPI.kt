package com.albuquerque.cryptoe_wallet.core.remote

import com.albuquerque.cryptoe_wallet.app.model.dto.BitcoinDTO
import retrofit2.http.GET

interface BitcoinAPI {

    @GET("BTC/ticker/")
    suspend fun fetchBitcoinData(): BitcoinDTO

}