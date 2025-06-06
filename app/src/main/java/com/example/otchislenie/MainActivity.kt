package com.example.otchislenie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.otchislenie.ui.screen.SplashScreen
import com.example.otchislenie.ui.theme.MatuleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatuleTheme {
                SplashScreen()
            }
        }
    }
}

