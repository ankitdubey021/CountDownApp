/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.screens

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlarmAdd
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.screens.TimerState.totalTime
import com.example.androiddevchallenge.ui.theme.DarkPrimary
import com.example.androiddevchallenge.ui.theme.DarkPrimaryLight
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.Voilet
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@ExperimentalMaterialApi
@Composable
fun MyApp() {

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    val coroutineScope = rememberCoroutineScope()

    val isDark = ThemeState.darkModeState.value

    Surface(color = MaterialTheme.colors.background) {
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "CountDown Timer",style = MaterialTheme.typography.h3)
                    },
                    backgroundColor = if (isDark) DarkPrimary else Voilet,
                    actions = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                } else {
                                    bottomSheetScaffoldState.bottomSheetState.collapse()
                                }
                            }
                        }) {
                            Icon(Icons.Filled.AlarmAdd, "time")
                        }
                        IconButton(onClick = {
                            toggleDarkMode(isDark)
                        }) {
                            Icon(if (isDark) Icons.Filled.LightMode else Icons.Filled.DarkMode, "Menu")
                        }
                    },
                )
            },
            sheetContent = {
                AddTimerScreen(bottomSheetCloseCallback = {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                })
            },
            content={
                Box (
                    Modifier
                        .background(Color.Yellow)
                        .fillMaxWidth()
                        .fillMaxHeight()
                ){
                    TimerUI()
                }
            },
            sheetPeekHeight = 0.dp
        )
    }
}

private fun toggleDarkMode(isDark: Boolean) {
    val uiMode = when (isDark) {
        true -> AppCompatDelegate.MODE_NIGHT_NO
        false -> AppCompatDelegate.MODE_NIGHT_YES
    }
    AppCompatDelegate.setDefaultNightMode(uiMode)
    ThemeState.darkModeState.value = !isDark
}

object ThemeState {
    var darkModeState: MutableState<Boolean> = mutableStateOf(false)
}

object TimerState{
    var totalTime = 60000L
    var started : MutableState<Boolean> = mutableStateOf(false)
    var millisLeft : MutableState<Float> = mutableStateOf(totalTime.toFloat())
}


@ExperimentalMaterialApi
@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@ExperimentalMaterialApi
@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
