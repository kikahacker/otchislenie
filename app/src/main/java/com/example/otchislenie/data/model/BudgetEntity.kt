package com.example.otchislenie.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val categoryId: Long, // 0 для общего бюджета
    val amount: Double,
    val period: String, // "weekly", "monthly", "yearly", "custom"
    val startDate: Long, // timestamp
    val endDate: Long, // timestamp
    val currency: String,
    val isActive: Boolean = true
) {
    companion object {
        const val PERIOD_WEEKLY = "weekly"
        const val PERIOD_MONTHLY = "monthly"
        const val PERIOD_YEARLY = "yearly"
        const val PERIOD_CUSTOM = "custom"
    }
}