package com.example.otchislenie.domain.model

import java.time.LocalDate

data class FinanceState(
    val currentPeriod: PeriodType = PeriodType.DAY,
    val currentDate: LocalDate = LocalDate.now(),
    val expenses: List<com.example.otchislenie.domain.model.Transaction> = emptyList(),
    val incomes: List<com.example.otchislenie.domain.model.Transaction> = emptyList(),
    val balance: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null
)

enum class PeriodType {
    DAY, WEEK, MONTH, YEAR
}