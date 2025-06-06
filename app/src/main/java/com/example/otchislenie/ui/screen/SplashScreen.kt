package com.example.otchislenie.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.otchislenie.R
import com.example.otchislenie.ui.theme.MatuleTheme

@Preview

@Composable
fun SplashScreen(){
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFE0FFFF)), verticalArrangement = Arrangement.Center) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(500.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Box(
                modifier = Modifier.size(250.dp)
                    .background(MatuleTheme.colors.Circle.copy(alpha = 0.6f), shape = RoundedCornerShape(100)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fabula),
                    contentDescription = null,
                    modifier = Modifier.size(235.dp)
                )
            }
            Text(text = "SPENDLY", style = MatuleTheme
                .typography
                .headingBold32,
                color = Color.Black,
                modifier = Modifier.padding(top = 50.dp))
        }
    }
}