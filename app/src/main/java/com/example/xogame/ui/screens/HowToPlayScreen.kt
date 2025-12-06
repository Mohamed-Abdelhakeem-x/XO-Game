package com.example.xogame.ui.screens

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.xogame.ui.theme.Red

@Composable
fun HowToPlayScreen(navController: NavController, innerPadding: PaddingValues) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "How to Play",
                fontSize = 32.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "1. The game is played on a grid that's 3 squares by 3 squares.\n\n" +
                    "2. You are X, your friend is O. Players take turns putting their marks in empty squares.\n\n" +
                    "3. The first player to get 3 of her marks in a row (up, down, across, or diagonally) is the winner.\n\n" +
                    "4. When all 9 squares are full, the game is over. If no player has 3 marks in a row, the game ends in a tie.",
                fontSize = 18.sp,
                color = Color.White
            )
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(top = 32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Red)
            ) {
                Text("Back")
            }
        }
    }
}
