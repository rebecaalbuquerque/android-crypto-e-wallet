package com.albuquerque.cryptoe_wallet.app.repository


import com.albuquerque.cryptoe_wallet.app.repository.IRemoteRepository
import com.albuquerque.cryptoe_wallet.core.remote.BancoCentralAPI
import com.albuquerque.cryptoe_wallet.core.remote.BitcoinAPI
import com.albuquerque.cryptoe_wallet.core.remote.Remote

class RemoteRepository: Remote(), IRemoteRepository {

    private val bancoCentralAPI by lazy { getRetrofitBuilder("").create(BancoCentralAPI::class.java) }
    private val bitcoinAPI by lazy { getRetrofitBuilder("").create(BitcoinAPI::class.java) }



}