package com.example.xogame.ui.screens

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.xogame.ui.theme.Red

@Composable
fun MenuScreen(navController: NavController, innerPadding: PaddingValues) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { navController.navigate("player_selection") },
                colors = ButtonDefaults.buttonColors(containerColor = Red)
            ) {
                Text("Start game")
            }
            Button(
                onClick = { navController.navigate("scoreboard") },
                colors = ButtonDefaults.buttonColors(containerColor = Red)
            ) {
                Text("Scoreboard")
            }
            Button(
                onClick = { navController.navigate("how_to_play") },
                colors = ButtonDefaults.buttonColors(containerColor = Red)
            ) {
                Text("How to play")
            }
            val activity = (LocalContext.current as? Activity)
            Button(
                onClick = { activity?.finish() },
                colors = ButtonDefaults.buttonColors(containerColor = Red)
            ) {
                Text("Exit app")
            }
        }
    }
}
