package com.albuquerque.cryptoe_wallet.app.dao

import androidx.room.Dao
import com.albuquerque.cryptoe_wallet.app.model.entity.CryptocurrencyEntity
import com.albuquerque.cryptoe_wallet.core.database.BaseDao

@Dao
interface CryptocurrencyDao: BaseDao<CryptocurrencyEntity> {
}