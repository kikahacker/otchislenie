package com.example.otchislenie.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.otchislenie.domain.model.Category
import com.example.otchislenie.domain.repository.FinanceRepository
import kotlinx.coroutines.flow.Flow

class AddTransactionViewModel(
    private val repository: FinanceRepository
) : ViewModel() {
    fun getCategoriesByType(type: String): Flow<List<Category>> {
        return repository.getCategoriesByType(type)
    }
}