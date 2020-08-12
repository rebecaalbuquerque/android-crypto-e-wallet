package com.albuquerque.cryptoe_wallet.app.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.albuquerque.cryptoe_wallet.app.model.toUI
import com.albuquerque.cryptoe_wallet.app.model.ui.CryptocurrencyUI
import com.albuquerque.cryptoe_wallet.app.repository.Repository

class GetCurrencyByName(
    private val repository: Repository
) {

    fun invoke(name: String): LiveData<CryptocurrencyUI> {
        return repository.getCriptoCurrencyByName(name).map {
            it.toUI()
        }
    }

}