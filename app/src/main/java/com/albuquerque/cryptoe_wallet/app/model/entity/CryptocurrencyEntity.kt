package com.albuquerque.cryptoe_wallet.app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "cryptocurrency")
data class CryptocurrencyEntity(
    @PrimaryKey val name: String,
    val buy: BigDecimal,
    val sell: BigDecimal,
    var amount: BigDecimal,
    val amountValue: BigDecimal = amount.times(buy)
)