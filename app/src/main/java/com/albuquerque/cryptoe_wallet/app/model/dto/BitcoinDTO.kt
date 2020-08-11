package com.albuquerque.cryptoe_wallet.app.model.dto

import java.math.BigDecimal

data class BitcoinDTO(
    val ticker: Ticker
)

data class Ticker(
    val buy: BigDecimal,
    val sell: BigDecimal,
    val date: Long
)