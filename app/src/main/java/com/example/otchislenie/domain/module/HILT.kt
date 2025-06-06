package com.example.otchislenie.domain.module

import android.content.Context
import com.example.otchislenie.data.local.DAO.BudgetDao
import com.example.otchislenie.data.local.DAO.CategoryDao
import com.example.otchislenie.data.local.DAO.TransactionDao
import com.example.otchislenie.data.local.database.AppDatabase
import com.example.otchislenie.data.repository.FinanceRepositoryImpl
import com.example.otchislenie.domain.repository.FinanceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideTransactionDao(database: AppDatabase): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    fun provideCategoryDao(database: AppDatabase): CategoryDao {
        return database.categoryDao()
    }
    @Provides
    fun provideBudgetDao(database: AppDatabase):BudgetDao{
        return database.budgetDao()
    }

    @Provides
    @Singleton
    fun provideFinanceRepository(
        transactionDao: TransactionDao,
        categoryDao: CategoryDao,
        budgetDao: BudgetDao
    ): FinanceRepository {
        return FinanceRepositoryImpl(transactionDao, categoryDao,budgetDao)
    }
}