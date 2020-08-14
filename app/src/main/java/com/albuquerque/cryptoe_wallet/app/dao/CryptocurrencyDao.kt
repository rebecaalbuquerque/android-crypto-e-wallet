package com.albuquerque.cryptoe_wallet.app.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.albuquerque.cryptoe_wallet.app.model.entity.CryptocurrencyEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserCurrency
import com.albuquerque.cryptoe_wallet.app.model.entity.UserEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserWithCurrencies
import com.albuquerque.cryptoe_wallet.app.utils.Session
import com.albuquerque.cryptoe_wallet.core.database.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptocurrencyDao: BaseDao<CryptocurrencyEntity> {

    @Query("DELETE FROM session")
    suspend fun deleteAll()

    @Query("select * from cryptocurrency")
    fun getAll(): List<CryptocurrencyEntity>

    @Query("select * from cryptocurrency WHERE name=:name")
    fun getByNameAsLiveData(name: String): LiveData<CryptocurrencyEntity?>

    @Query("select * from cryptocurrency WHERE name=:name")
    suspend fun getByName(name: String): CryptocurrencyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserWithCurrency(join: UserCurrency)

    @Transaction
    suspend fun getCurrenciesAndInsertWithUser(user: UserEntity, action: suspend () -> Unit): UserEntity {
        action.invoke()
        return user
    }

    @Transaction
    @Query("select * from user WHERE email=:user")
    fun getUserWithCurrencies(user: String = Session.userLogged): Flow<List<UserWithCurrencies>>

    @Query("SELECT * FROM usercurrency WHERE email=:userId AND name=:currencyId")
    fun getUserCurrencyById(userId: String, currencyId: String): LiveData<UserCurrency>

}