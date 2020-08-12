package com.albuquerque.cryptoe_wallet.app.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.albuquerque.cryptoe_wallet.core.extensions.add
import java.util.*

@Entity(tableName = "session")
class SessionEntity(
    @PrimaryKey val userId: String,
    val expiresIn: Date? = Calendar.getInstance().time.add(Calendar.MINUTE, 10)
)