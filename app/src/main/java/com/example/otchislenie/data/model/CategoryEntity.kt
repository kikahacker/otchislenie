package com.example.otchislenie.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val iconRes: String, // или Int если используете ресурсы Android
    val color: String, // в формате "#RRGGBB"
    val type: String, // "income" или "expense"
    val isUserCreated: Boolean = false // для разделения системных и пользовательских категорий
) {
    companion object {
        // Стандартные категории расходов
        fun defaultExpenseCategories(): List<CategoryEntity> {
            return listOf(
                CategoryEntity(
                    id = 1,
                    name = "Еда",
                    iconRes = "ic_food",
                    color = "#FF9800",
                    type = "expense"
                ),
                CategoryEntity(
                    id = 2,
                    name = "Транспорт",
                    iconRes = "ic_transport",
                    color = "#2196F3",
                    type = "expense"
                ),
                // Другие категории...
            )
        }

        // Стандартные категории доходов
        fun defaultIncomeCategories(): List<CategoryEntity> {
            return listOf(
                CategoryEntity(
                    id = 100,
                    name = "Зарплата",
                    iconRes = "ic_salary",
                    color = "#4CAF50",
                    type = "income"
                ),
                CategoryEntity(
                    id = 101,
                    name = "Инвестиции",
                    iconRes = "ic_investment",
                    color = "#9C27B0",
                    type = "income"
                )
            )
        }
    }
}