package com.example.xogame.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.xogame.ui.theme.Blue
import com.example.xogame.ui.theme.Red

@Composable
fun GameScreen(navController: NavController, innerPadding: PaddingValues, gameViewModel: GameViewModel = viewModel(), startPlayer: String = "X") {
    LaunchedEffect(Unit) {
        gameViewModel.setStartingPlayer(startPlayer)
    }

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
            Text(text = "Current Turn: ${gameViewModel.currentPlayer}", fontSize = 24.sp, color = Color.White)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Player 1 (X): ${gameViewModel.sessionPlayer1Score}", fontSize = 20.sp, color = Color.White)
                Text(text = "Player 2 (O): ${gameViewModel.sessionPlayer2Score}", fontSize = 20.sp, color = Color.White)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val thirdWidth = size.width / 3
                    val thirdHeight = size.height / 3
                    // Draw vertical lines
                    drawLine(
                        color = Color.White,
                        start = Offset(x = thirdWidth, y = 0f),
                        end = Offset(x = thirdWidth, y = size.height),
                        strokeWidth = 5f,
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = Color.White,
                        start = Offset(x = 2 * thirdWidth, y = 0f),
                        end = Offset(x = 2 * thirdWidth, y = size.height),
                        strokeWidth = 5f,
                        cap = StrokeCap.Round
                    )
                    // Draw horizontal lines
                    drawLine(
                        color = Color.White,
                        start = Offset(x = 0f, y = thirdHeight),
                        end = Offset(x = size.width, y = thirdHeight),
                        strokeWidth = 5f,
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = Color.White,
                        start = Offset(x = 0f, y = 2 * thirdHeight),
                        end = Offset(x = size.width, y = 2 * thirdHeight),
                        strokeWidth = 5f,
                        cap = StrokeCap.Round
                    )
                }
                Column(modifier = Modifier.fillMaxSize()) {
                    for (i in 0..2) {
                        Row(modifier = Modifier.weight(1f)) {
                            for (j in 0..2) {
                                val index = i * 3 + j
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxSize()
                                        .clickable { gameViewModel.onCellClicked(index) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = gameViewModel.board[index],
                                        fontSize = 48.sp,
                                        color = if (gameViewModel.board[index] == "X") Red else Blue
                                    )
                                }
                            }
                        }
                    }
                }
            }
            if (gameViewModel.winner.isNotEmpty()) {
                val message = when (gameViewModel.winner) {
                    "Tie" -> "It's a tie!"
                    else -> "Player ${gameViewModel.winner} wins!"
                }
                Text(text = message, fontSize = 24.sp, color = Color.White)
                Button(
                    onClick = { gameViewModel.resetGame() },
                    colors = ButtonDefaults.buttonColors(containerColor = Red)
                ) {
                    Text("Play Again")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { gameViewModel.newGame() },
                    colors = ButtonDefaults.buttonColors(containerColor = Red)
                ) {
                    Text("New Game")
                }
                Button(
                    onClick = { navController.navigate("menu") },
                    colors = ButtonDefaults.buttonColors(containerColor = Red)
                ) {
                    Text("Exit Game")
                }
            }
        }
    }
}
