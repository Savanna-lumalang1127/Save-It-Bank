package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import com.example.myapplication.navigation.AppRouting
import com.example.myapplication.ui.theme.SaveItDarkColorScheme
import com.example.myapplication.ui.theme.SaveItLightColorScheme

class MainActivity : ComponentActivity() {

    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val colors = if (isSystemInDarkTheme()) SaveItDarkColorScheme else SaveItLightColorScheme
            MaterialTheme(colorScheme = colors) {
                AppRouting(viewModel = viewModel)
            }
        }
    }
}