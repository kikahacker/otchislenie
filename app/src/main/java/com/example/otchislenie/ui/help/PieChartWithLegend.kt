package com.example.otchislenie.ui.help

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.otchislenie.PieChart
import com.example.otchislenie.R

@Composable
fun PieChartWithLegend(
    data: Map<String, Double>,
    modifier: Modifier = Modifier
) {
    val totalSum = data.values.sum()
    val sortedData = data.toList().sortedByDescending { it.second }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Круговой график
        PieChart(
            data = data.mapValues { it.value.toInt() },
            radiusOuter = 120.dp,
            chartBarWidth = 30.dp
        )

        // Легенда с процентами
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            sortedData.forEach { (name, value) ->
                val percentage = if (totalSum > 0) (value / totalSum * 100) else 0.0

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(getCategoryColor(name),
                                shape = CircleShape
                            ))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                text = "$name: ${"%.1f".format(percentage)}%",
                                fontSize = 14.sp,
                                modifier = Modifier.weight(1f)
                                )
                    Text(
                        text = "${value.toInt()} руб",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
fun getCategoryColor(name: String): Color {
    return when (name) {
        "Дом" -> Color(0xFF20B2AA)
        "Спорт" -> Color(0xFF008000)
        "Развлечения" -> Color(0xFFFFA500)
        "Одежда" -> Color(0xFF8A2BE2)
        "Еда" -> Color(0xFFFF69B4)
        "Связь" -> Color.Gray
        "Машина" -> Color.DarkGray
        "Кафе" -> Color(0xFF9ACD32)
        "Подарки" -> Color(0xFFFF4500)
        "Такси" -> Color(0xFFFFD700)
        "Гигиенна" -> Color(0xFF32CD32)
        "Здоровье" -> Color(0xFF2F4F4F)
        else -> Color(0xFF7AC793) // Цвет по умолчанию
    }
}

fun getCategoryIcon(name: String): Int {
    return when (name) {
        "Дом" -> R.drawable.free_icon_house_5687455
        "Спорт" -> R.drawable.free_icon_dumbbell_4205515
        "Развлечения" -> R.drawable.free_icon_cocktail_259953
        "Одежда" -> R.drawable.free_icon_clothes_864332
        "Еда" -> R.drawable.free_icon_food_basket_18902103
        "Связь" -> R.drawable.free_icon_telephone_159832
        "Машина" -> R.drawable.free_icon_car_5670285
        "Кафе" -> R.drawable.free_icon_restaurant_562678
        "Подарки" -> R.drawable.free_icon_gift_116392
        "Такси" -> R.drawable.taxi
        "Гигиенна" -> R.drawable.free_icon_personal_hygiene_5532356
        "Здоровье" -> R.drawable.free_icon_pills_4417863
        else -> R.drawable.ic_launcher_foreground // Иконка по умолчанию
    }
}