package com.albuquerque.cryptoe_wallet.app.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.albuquerque.cryptoe_wallet.app.model.entity.CryptocurrencyEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserCurrency
import com.albuquerque.cryptoe_wallet.app.model.entity.UserEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserWithCurrencies
import com.albuquerque.cryptoe_wallet.core.database.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptocurrencyDao: BaseDao<CryptocurrencyEntity> {

    @Query("select * from cryptocurrency")
    fun getAll(): List<CryptocurrencyEntity>

    @Query("select * from cryptocurrency WHERE name=:name")
    fun getByName(name: String): LiveData<CryptocurrencyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserWithCurrencies(join: UserCurrency)

    @Transaction
    suspend fun getCurrenciesAndInsertWithUser(user: UserEntity, action: suspend () -> Unit): UserEntity {
        action.invoke()
        return user
    }

    @Transaction
    @Query("select * from user")
    fun getUserWithCurrencies(): Flow<List<UserWithCurrencies>>

}