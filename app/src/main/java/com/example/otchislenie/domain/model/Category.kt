package com.example.otchislenie.domain.model

data class Category(
    val id: Long = 0,
    val name: String,
    val iconRes: String,
    val color: String,
    val type: String, // "income" или "expense"
    val isUserCreated: Boolean = false
)