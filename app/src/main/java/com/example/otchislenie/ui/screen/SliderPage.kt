package com.example.otchislenie.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.otchislenie.R
import com.example.otchislenie.data.local.store.DataStoreOnBoarding
import com.example.otchislenie.data.local.store.OnboardingPage
import com.example.otchislenie.ui.theme.MatuleTheme
import kotlinx.coroutines.launch

@Preview
@Composable
fun PreviewSliderPage(){
    val dataStore = DataStoreOnBoarding(LocalContext.current)
    MatuleTheme{
        SliderPage(dataStore)
    }
}
@Composable
fun SliderPage(dataStore: DataStoreOnBoarding){
    val colorlist = listOf(MatuleTheme.colors.BackSlider1, MatuleTheme.colors.BackSlider2)
    val pageState  = rememberPagerState( pageCount = {3})
    val coroutineScope = rememberCoroutineScope ()
    val pages = listOf(
        OnboardingPage(
            title = "Поздоровайтесь с вашим новым финансовым трекером",
            description = "Вы замечательны тем, что сделали первый шаг к улучшению контроля над своими деньгами и финансовыми целями.",
            image = R.drawable.__2025_05_30_152055_2_,
            imageSize = 500.dp
        ),
        OnboardingPage(
            title = "Контролируйте свои расходы и начинайте экономить",
            description = "Spendly поможет вам контролировать свои расходы, отслеживать их и в конечном итоге экономить больше денег.",
            image = R.drawable._1_,
            imageSize = 350.dp
        ),
        OnboardingPage(
            title = "Вместе мы достигнем ваших финансовых целей",
            description = "Если вы не планируете бюджет, то планируете неудачу. Spendly поможет вам контролировать расходы и достигать финансовых целей.",
            image = R.drawable.qwe123_1_,
            imageSize = 900.dp
        )
    )

    HorizontalPager(state = pageState) { page ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colorlist))
        ) {
            // Изображение (первый элемент)
            Box(
                modifier = Modifier
                    .height(500.dp) // Фиксированная высота для всех экранов
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = painterResource(pages[page].image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(pages[page].imageSize) // Разный размер изображения
                        .align(Alignment.Center)
                )
            }

            Text(
                text = pages[page].title,
                color = Color.White,
                style = MatuleTheme.typography.headingBold24.copy(fontSize = 20.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp) // Боковые отступы для текста
            )

            Spacer(modifier = Modifier.height(20.dp)) // Отступ между текстами

            // Второй текст
            Text(
                text = pages[page].description,
                color = Color.White.copy(alpha = 0.8f),
                style = MatuleTheme.typography.subTitleRegular16,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp) // Боковые отступы для текста
            )

            Spacer(modifier = Modifier.weight(1f)) // Гибкий отступ, чтобы кнопка прижалась к низу

            // Кнопка (последний элемент)
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (pageState.currentPage < pages.lastIndex) {
                            pageState.animateScrollToPage(pageState.currentPage + 1)
                        } else {
                            dataStore.setOnBoardingCompleted(true)
                            dataStore.setOnBoardingCompleted(false)
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = MatuleTheme.colors.colorForGradient2,
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .width(350.dp) // Ширина кнопки
                    .height(70.dp), // Высота кнопки
                shape = RoundedCornerShape(60.dp)
            ) {
                Text(
                    text = when(pageState.currentPage) {
                        0 -> "НАЧАТЬ"
                        1 -> "УДИВИТЕЛЬНЫЙ"
                        2 -> "Я ГОТОВ"
                        else -> ""
                    }
                )
            }

            Spacer(modifier = Modifier.height(40.dp)) // Отступ снизу (можно регулировать)
        }

    }
}

