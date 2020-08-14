package com.albuquerque.cryptoe_wallet.app.model.dto

import java.math.BigDecimal

data class CryptocurrencyDTO(
    val name: String,
    val buyValue: BigDecimal?,
    val sellValue: BigDecimal?
)