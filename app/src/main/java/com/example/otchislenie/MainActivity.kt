package com.example.otchislenie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.otchislenie.data.local.database.AppDatabase
import com.example.otchislenie.data.repository.FinanceRepositoryImpl

import com.example.otchislenie.ui.screen.HomePageContent
import com.example.otchislenie.ui.theme.MatuleTheme
import com.example.otchislenie.ui.viewmodels.FinanceViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = AppDatabase.getInstance(this)
        val repository = FinanceRepositoryImpl(
            transactionDao = db.transactionDao(),
            categoryDao = db.categoryDao(),
            budgetDao = db.budgetDao()
        )
        val viewModel = FinanceViewModel(repository)
        setContent {
            MatuleTheme {
                HomePageContent(viewModel)
            }
        }
    }
}

