package com.example.otchislenie.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.example.otchislenie.domain.model.Category
import com.example.otchislenie.domain.model.FinanceState
import com.example.otchislenie.domain.repository.FinanceRepository
import com.example.otchislenie.ui.viewmodels.FinanceViewModel
import java.time.format.DateTimeFormatter

@Composable
fun FinanceScreen(repository: FinanceRepository) {
    val viewModel = remember { FinanceViewModel(repository) }
    val state by viewModel.state.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var transactionType by remember { mutableStateOf("expense") }

    Scaffold(
        topBar = { PeriodSelector(state, viewModel) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add transaction")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            FinanceContent(state)

            if (showAddDialog) {
                AddTransactionDialog(
                    repository = repository,
                    type = transactionType,
                    onDismiss = { showAddDialog = false },
                    onAdd = { transaction ->
                        viewModel.addTransaction(transaction)
                        showAddDialog = false
                    }
                )
            }
        }
    }
}

@Composable
private fun PeriodSelector(state: FinanceState, viewModel: FinanceViewModel) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.changeDate(false) }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Previous")
            }

            Text(
                text = state.currentDate.format(DateTimeFormatter.ofPattern("d MMMM, EEEE")),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(8.dp)
            )

            IconButton(onClick = { viewModel.changeDate(true) }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Next")
            }
        }
    }
}

@Composable
private fun FinanceContent(state: FinanceState) {
    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        return
    }

    if (state.error != null) {
        Text("Error: ${state.error}", color = MaterialTheme.colorScheme.error)
        return
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // Статистика по расходам
        Text("РАСХОД", style = MaterialTheme.typography.titleSmall)
        state.expenses.groupBy { it.category }.forEach { (category, transactions) ->
            val sum = transactions.sumOf { it.amount }
            CategoryItem(category, sum)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Статистика по доходам
        Text("ДОХОД", style = MaterialTheme.typography.titleSmall)
        state.incomes.groupBy { it.category }.forEach { (category, transactions) ->
            val sum = transactions.sumOf { it.amount }
            CategoryItem(category, sum)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Баланс
        Text(
            text = "Баланс ${state.balance.formatAsCurrency()}",
            style = MaterialTheme.typography.titleSmall,
            color = if (state.balance >= 0) Color.Green else Color.Red
        )
    }
}

@Composable
private fun CategoryItem(category: Category, amount: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = rememberVectorPainter(Icons.Default.Category),
            contentDescription = null,
            tint = Color(category.color)
        )

        Text(
            text = category.name,
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
        )

        Text(amount.formatAsCurrency())
    }
}

fun Double.formatAsCurrency(): String {
    return "%.2f ₽".format(this)
}