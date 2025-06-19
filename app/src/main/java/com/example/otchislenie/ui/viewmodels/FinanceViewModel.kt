package com.example.otchislenie.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otchislenie.domain.model.Category
import com.example.otchislenie.domain.model.FinanceState
import com.example.otchislenie.domain.model.Transaction

import com.example.otchislenie.domain.repository.FinanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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
                // Обновляем данные после добавления
                loadPieChartData()
                loadData() // Если нужно обновить другие данные
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }
    // Новые состояния для PieChart
    private val _pieChartData = MutableStateFlow<Map<String, Double>>(emptyMap())
    val pieChartData = _pieChartData.asStateFlow()

    private val _timeRange = MutableStateFlow(TimeRange.MONTH)
    val timeRange = _timeRange.asStateFlow()

    // Загрузка данных для PieChart
    fun loadPieChartData() {
        viewModelScope.launch {
            val (startDate, endDate) = getDateRange(_timeRange.value)

            repository.getExpensesByCategory(startDate, endDate)
                .collect { categories ->
                    val data = categories.associate {
                        it.categoryName to it.totalAmount
                    }
                    _pieChartData.value = data
                }
        }
    }
    fun clearDatabase() {
        viewModelScope.launch {
            try {
                repository.clearAllData()
                // Обновляем UI после очистки
                loadPieChartData()
                loadData()
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }
    fun getCategoriesByType(type: String): Flow<List<Category>> {
        return repository.getCategoriesByType(type)
    }
    // Изменение временного диапазона
    fun setTimeRange(range: TimeRange) {
        _timeRange.value = range
        loadPieChartData()
    }

    // Вычисление диапазона дат
    private fun getDateRange(range: TimeRange): Pair<Long, Long> {
        val now = System.currentTimeMillis()
        return when (range) {
            TimeRange.DAY -> {
                val start = LocalDate.now().atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000
                start to now
            }
            TimeRange.WEEK -> {
                val start = LocalDate.now().minusDays(7).atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000
                start to now
            }
            TimeRange.MONTH -> {
                val start = LocalDate.now().minusMonths(1).atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000
                start to now
            }
            TimeRange.YEAR -> {
                val start = LocalDate.now().minusYears(1).atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000
                start to now
            }
            TimeRange.ALL -> 0L to now
        }
    }

    enum class TimeRange {
        DAY, WEEK, MONTH, YEAR, ALL
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