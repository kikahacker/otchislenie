package com.example.otchislenie.domain.model

data class Budget(
    val id: Long = 0,
    val categoryId: Long, // 0 для общего бюджета
    val amount: Double,
    val period: String, // "weekly", "monthly", "yearly", "custom"
    val startDate: Long, // timestamp
    val endDate: Long, // timestamp
    val currency: String,
    val isActive: Boolean = true
)