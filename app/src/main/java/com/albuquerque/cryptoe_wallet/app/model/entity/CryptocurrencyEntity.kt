package com.albuquerque.cryptoe_wallet.app.model.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.albuquerque.cryptoe_wallet.core.extensions.getCurrentBrazilianTime
import java.math.BigDecimal

@Entity(tableName = "cryptocurrency")
data class CryptocurrencyEntity(
    @PrimaryKey val name: String,
    val buy: BigDecimal,
    val sell: BigDecimal
)