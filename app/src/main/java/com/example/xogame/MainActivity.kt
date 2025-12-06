package com.example.xogame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.xogame.ui.screens.GameScreen
import com.example.xogame.ui.screens.GameViewModel
import com.example.xogame.ui.screens.HowToPlayScreen
import com.example.xogame.ui.screens.MenuScreen
import com.example.xogame.ui.screens.PlayerSelectionScreen
import com.example.xogame.ui.screens.ScoreboardScreen
import com.example.xogame.ui.theme.XOGameTheme

class MainActivity : ComponentActivity() {
    private val gameViewModel by viewModels<GameViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XOGameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "menu") {
                        composable("menu") {
                            MenuScreen(navController = navController, innerPadding = innerPadding)
                        }
                        composable("player_selection") {
                            PlayerSelectionScreen(navController = navController, innerPadding = innerPadding)
                        }
                        composable("game/{startPlayer}") { backStackEntry ->
                            val startPlayer = backStackEntry.arguments?.getString("startPlayer") ?: "X"
                            GameScreen(
                                navController = navController, 
                                innerPadding = innerPadding, 
                                gameViewModel = gameViewModel,
                                startPlayer = startPlayer
                            )
                        }
                        composable("how_to_play") {
                            HowToPlayScreen(navController = navController, innerPadding = innerPadding)
                        }
                        composable("scoreboard") {
                            ScoreboardScreen(
                                navController = navController, 
                                innerPadding = innerPadding, 
                                gameViewModel = gameViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
