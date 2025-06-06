package com.example.otchislenie.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class BudgetWithCategory(
    @Embedded val budget: BudgetEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: CategoryEntity?
)