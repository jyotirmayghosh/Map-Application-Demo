package com.jyotirmay.mapapplicationdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.jyotirmay.mapapplicationdemo.ui.navigation.MapNavHost
import com.jyotirmay.mapapplicationdemo.ui.theme.MapApplicationDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MapApplicationDemoTheme {
                MapNavHost(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
