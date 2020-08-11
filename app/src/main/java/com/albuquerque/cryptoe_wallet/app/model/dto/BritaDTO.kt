package com.albuquerque.cryptoe_wallet.app.model.dto

import java.math.BigDecimal

data class BritaDTO(
    val value: List<BritaValue>
)

data class BritaValue(
    val cotacaoCompra: BigDecimal,
    val cotacaoVenda: BigDecimal,
    val tipoBoletim: String
)