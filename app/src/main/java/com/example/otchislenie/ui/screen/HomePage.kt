package com.example.otchislenie.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.otchislenie.domain.model.Category
import com.example.otchislenie.domain.model.FinanceState
import com.example.otchislenie.domain.model.PeriodType
import com.example.otchislenie.ui.theme.MatuleTheme
import com.example.otchislenie.ui.theme.matuleFontFamily
import com.example.otchislenie.ui.viewmodels.FinanceViewModel
import org.tensorflow.lite.support.label.Category
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.layout.BoxScopeInstance as BoxScopeInstance1

@Composable
fun FinanceScreen(viewModel: FinanceViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var transactionType by remember { mutableStateOf("expense") }

    Scaffold (
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
                text = when (state.currentPeriod) {
                    PeriodType.DAY -> state.currentDate.format(DateTimeFormatter.ofPattern("d MMMM, EEEE"))
                    PeriodType.WEEK -> "Неделя ${state.currentDate.format(DateTimeFormatter.ofPattern("d MMM"))}"
                    PeriodType.MONTH -> state.currentDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"))
                    PeriodType.YEAR -> state.currentDate.format(DateTimeFormatter.ofPattern("yyyy"))
                },
                style = MatuleTheme.typography.bodyRegular16,
                modifier = Modifier.padding(8.dp)
            )

            IconButton(onClick = { viewModel.changeDate(true) }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Next")
            }
        }

        TabRow(
            selectedTabIndex = state.currentPeriod.ordinal,

        ) {
            listOf("День", "Неделя", "Месяц", "Год").forEachIndexed { index, title ->
                Tab(
                    selected = state.currentPeriod.ordinal == index,
                    onClick = { viewModel.changePeriod(PeriodType.values()[index]) },
                    text = { Text(title) }
                )
            }
        }
    }
}

@Composable
private fun FinanceContent(state: FinanceState) {
    if (state.isLoading) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        return
    }

    if (state.error != null) {
        Text("Error: ${state.error}", color = Color.Red)
        return
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // Статистика по расходам
        Text("РАСХОД", style = MaterialTheme.typography.subtitle1)
        state.expenses.groupBy { it.category }.forEach { (category, transactions) ->
            val sum = transactions.sumOf { it.amount }
            val percentage = (sum / state.expenses.sumOf { it.amount } * 100).toInt()

            CategoryItem(category, sum, percentage)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Статистика по доходам
        Text("ДОХОД", style = MaterialTheme.typography.subtitle1)
        state.incomes.groupBy { it.category }.forEach { (category, transactions) ->
            val sum = transactions.sumOf { it.amount }
            val percentage = (sum / state.incomes.sumOf { it.amount } * 100).toInt()

            CategoryItem(category, sum, percentage)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Баланс
        Text(
            text = "Баланс ${state.balance.formatAsCurrency()}",
            style = MaterialTheme.typography.h6,
            color = if (state.balance >= 0) Color.Green else Color.Red
        )
    }
}

@Composable
private fun CategoryItem(category: Category, amount: Double, percentage: Int) {
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

        Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
            Text(category.name)
            LinearProgressIndicator(
                progress = percentage / 100f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = Color(category.color)
            )
        }

        Text(amount.formatAsCurrency())
        Text("$percentage%", modifier = Modifier.width(40.dp))
    }
}

fun Double.formatAsCurrency(): String {
    return "%.2f ₽".format(this)
}