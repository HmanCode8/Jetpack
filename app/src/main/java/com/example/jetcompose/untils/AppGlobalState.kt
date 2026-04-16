package com.example.jetcompose.untils

import androidx.compose.runtime.mutableStateOf

object AppGlobalState {
    var currentMode = mutableStateOf("mode_online")
}