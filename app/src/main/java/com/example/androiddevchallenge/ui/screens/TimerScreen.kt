package com.example.androiddevchallenge.ui.screens

import android.os.CountDownTimer
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.DarkPrimary
import com.example.androiddevchallenge.ui.theme.DarkPrimaryLight
import com.example.androiddevchallenge.ui.theme.VoiletLight

var timer: CountDownTimer? = null

@Composable
fun TimerTV(){
    val millisLeft = TimerState.millisLeft.value.toLong()
    val secondsInMilli: Long = 1000

    val seconds = millisLeft/secondsInMilli

    val sec = (seconds % 60).toInt()
    var p2 = (seconds / 60).toInt()
    val p3 = p2 % 60
    p2 = p2 / 60

    Text(text = "$p3:$sec", style = MaterialTheme.typography.h1.copy(fontSize = 60.sp))
}

@Composable
fun TimerUI() {

    val isDark = ThemeState.darkModeState.value
    val percentState = TimerState.millisLeft.value
    val started = TimerState.started.value
    val totalTime = TimerState.totalTime

    val transition = updateTransition(targetState = percentState)

    val box1Height by transition.animateFloat {
        (TimerState.totalTime - percentState) + 0.1f
    }

    val box2Height by transition.animateFloat {
        percentState
    }

    if (timer == null) {
        timer = object : CountDownTimer(totalTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                TimerState.millisLeft.value = millisUntilFinished.toFloat()
            }

            override fun onFinish() {
                TimerState.millisLeft.value = 0.1f
                TimerState.started.value = false
            }
        }
    }

    Column {

        Box(
            modifier = Modifier
                .weight(box1Height, true)
                .fillMaxWidth()
                .background(if (isDark) DarkPrimary else Color.White)
        ) {
        }

        Box(
            modifier = Modifier
                .weight(box2Height, true)
                .fillMaxWidth()
                .background(if (isDark) DarkPrimaryLight else VoiletLight)
        ) {
        }
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .weight(2f, true)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center

        ) {
            TimerTV()
        }
        Box(
            modifier = Modifier
                .weight(1f, true)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center

        ) {
            FloatingActionButton(
                onClick = {
                    if (!started) {
                        TimerState.started.value = true
                        timer!!.start()
                    } else {
                        TimerState.millisLeft.value = totalTime.toFloat()
                        timer!!.cancel()
                        TimerState.started.value = false
                    }
                },
            ) {
                Text(
                    if (!started) "Start" else "Stop",
                    color = if (isDark) Color.Black else Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

}


