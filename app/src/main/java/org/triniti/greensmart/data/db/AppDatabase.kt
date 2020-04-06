package org.triniti.greensmart.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import org.triniti.greensmart.data.db.daos.*
import org.triniti.greensmart.data.db.entities.*
import org.triniti.greensmart.utilities.Coroutines

@Database(
    entities = [User::class, Bin::class, Shop::class, Cart::class, News::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getBinsDao(): BinsDao
    abstract fun getShopDao(): ShopDao
    abstract fun getCartDao(): CartDao
    abstract fun getNewsDao(): NewsDao

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
            "App-Database.db"
        ).fallbackToDestructiveMigration()
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    prepopulateDb(INSTANCE!!)
                }
            })
            .build()

        fun prepopulateDb(db: AppDatabase) {
            val message = mutableListOf<News>()
            message.add(
                News(
                    0,
                    "Welcome to Greensmart feeds!",
                    "This where you'll see notifications about greensmart events and other news. \nSwipe to view more info cards",
                    0,
                    "https://www.psdgraphics.com/file/wavy-turquoise-background.jpg"
                )
            )
            Coroutines.io {
                db.getNewsDao().insertMessages(message)
            }
        }
    }
}