package com.example.otchislenie.ui.screen

import AddExpenseDialog
import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.otchislenie.ui.viewmodels.FinanceViewModel.TimeRange
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.otchislenie.PieChart
import com.example.otchislenie.R
import com.example.otchislenie.domain.model.Category
import com.example.otchislenie.domain.model.Transaction
import com.example.otchislenie.ui.viewmodels.FinanceViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun HomePageContent(viewModel: FinanceViewModel) {
    val sizeIcons = 50.dp
    var showClearDialog by remember { mutableStateOf(false) }
    val pieChartData by viewModel.pieChartData.collectAsState()
    val timeRange by viewModel.timeRange.collectAsState()
    val onExpenseAdded: (Transaction) -> Unit = { transaction ->
        viewModel.addTransaction(transaction)
    }
    // Преобразуем данные для PieChart (категория -> сумма)
    val expensesData = pieChartData.mapValues { it.value.toInt() }

    // Цвета для категорий (можно брать из базы данных)
    val categoryColors = mapOf(
        "Дом" to Color(0xFF20B2AA),
        "Транспорт" to Color(0xFF008000),
        "Развлечения" to Color(0xFFFFA500),
        "Одежда" to Color(0xFF8A2BE2),
        "Кафе" to Color(0xFFFF69B4),
        "Связь" to Color.Gray,
        "Авто" to Color.DarkGray,
        "Здоровье" to Color(0xFF9ACD32) )

    Column(modifier = Modifier.fillMaxSize()) {
            // Верхний бар
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(color = Color(0xFF7AC793))
                    .statusBarsPadding()
                    .padding(top = 10.dp, start = 10.dp)
            ) {
                IconButton(onClick = {
                    showClearDialog = true
                }, modifier = Modifier
                    .fillMaxHeight()
                    .width(40.dp)) {
                    Icon(
                        painter = painterResource(R.drawable.free_icon_sort_descending_8189398),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp))
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(150.dp)
                        .padding(end = 20.dp, start = 20.dp, top = 15.dp)
                ) {
                    Text(
                        text = "Spendly",
                        fontFamily = FontFamily(Font(R.font.aguafina_script)),
                        fontWeight = FontWeight.Bold,
                        fontSize = 21.sp,
                    )
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(horizontal = 18.dp)
                        .fillMaxHeight()
                        .width(40.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.loupe),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(horizontal = 18.dp)
                        .fillMaxHeight()
                        .width(40.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.exchange),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(horizontal = 18.dp)
                        .fillMaxHeight()
                        .width(40.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.dots),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }

            // Выбор временного периода
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TimeRange.values().forEach { range ->
                    OutlinedButton(
                        onClick = { viewModel.setTimeRange(range) },
                        modifier = Modifier.padding(4.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (timeRange == range) Color(0xFF7AC793) else Color.Transparent,
                            contentColor = if (timeRange == range) Color.White else Color(0xFF7AC793)
                        ),
                        border = BorderStroke(1.dp, Color(0xFF7AC793))
                    ) {
                        Text(
                            text = when(range) {
                                TimeRange.DAY -> "День"
                                TimeRange.WEEK -> "Неделя"
                                TimeRange.MONTH -> "Месяц"
                                TimeRange.YEAR -> "Год"
                                TimeRange.ALL -> "Все время"
                            },
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Центральная часть с PieChart
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                if (expensesData.isNotEmpty()) {
                    PieChart(
                        data = expensesData,
                        radiusOuter = 120.dp,
                        chartBarWidth = 30.dp,
                        animDuration = 1000
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Нет данных за выбранный период",
                            color = Color.Gray,
                            modifier = Modifier.padding(16.dp)
                        )
                        Button(
                            onClick = { viewModel.loadPieChartData() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7AC793))
                        ) {
                            Text("Обновить")
                        }
                    }
                }
            }

            // Нижняя часть с кнопками категорий
            Column(modifier = Modifier.padding(bottom = 16.dp).heightIn(min =200.dp,max = 400.dp)) {
                // Первый ряд кнопок
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CategoryButton(sizeIcons, Color(0xFF20B2AA), R.drawable.free_icon_house_5687455,Category(id = 1, name = "Дом", iconRes = "", color = 0, type = "expense"),
                        onExpenseAdded)
                    CategoryButton(sizeIcons, Color(0xFF008000),
                        R.drawable.free_icon_dumbbell_4205515,
                        Category(id = 2, name = "Спорт", iconRes = "", color = 0, type = "expense"),
                        onExpenseAdded)
                    CategoryButton(sizeIcons, Color(0xFFFFA500),
                        R.drawable.free_icon_cocktail_259953,
                        Category(id = 3, name = "Развлечения", iconRes = "",color = 0, type = "expense"),onExpenseAdded)
                    CategoryButton(sizeIcons, Color(0xFF8A2BE2),
                        R.drawable.free_icon_clothes_864332,
                        Category(id = 4, name = "Одежда", iconRes = "",color = 0, type = "expense"),onExpenseAdded)
                }

                // Второй ряд кнопок
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CategoryButton(sizeIcons, Color(0xFFFF69B4),
                        R.drawable.free_icon_food_basket_18902103,
                        Category(id = 5, name = "Еда", iconRes = "",color = 0, type = "expense"),onExpenseAdded)
                    CategoryButton(sizeIcons, Color.Gray,
                        R.drawable.free_icon_telephone_159832,
                        Category(id = 6, name = "Связь", iconRes = "",color = 0, type = "expense"),onExpenseAdded)
                    CategoryButton(sizeIcons, Color.DarkGray,
                        R.drawable.free_icon_car_5670285,
                        Category(id = 7, name = "Машина", iconRes = "",color = 0, type = "expense"),onExpenseAdded)
                    CategoryButton(sizeIcons, Color(0xFF9ACD32),
                        R.drawable.free_icon_restaurant_562678, Category(id = 8, name = "Кафе", iconRes = "",color = 0, type = "expense"),onExpenseAdded)
                }

                // Третий ряд кнопок
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CategoryButton(sizeIcons, Color(0xFFFF4500), R.drawable.free_icon_gift_116392, Category(id = 9, name = "Подарки", iconRes = "",color = 0, type = "expense"),onExpenseAdded)
                    CategoryButton(sizeIcons, Color(0xFFFFD700), R.drawable.taxi, Category(id = 10, name = "Такси", iconRes = "",color = 0, type = "expense"),onExpenseAdded)
                    CategoryButton(sizeIcons, Color(0xFF32CD32), R.drawable.free_icon_personal_hygiene_5532356, Category(id = 11, name = "Гигиенна", iconRes = "",color = 0, type = "expense"),onExpenseAdded)
                    CategoryButton(sizeIcons, Color(0xFF2F4F4F), R.drawable.free_icon_pills_4417863, Category(id = 12, name = "Здоровье", iconRes = "",color = 0, type = "expense"),onExpenseAdded)
                }
            }
        }
    if (showClearDialog) {
        ClearDatabaseDialog(
            onConfirm = {
                viewModel.clearDatabase()
                showClearDialog = false
            },
            onDismiss = { showClearDialog = false }
        )
    }
}

@Composable
fun CategoryButton(
    size: Dp,
    color: Color,
    iconRes: Int,
    category: Category,
    onExpenseAdded: (Transaction) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        OutlinedButton(
            onClick = { showDialog = true },
            modifier = Modifier.size(size),
            shape = CircleShape,
            border = BorderStroke(1.dp, Color.Transparent),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = color)
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = category.name,
                modifier = Modifier.padding(5.dp)
            )
        }
        Text(
            text = category.name,
            fontSize = 12.sp,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(70.dp),
            textAlign = TextAlign.Center
        )
    }

    if (showDialog) {
        AddExpenseDialog(
            category = category,
            onDismiss = { showDialog = false },
            onSave = { amount, note ->
                val newTransaction = Transaction(
                    id = category.id,
                    amount = amount,
                    category = category.copy(),
                    date = System.currentTimeMillis(),
                    note = note,
                    type = "expense"
                )
                onExpenseAdded(newTransaction)
            }
        )
    }

}
