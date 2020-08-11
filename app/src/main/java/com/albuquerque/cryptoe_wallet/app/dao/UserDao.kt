package com.albuquerque.cryptoe_wallet.app.dao

import androidx.room.Dao
import androidx.room.Query
import com.albuquerque.cryptoe_wallet.app.model.entity.UserEntity
import com.albuquerque.cryptoe_wallet.core.database.BaseDao

@Dao
interface UserDao: BaseDao<UserEntity> {

    @Query("SELECT * FROM user WHERE email=:email AND password=:password")
    fun getUserByEmailAndPassword(email: String, password: String): UserEntity?

}