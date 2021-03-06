package com.example.androiddevchallenge.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


object AddTimerNumberState {
    var h1: MutableState<Int?> = mutableStateOf(null)
    var h2: MutableState<Int?> = mutableStateOf(null)
    var m1: MutableState<Int?> = mutableStateOf(null)
    var m2: MutableState<Int?> = mutableStateOf(null)
}

@Composable
fun AddTimerScreen() {

    val m1 = AddTimerNumberState.m1.value
    val m2 = AddTimerNumberState.m2.value
    val h1 = AddTimerNumberState.h1.value
    val h2 = AddTimerNumberState.h2.value

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(32.dp)
        ) {
            Row(
                modifier = Modifier
                    .weight(2f, true),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "${h1 ?: 0}", style = MaterialTheme.typography.h1)
                Text(text = "${h2 ?: 0}", style = MaterialTheme.typography.h1)
                Text(text = " m", style = MaterialTheme.typography.h3)
                Text(text = "${m1 ?: 0}", style = MaterialTheme.typography.h1)
                Text(text = "${m2 ?: 0}", style = MaterialTheme.typography.h1)
                Text(text = " s", style = MaterialTheme.typography.h3)
            }
            IconButton(
                onClick = {
                    deleteNumbers()
                },
                modifier = Modifier.weight(1f, true),
            ) {
                Icon(
                    Icons.Filled.Backspace, "cut",
                )
            }
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            NumberBox(text = 1, callBack = {
                addNumber(it)
            })
            NumberBox(text = 2, callBack = {
                addNumber(it)
            })
            NumberBox(text = 3, callBack = {
                addNumber(it)
            })
        }

        Spacer(Modifier.height(25.dp))

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            NumberBox(text = 4, callBack = {
                addNumber(it)
            })
            NumberBox(text = 5, callBack = {
                addNumber(it)
            })
            NumberBox(text = 6, callBack = {
                addNumber(it)
            })
        }
        Spacer(Modifier.height(25.dp))
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            NumberBox(text = 7, callBack = {
                addNumber(it)
            })
            NumberBox(text = 8, callBack = {
                addNumber(it)
            })
            NumberBox(text = 9, callBack = {
                addNumber(it)
            })
        }
        Spacer(Modifier.height(25.dp))
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {

            NumberBox(text = 0, callBack = {
                addNumber(it)
            })
        }
        Spacer(Modifier.height(50.dp))
    }
}

fun deleteNumbers() {
    AddTimerNumberState.m2.value =
        if(AddTimerNumberState.m1.value == 0 && AddTimerNumberState.h2.value == 0 && AddTimerNumberState.h1.value == 0) null else AddTimerNumberState.m1.value
    AddTimerNumberState.m1.value =
        if(AddTimerNumberState.h2.value == 0 && AddTimerNumberState.h1.value == 0) null else AddTimerNumberState.h2.value
    AddTimerNumberState.h2.value =
        if(AddTimerNumberState.h1.value == 0) null else AddTimerNumberState.h1.value
    AddTimerNumberState.h1.value = null
}

fun addNumber(number: Int) {
    if (AddTimerNumberState.m2.value == null) {
        AddTimerNumberState.m2.value = number
    } else if (AddTimerNumberState.m1.value == null) {
        AddTimerNumberState.m1.value = AddTimerNumberState.m2.value
        AddTimerNumberState.m2.value = number
    } else if (AddTimerNumberState.h2.value == null) {
        AddTimerNumberState.h2.value = AddTimerNumberState.m1.value
        AddTimerNumberState.m1.value = AddTimerNumberState.m2.value
        AddTimerNumberState.m2.value = number
    } else if (AddTimerNumberState.h1.value == null) {
        AddTimerNumberState.h1.value = AddTimerNumberState.h2.value
        AddTimerNumberState.h2.value = AddTimerNumberState.m1.value
        AddTimerNumberState.m1.value = AddTimerNumberState.m2.value
        AddTimerNumberState.m2.value = number
    }
}

@Composable
fun NumberBox(text: Int, callBack: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .width(50.dp)
            .fillMaxHeight(),
        contentAlignment = Alignment.Center,
    ) {
        FloatingActionButton(onClick = { callBack(text) }) {
            Text(
                text = "$text",
                style = MaterialTheme.typography.h3
            )
        }
    }
}