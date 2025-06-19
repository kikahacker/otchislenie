package com.example.otchislenie.data.local.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.otchislenie.data.model.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: TransactionEntity): Long

    @Update
    suspend fun update(transaction: TransactionEntity)

    @Delete
    suspend fun delete(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE date BETWEEN :start AND :end ORDER BY date DESC")
    fun getTransactionsByDate(start: Long, end: Long): Flow<List<TransactionEntity>>
    @Query("SELECT * FROM transactions WHERE categoryId = :categoryId")
    fun getTransactionsByCategory(categoryId: Long): Flow<List<TransactionEntity>>
    @Query("""
        SELECT c.id, c.name, SUM(t.amount) as total 
        FROM transactions t 
        JOIN categories c ON t.categoryId = c.id 
        WHERE t.type = 'expense' 
        AND t.date BETWEEN :startDate AND :endDate
        GROUP BY c.id, c.name
    """)
    fun getExpensesByCategory(startDate: Long, endDate: Long): Flow<List<CategoryWithAmount>>

    data class CategoryWithAmount(
        val id: Long,
        val name: String,
        val total: Double
    )

    @Query("DELETE FROM transactions")
    suspend fun deleteAllTransactions()
}