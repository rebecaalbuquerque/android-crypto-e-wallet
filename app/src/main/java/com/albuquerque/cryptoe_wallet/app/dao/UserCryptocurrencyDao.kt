package com.albuquerque.cryptoe_wallet.app.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.albuquerque.cryptoe_wallet.app.model.entity.CryptocurrencyEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserCryptocurrencyEntity
import com.albuquerque.cryptoe_wallet.app.utils.Session
import com.albuquerque.cryptoe_wallet.core.database.BaseDao

@Dao
interface UserCryptocurrencyDao: BaseDao<UserCryptocurrencyEntity> {

    @Query("DELETE FROM user_cryptocurrency WHERE user=:user")
    suspend fun deleteAll(user: String = Session.userLogged)

    @Query("select * from user_cryptocurrency WHERE currency=:name")
    fun getAll(name: String): List<UserCryptocurrencyEntity>

    @Transaction
    suspend fun updateValues(currency: CryptocurrencyEntity) {
        getAll(currency.name).forEach {
            insert(UserCryptocurrencyEntity(it.currency, it.user, currency.buy, currency.sell, it.amount))
        }

    }

}