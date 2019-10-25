package org.triniti.greensmart.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.triniti.greensmart.data.db.daos.BinsDao
import org.triniti.greensmart.data.db.daos.UserDao
import org.triniti.greensmart.data.db.entities.Bin
import org.triniti.greensmart.data.db.entities.User

@Database(entities = [User::class, Bin::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getBinsDao(): BinsDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE
            ?: synchronized(LOCK) {
                INSTANCE
                    ?: buildDatabase(context).also {
                        INSTANCE = it
                    }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "User_Database.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}