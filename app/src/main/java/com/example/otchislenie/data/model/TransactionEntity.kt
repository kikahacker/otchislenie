package com.example.otchislenie.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val categoryId: Long,
    val date: Long, // timestamp
    val note: String,
    val type: String // "income" or "expense"
)