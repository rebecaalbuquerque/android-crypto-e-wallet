package com.albuquerque.cryptoe_wallet.core.utils

import com.albuquerque.cryptoe_wallet.app.extensions.toBrazilianCurrency
import com.albuquerque.cryptoe_wallet.app.model.ui.CryptocurrencyUI
import com.albuquerque.cryptoe_wallet.app.utils.StatusTransaction
import com.albuquerque.cryptoe_wallet.app.utils.StatusTransaction.*
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

            SALE -> {

                if(amount > cryptocurrencyUI.amount)
                    balance.toBrazilianCurrency()
                else
                    balance.plus(cryptocurrencyUI.sellValue.times(amount)).toBrazilianCurrency()
            }

            EXCHANGE -> "setNewBalance"
        }

    }

    fun calculateTransactionTotalValue(cryptocurrencyUI: CryptocurrencyUI?, amount: BigDecimal, balance: BigDecimal?, typeTransaction: TypeTransaction?): Pair<String, StatusTransaction> {
        if(cryptocurrencyUI == null || typeTransaction == null || balance == null)
            return "" to UNAVAILABLE_TRANSACTION

        return when(typeTransaction) {
            PURCHASE -> {
                val value = cryptocurrencyUI.buyValue.times(amount)

                if (value > balance)
                    "Saldo insucifiente para realizar esta operação." to INSUFFICIENT_FUNDS
                else
                    value.toBrazilianCurrency() to AVAILABLE_TRANSACTION
            }
            SALE -> {
                if(amount > cryptocurrencyUI.amount)
                    "Você não tem moedas suficiente para realizar esta operação." to INSUFFICIENT_CRYPTOCURRENCIES
                else
                    (cryptocurrencyUI.sellValue.times(amount)).toBrazilianCurrency() to AVAILABLE_TRANSACTION
            }
            EXCHANGE -> "setTotalAmount" to AVAILABLE_TRANSACTION
        }
    }

}