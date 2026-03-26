package com.example.mp_sfp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.mp_sfp.interfaces.AppNav
import com.example.mp_sfp.ui.theme.MP_SFPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MP_SFPTheme {
                Surface {
                    val navController = rememberNavController()
                    AppNav(navController)
                }
            }
        }
    }
}

