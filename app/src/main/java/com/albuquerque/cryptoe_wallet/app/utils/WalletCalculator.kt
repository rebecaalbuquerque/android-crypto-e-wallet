package com.albuquerque.cryptoe_wallet.app.utils

import com.albuquerque.cryptoe_wallet.app.extensions.toBrazilianCurrency
import com.albuquerque.cryptoe_wallet.app.model.ui.CryptocurrencyUI
import com.albuquerque.cryptoe_wallet.app.utils.StatusTransaction.*
import com.albuquerque.cryptoe_wallet.app.utils.TypeTransaction.*
import java.math.BigDecimal
import java.math.MathContext

object WalletCalculator {

    fun calculateTransactionNewBalance(
        cryptocurrencyUI: CryptocurrencyUI?,
        amount: BigDecimal,
        balance: BigDecimal?,
        typeTransaction: TypeTransaction?
    ): String {

        if (cryptocurrencyUI == null || balance == null || typeTransaction == null)
            return ""

        return when (typeTransaction) {
            PURCHASE -> {
                val value = balance.minus(cryptocurrencyUI.buyValue.times(amount))

                if (value < BigDecimal(0))
                    balance.toBrazilianCurrency()
                else
                    value.toBrazilianCurrency()
            }

            SALE -> {

                if (amount > cryptocurrencyUI.amount)
                    balance.toBrazilianCurrency()
                else
                    balance.plus(cryptocurrencyUI.sellValue.times(amount)).toBrazilianCurrency()
            }

            EXCHANGE -> balance.toBrazilianCurrency()
        }

    }

    fun calculateTransactionTotalValue(
        sourceCurrency: CryptocurrencyUI?,
        targetCurrency: CryptocurrencyUI?,
        amount: BigDecimal,
        balance: BigDecimal?,
        typeTransaction: TypeTransaction?
    ): Pair<String, StatusTransaction> {
        if (sourceCurrency == null || typeTransaction == null || balance == null || targetCurrency == null)
            return "" to UNAVAILABLE_TRANSACTION

        return when (typeTransaction) {
            PURCHASE -> {
                val value = sourceCurrency.buyValue.times(amount)

                if (value > balance)
                    "Saldo insucifiente para realizar esta operação." to INSUFFICIENT_FUNDS
                else
                    value.toBrazilianCurrency() to AVAILABLE_TRANSACTION
            }

            SALE -> {
                if (amount > sourceCurrency.amount)
                    "Saldo insucifiente para realizar esta operação." to INSUFFICIENT_FUNDS
                else
                    (sourceCurrency.sellValue.times(amount)).toBrazilianCurrency() to AVAILABLE_TRANSACTION
            }

            EXCHANGE -> {
                val exchangeAmount = sourceCurrency.buyValue.divide(targetCurrency.buyValue, MathContext.DECIMAL128)
                val exchangeValue =  amount.multiply(exchangeAmount)

                when {
                    sourceCurrency.amount <= BigDecimal.ZERO -> "Saldo insucifiente para realizar esta operação." to INSUFFICIENT_FUNDS

                    exchangeValue <= BigDecimal.ZERO -> "" to UNAVAILABLE_TRANSACTION

                    else -> exchangeValue.toBrazilianCurrency() to AVAILABLE_TRANSACTION

                }

            }
        }
    }

}