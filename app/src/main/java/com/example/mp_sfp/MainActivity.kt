package com.example.mp_sfp

import com.example.mp_sfp.ui.theme.MP_SFPTheme
import com.example.mp_sfp.interfaces.AppNav

import androidx.navigation.compose.rememberNavController

import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

import androidx.compose.material3.Surface

import android.os.Bundle


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

