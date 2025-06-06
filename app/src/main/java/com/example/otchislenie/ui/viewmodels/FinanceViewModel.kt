package com.example.otchislenie.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otchislenie.domain.model.FinanceState
import com.example.otchislenie.domain.model.Transaction

import com.example.otchislenie.domain.repository.FinanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class FinanceViewModel @Inject constructor(
    private val repository: FinanceRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FinanceState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun changeDate(forward: Boolean) {
        val newDate = if (forward)
            _state.value.currentDate.plusDays(1)
        else
            _state.value.currentDate.minusDays(1)
        _state.update { it.copy(currentDate = newDate) }
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val startOfDay = _state.value.currentDate.atStartOfDay().toEpochSecond(ZoneOffset.ofHours(3))
                val endOfDay = _state.value.currentDate.atTime(LocalTime.MAX).toEpochSecond(
                    ZoneOffset.ofHours(3))

                val transactions = repository.getTransactionsByDate(startOfDay, endOfDay)
                    .first() // Берем первый результат Flow

                val expenses = transactions.filter { it.type == "expense" }
                val incomes = transactions.filter { it.type == "income" }
                val balance = incomes.sumOf { it.amount } - expenses.sumOf { it.amount }

                _state.update {
                    it.copy(
                        expenses = expenses,
                        incomes = incomes,
                        balance = balance,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            try {
                repository.addTransaction(transaction)
                loadData() // Обновляем данные после добавления
            } catch (e: Exception) {
                _state.update { it.copy(error = "Ошибка добавления: ${e.message}") }
            }
        }
    }
}

data class FinanceState(
    val currentDate: LocalDate = LocalDate.now(),
    val expenses: List<Transaction> = emptyList(),
    val incomes: List<Transaction> = emptyList(),
    val balance: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null
)