package com.albuquerque.cryptoe_wallet.core.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.albuquerque.cryptoe_wallet.app.dao.CryptocurrencyDao
import com.albuquerque.cryptoe_wallet.app.dao.SessionDao
import com.albuquerque.cryptoe_wallet.app.dao.UserDao
import com.albuquerque.cryptoe_wallet.app.model.entity.CryptocurrencyEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.SessionEntity
import com.albuquerque.cryptoe_wallet.app.model.entity.UserEntity

@Database(
        version = 1,
        exportSchema = false,
        entities = [UserEntity::class, SessionEntity::class, CryptocurrencyEntity::class]
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "crypto_e_wallet"
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: synchronized(this) {buildDatabase(context)}
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                    context, AppDatabase::class.java,
                    DATABASE_NAME
            )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
        }

    }

    abstract val userDao: UserDao
    abstract val sessionDao: SessionDao
    abstract val cryptocurrencyDao: CryptocurrencyDao

}