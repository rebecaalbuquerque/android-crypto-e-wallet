package com.albuquerque.cryptoe_wallet.app.model.entity

import androidx.room.Entity
import androidx.room.Index


/**
 * @param email id of the User
 * @param date id of the Transaction
 * */
@Entity(
    tableName = "user_transaction",
    primaryKeys = ["date", "email"],
    indices = [Index("date"), Index("email")]
)
data class UserTransaction(
    val email: String,
    val date: Long
)