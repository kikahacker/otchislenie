package com.example.otchislenie.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val iconRes: String, // или Int если используете ресурсы Android
    val color: Int, // в формате "#RRGGBB"
    val type: String, // "income" или "expense"
    val isUserCreated: Boolean = false // для разделения системных и пользовательских категорий
) {
    companion object {
        // Стандартные категории расходов
        fun defaultExpenseCategories(): List<CategoryEntity> {
            return listOf(
                CategoryEntity(
                    id = 1,
                    name = "Дом",
                    iconRes = "",
                    color = 0,
                    type = "expense"
                ),
                CategoryEntity(
                    id = 2,
                    name = "Спорт",
                    iconRes = "",
                    color = 0,
                    type = "expense"
                ),
                CategoryEntity(
                    id = 3,
                    name = "Развлечения",
                    iconRes = "",
                    color = 0,
                    type = "expense"
                ),
                CategoryEntity(
                    id = 4,
                    name = "Одежда",
                    iconRes = "",
                    color = 0,
                    type = "expense"
                ),
                CategoryEntity(
                    id = 5,
                    name = "Еда",
                    iconRes = "",
                    color = 0,
                    type = "expense"
                ),
                CategoryEntity(
                    id = 6,
                    name = "Связь",
                    iconRes = "",
                    color = 0,
                    type = "expense"
                ),
                CategoryEntity(
                    id = 7,
                    name = "Машина",
                    iconRes = "",
                    color = 0,
                    type = "expense"
                ),
                CategoryEntity(
                    id = 8,
                    name = "Кафе",
                    iconRes = "",
                    color = 0,
                    type = "expense"
                ),
                CategoryEntity(
                    id = 9,
                    name = "Подарки",
                    iconRes = "",
                    color = 0,
                    type = "expense"
                ),
                CategoryEntity(
                    id = 10,
                    name = "Такси",
                    iconRes = "",
                    color = 0,
                    type = "expense"
                ),
                CategoryEntity(
                    id = 11,
                    name = "Гигиенна",
                    iconRes = "",
                    color = 0,
                    type = "expense"
                ),
                CategoryEntity(
                    id = 12,
                    name = "Здоровье",
                    iconRes = "",
                    color = 0,
                    type = "expense"
                )
            )
        }

        // Стандартные категории доходов
        fun defaultIncomeCategories(): List<CategoryEntity> {
            return listOf(
                CategoryEntity(
                    id = 100,
                    name = "Зарплата",
                    iconRes = "ic_salary",
                    color = 0,
                    type = "income"
                ),
                CategoryEntity(
                    id = 101,
                    name = "Инвестиции",
                    iconRes = "ic_investment",
                    color = 0,
                    type = "income"
                )
            )
        }
    }
}