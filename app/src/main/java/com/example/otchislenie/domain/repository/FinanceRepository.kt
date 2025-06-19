package com.example.otchislenie.domain.repository


import com.example.otchislenie.domain.model.Budget
import com.example.otchislenie.domain.model.Category
import com.example.otchislenie.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface FinanceRepository {
    // Транзакции
    suspend fun addTransaction(transaction: Transaction)
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
    fun getAllTransactions(): Flow<List<Transaction>>
    fun getTransactionsByDate(startDate: Long, endDate: Long): Flow<List<Transaction>>
    fun getTransactionsByCategory(categoryId: Long): Flow<List<Transaction>>

    // Категории
    suspend fun addCategory(category: Category)
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(category: Category)
    fun getAllCategories(): Flow<List<Category>>
    fun getCategoriesByType(type: String): Flow<List<Category>> // "income" или "expense"
    suspend fun getCategoryById(id: Long): Category?
    fun getExpensesByCategory(startDate: Long, endDate: Long): Flow<List<CategoryWithAmount>>

    data class CategoryWithAmount(
        val categoryId: Long,
        val categoryName: String,
        val totalAmount: Double
    )

    // Бюджеты
    suspend fun addBudget(budget: Budget)
    suspend fun updateBudget(budget: Budget)
    suspend fun deleteBudget(budget: Budget)
    fun getAllBudgets(): Flow<List<Budget>>
    fun getActiveBudgets(): Flow<List<Budget>>
    fun getBudgetsForDate(date: Long): Flow<List<Budget>>
    suspend fun getBudgetById(id: Long): Budget?

    suspend fun clearAllData()
}