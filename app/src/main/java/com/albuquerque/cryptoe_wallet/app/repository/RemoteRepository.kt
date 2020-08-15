package com.albuquerque.cryptoe_wallet.app.repository


import com.albuquerque.cryptoe_wallet.app.model.dto.CryptocurrencyDTO
import com.albuquerque.cryptoe_wallet.app.model.toCryptocurrencyDTO
import com.albuquerque.cryptoe_wallet.core.remote.BritaAPI
import com.albuquerque.cryptoe_wallet.core.remote.BitcoinAPI
import com.albuquerque.cryptoe_wallet.core.remote.Remote

class RemoteRepository: Remote() {

    private val bancoCentralAPI by lazy { getRetrofitBuilder("https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/").create(BritaAPI::class.java) }
    private val bitcoinAPI by lazy { getRetrofitBuilder("https://www.mercadobitcoin.net/api/").create(BitcoinAPI::class.java) }

    suspend fun fetchBicoinInfo(): Result<CryptocurrencyDTO> {
        return runRequest(bitcoinAPI.fetchBitcoinData()).map { it.toCryptocurrencyDTO() }
    }

    suspend fun fetchBritaInfo(): Result<CryptocurrencyDTO> {
        return runRequest(bancoCentralAPI.fetchBritaData()).map { it.toCryptocurrencyDTO() }
    }

}