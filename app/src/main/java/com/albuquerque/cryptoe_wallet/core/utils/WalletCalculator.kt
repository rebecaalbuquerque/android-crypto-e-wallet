package com.albuquerque.cryptoe_wallet.core.utils

import com.albuquerque.cryptoe_wallet.app.extensions.toBrazilianCurrency
import com.albuquerque.cryptoe_wallet.app.model.ui.CryptocurrencyUI
import com.albuquerque.cryptoe_wallet.app.utils.TypeTransaction
import com.albuquerque.cryptoe_wallet.app.utils.TypeTransaction.*
import java.math.BigDecimal

object WalletCalculator {

    fun calculateTransactionNewBalance(cryptocurrencyUI: CryptocurrencyUI?, amount: BigDecimal, balance: BigDecimal?, typeTransaction: TypeTransaction?): String {

        if (cryptocurrencyUI == null || balance == null || typeTransaction == null)
             return ""

        return when(typeTransaction) {
            PURCHASE -> {
                val value = balance.minus(cryptocurrencyUI.buyValue.times(amount))

                if(value < BigDecimal(0))
                    balance.toBrazilianCurrency()
                else
                    value.toBrazilianCurrency()
            }

            SALE -> balance.minus(cryptocurrencyUI.sellValue.times(amount)).toBrazilianCurrency()

            EXCHANGE -> "setNewBalance"
        }

    }

    fun calculateTransactionTotalValue(cryptocurrencyUI: CryptocurrencyUI?, amount: BigDecimal, balance: BigDecimal?, typeTransaction: TypeTransaction?): String {
        if(cryptocurrencyUI == null || typeTransaction == null || balance == null)
            return ""

        return when(typeTransaction) {
            PURCHASE -> {
                val value = cryptocurrencyUI.buyValue.times(amount)

                if (value > balance)
                    "Saldo insucifiente para realizar esta operação."
                else
                    value.toBrazilianCurrency()
            }
            SALE -> (cryptocurrencyUI.sellValue.times(amount)).toBrazilianCurrency()
            EXCHANGE -> "setTotalAmount"
        }
    }

}