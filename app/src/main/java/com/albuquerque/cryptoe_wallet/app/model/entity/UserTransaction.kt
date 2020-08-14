package com.albuquerque.cryptoe_wallet.app.model.entity

import androidx.room.Entity


/**
 * @param email id of the User
 * @param date id of the Transaction
 * */
@Entity(primaryKeys = ["date", "email"])
data class UserTransaction(
    val email: String,
    val date: Long
)