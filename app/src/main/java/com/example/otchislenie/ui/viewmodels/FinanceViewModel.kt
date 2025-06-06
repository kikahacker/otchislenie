package com.example.otchislenie.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otchislenie.domain.model.FinanceState
import com.example.otchislenie.domain.model.PeriodType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class FinanceViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val addTransactionUseCase: AddTransactionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FinanceState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun changePeriod(periodType: PeriodType) {
        _state.update { it.copy(currentPeriod = periodType) }
        loadData()
    }

    fun changeDate(forward: Boolean) {
        val currentDate = _state.value.currentDate
        val newDate = when (_state.value.currentPeriod) {
            PeriodType.DAY -> if (forward) currentDate.plusDays(1) else currentDate.minusDays(1)
            PeriodType.WEEK -> if (forward) currentDate.plusWeeks(1) else currentDate.minusWeeks(1)
            PeriodType.MONTH -> if (forward) currentDate.plusMonths(1) else currentDate.minusMonths(1)
            PeriodType.YEAR -> if (forward) currentDate.plusYears(1) else currentDate.minusYears(1)
        }
        _state.update { it.copy(currentDate = newDate) }
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val (startDate, endDate) = calculateDateRange()
                val transactions = getTransactionsUseCase(startDate, endDate)

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

    private fun calculateDateRange(): Pair<Long, Long> {
        return when (_state.value.currentPeriod) {
            PeriodType.DAY -> _state.value.currentDate.atStartOfDay().toEpochSecond() to
                    _state.value.currentDate.atTime(LocalTime.MAX).toEpochSecond()
            PeriodType.WEEK -> {
                val firstDay = _state.value.currentDate.with(TemporalAdjusters.previousOrSame(
                    DayOfWeek.MONDAY))
                val lastDay = firstDay.plusDays(6)
                firstDay.atStartOfDay().toEpochSecond() to lastDay.atTime(LocalTime.MAX).toEpochSecond()
            }
            PeriodType.MONTH -> {
                val firstDay = _state.value.currentDate.withDayOfMonth(1)
                val lastDay = _state.value.currentDate.with(TemporalAdjusters.lastDayOfMonth())
                firstDay.atStartOfDay().toEpochSecond() to lastDay.atTime(LocalTime.MAX).toEpochSecond()
            }
            PeriodType.YEAR -> {
                val firstDay = _state.value.currentDate.withDayOfYear(1)
                val lastDay = _state.value.currentDate.with(TemporalAdjusters.lastDayOfYear())
                firstDay.atStartOfDay().toEpochSecond() to lastDay.atTime(LocalTime.MAX).toEpochSecond()
            }
        }
    }
}