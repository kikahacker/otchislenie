package com.example.otchislenie.data.repository

import com.example.otchislenie.data.local.DAO.BudgetDao
import com.example.otchislenie.data.local.DAO.CategoryDao
import com.example.otchislenie.data.local.DAO.TransactionDao
import com.example.otchislenie.data.model.BudgetEntity
import com.example.otchislenie.data.model.CategoryEntity
import com.example.otchislenie.data.model.TransactionEntity
import com.example.otchislenie.domain.model.Budget
import com.example.otchislenie.domain.model.Category
import com.example.otchislenie.domain.model.Transaction
import com.example.otchislenie.domain.repository.FinanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class FinanceRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao,
    private val budgetDao: BudgetDao
) : FinanceRepository {

    // region Транзакции
    override suspend fun addTransaction(transaction: Transaction) {
        transactionDao.insert(transaction.toEntity())
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.update(transaction.toEntity())
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.delete(transaction.toEntity())
    }

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions()
            .map { transactions -> transactions.map { it.toDomain() } }
    }

    override fun getTransactionsByDate(startDate: Long, endDate: Long): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByDate(startDate, endDate)
            .map { transactions -> transactions.map { it.toDomain() } }
    }

    override fun getTransactionsByCategory(categoryId: Long): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByCategory(categoryId)
            .map { transactions -> transactions.map { it.toDomain() } }
    }
    // endregion

    // region Категории
    override suspend fun addCategory(category: Category) {
        categoryDao.insert(category.toEntity())
    }

    override suspend fun updateCategory(category: Category) {
        categoryDao.update(category.toEntity())
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDao.delete(category.toEntity())
    }

    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories()
            .map { categories -> categories.map { it.toDomain() } }
    }

    override fun getCategoriesByType(type: String): Flow<List<Category>> {
        return categoryDao.getCategoriesByType(type)
            .map { categories -> categories.map { it.toDomain() } }
    }

    override suspend fun getCategoryById(id: Long): Category? {
        return categoryDao.getCategoryById(id)?.toDomain()
    }
    // endregion

    // region Бюджеты
    override suspend fun addBudget(budget: Budget) {
        budgetDao.insert(budget.toEntity())
    }

    override suspend fun updateBudget(budget: Budget) {
        budgetDao.update(budget.toEntity())
    }

    override suspend fun deleteBudget(budget: Budget) {
        budgetDao.delete(budget.toEntity())
    }

    override fun getAllBudgets(): Flow<List<Budget>> {
        return budgetDao.getAllActiveBudgets()
            .map { budgets -> budgets.map { it.toDomain() } }
    }

    override fun getActiveBudgets(): Flow<List<Budget>> {
        return budgetDao.getAllActiveBudgets()
            .map { budgets -> budgets.map { it.toDomain() } }
    }

    override fun getBudgetsForDate(date: Long): Flow<List<Budget>> {
        return budgetDao.getBudgetsForDate(date)
            .map { budgets -> budgets.map { it.toDomain() } }
    }

    override suspend fun getBudgetById(id: Long): Budget? {
        return budgetDao.getBudgetById(id)?.toDomain()
    }
    // endregion
    override fun getExpensesByCategory(startDate: Long, endDate: Long): Flow<List<FinanceRepository.CategoryWithAmount>> {
        return transactionDao.getExpensesByCategory(startDate, endDate).map { list ->
            list.map {
                FinanceRepository.CategoryWithAmount(
                    categoryId = it.id,
                    categoryName = it.name,
                    totalAmount = it.total
                )
            }
        }
    }
    // region Вспомогательные методы преобразования
    private fun TransactionEntity.toDomain(): Transaction {
        return Transaction(
            id = id,
            amount = amount,
            category = Category(id = categoryId, name = "", iconRes = "", color = 0, type = ""),
            date = date,
            note = note,
            type = type
        ).also {
            // Загружаем полную информацию о категории при необходимости
            if (it.category.name.isEmpty()) {
                runBlocking {
                    categoryDao.getCategoryById(categoryId)?.let { categoryEntity ->
                        it.category = categoryEntity.toDomain()
                    }
                }
            }
        }
    }

    private fun Transaction.toEntity(): TransactionEntity {
        return TransactionEntity(
            id = id,
            amount = amount,
            categoryId = category.id,
            date = date,
            note = note,
            type = type
        )
    }
    override suspend fun clearAllData() {
        transactionDao.deleteAllTransactions()
        categoryDao.deleteAllCategories()
        budgetDao.deleteAllBudgets()

        // После очистки можно снова добавить дефолтные категории
        categoryDao.insertDefaultCategories(
            CategoryEntity.defaultExpenseCategories() +
                    CategoryEntity.defaultIncomeCategories()
        )
    }
    private fun CategoryEntity.toDomain(): Category {
        return Category(
            id = id,
            name = name,
            iconRes = iconRes,
            color = color,
            type = type,
            isUserCreated = isUserCreated
        )
    }

    private fun Category.toEntity(): CategoryEntity {
        return CategoryEntity(
            id = id,
            name = name,
            iconRes = iconRes,
            color = color,
            type = type,
            isUserCreated = isUserCreated
        )
    }

    private fun BudgetEntity.toDomain(): Budget {
        return Budget(
            id = id,
            categoryId = categoryId,
            amount = amount,
            period = period,
            startDate = startDate,
            endDate = endDate,
            currency = currency,
            isActive = isActive
        )
    }

    private fun Budget.toEntity(): BudgetEntity {
        return BudgetEntity(
            id = id,
            categoryId = categoryId,
            amount = amount,
            period = period,
            startDate = startDate,
            endDate = endDate,
            currency = currency,
            isActive = isActive
        )
    }
    // endregion
}