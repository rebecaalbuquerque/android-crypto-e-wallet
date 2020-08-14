package com.albuquerque.cryptoe_wallet.app.model.entity

import androidx.room.Entity
import androidx.room.Index
import java.math.BigDecimal

@Entity(
    tableName = "user_cryptocurrency",
    primaryKeys = ["currency", "user"],
    indices = [Index("currency"), Index(("user"))]
)
class UserCryptocurrencyEntity(
    val currency: String,
    val user: String,
    val buy: BigDecimal,
    val sell: BigDecimal,
    var amount: BigDecimal
)