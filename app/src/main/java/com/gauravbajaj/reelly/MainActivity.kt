package com.gauravbajaj.reelly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.gauravbajaj.reelly.navigation.ReellyNavHost
import com.gauravbajaj.reelly.ui.theme.ReellyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReellyTheme {
                ReellyNavHost()
            }
        }
    }
}

@Composable
fun ReellyApp() {
    ReellyNavHost()
}

@Composable
fun ReellyPreview() {
    ReellyTheme {
        ReellyApp()
    }
}