package com.example.otchislenie.data.local.database

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.otchislenie.data.local.DAO.BudgetDao
import com.example.otchislenie.data.local.DAO.CategoryDao
import com.example.otchislenie.data.local.DAO.TransactionDao
import com.example.otchislenie.data.model.BudgetEntity
import com.example.otchislenie.data.model.CategoryEntity
import com.example.otchislenie.data.model.TransactionEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Date

@Database(
    entities = [
        TransactionEntity::class,
        CategoryEntity::class,
        BudgetEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao
    abstract fun budgetDao(): BudgetDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private const val DATABASE_NAME = "finance_tracker_db"

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                getInstance(context)?.let { dbInstance ->
                                    populateInitialData(dbInstance)
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private suspend fun populateInitialData(database: AppDatabase) {
            // Заполняем начальные категории
            val categoryDao = database.categoryDao()
            categoryDao.insertDefaultCategories(
                CategoryEntity.defaultExpenseCategories() +
                        CategoryEntity.defaultIncomeCategories()
            )

            // Можно добавить начальные бюджеты
            val budgetDao = database.budgetDao()
            val now = System.currentTimeMillis()
            val oneMonthLater = now + 30L * 24 * 60 * 60 * 1000

            budgetDao.insert(
                BudgetEntity(
                    categoryId = 0, // Общий бюджет
                    amount = 10000.0,
                    period = BudgetEntity.PERIOD_MONTHLY,
                    startDate = now,
                    endDate = oneMonthLater,
                    currency = "RUB"
                )
            )
        }
    }
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}