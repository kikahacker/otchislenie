package com.example.otchislenie.domain.model

data class Category(
    val id: Long = 0,
    val name: String,
    val iconRes: String,
    val color: Int,
    val type: String, // "income" или "expense"
    val isUserCreated: Boolean = false
) {
    companion object {
        fun default(): Category {
            return Category(
                id = 0,
                name = "Другое",
                color = 0,
                iconRes = "",// Серый цвет
                type = "expense")
        }
    }
}