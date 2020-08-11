package com.albuquerque.cryptoe_wallet.app.model.ui

import java.math.BigDecimal

data class CryptocurrencyUI(
    val name: String,
    val buyValue: BigDecimal,
    val sellValue: BigDecimal
)