package com.albuquerque.cryptoe_wallet.app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val email: String,
    val password: String,
    val fullName: String,
    val balance: BigDecimal = BigDecimal(100000.0)
)