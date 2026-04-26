package com.example.jetcompose.untils

import androidx.compose.runtime.mutableStateOf

object AppGlobalState {
    var currentMode = mutableStateOf("mode_online")
    var currentLang = mutableStateOf("en")
    var currentScale = mutableStateOf(0)

    var currentFontScale = mutableStateOf(1f)
}