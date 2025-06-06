package com.example.otchislenie.data.local.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.otchislenie.data.model.BudgetEntity
import com.example.otchislenie.data.model.BudgetWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Insert
    suspend fun insert(budget: BudgetEntity): Long

    @Update
    suspend fun update(budget: BudgetEntity)

    @Delete
    suspend fun delete(budget: BudgetEntity)

    @Query("SELECT * FROM budgets WHERE isActive = 1 ORDER BY startDate DESC")
    fun getAllActiveBudgets(): Flow<List<BudgetEntity>>

    @Query("SELECT * FROM budgets WHERE categoryId = :categoryId AND isActive = 1")
    fun getActiveBudgetByCategory(categoryId: Long): Flow<BudgetEntity?>

    @Query("SELECT * FROM budgets WHERE :date BETWEEN startDate AND endDate AND isActive = 1")
    fun getBudgetsForDate(date: Long): Flow<List<BudgetEntity>>

    @Query("SELECT * FROM budgets WHERE categoryId = 0 AND isActive = 1")
    fun getGeneralBudgets(): Flow<List<BudgetEntity>>

    @Query("SELECT * FROM budgets WHERE id = :id")
    suspend fun getBudgetById(id: Long): BudgetEntity?

    @Query("UPDATE budgets SET isActive = 0 WHERE endDate < :currentDate AND isActive = 1")
    suspend fun deactivateExpiredBudgets(currentDate: Long)

    @Transaction
    @Query("SELECT * FROM budgets WHERE isActive = 1")
    fun getActiveBudgetsWithCategories(): Flow<List<BudgetWithCategory>>
}