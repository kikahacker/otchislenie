package com.example.otchislenie.domain.model

data class Transaction(
    val id: Long = 0,
    val amount: Double,
    var category: Category,
    val date: Long, // timestamp
    val note: String,
    val type: String // "income" или "expense"
)