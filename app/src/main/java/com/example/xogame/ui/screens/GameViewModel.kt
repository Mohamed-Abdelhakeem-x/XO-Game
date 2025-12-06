package com.example.xogame.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    var board by mutableStateOf(List(9) { "" })
        private set

    var currentPlayer by mutableStateOf("X")
        private set

    var sessionPlayer1Score by mutableStateOf(0)
        private set

    var sessionPlayer2Score by mutableStateOf(0)
        private set

    var totalPlayer1Score by mutableStateOf(0)
        private set

    var totalPlayer2Score by mutableStateOf(0)
        private set

    var winner by mutableStateOf("")
        private set

    private var lastWinner: String? = null

    fun onCellClicked(index: Int) {
        if (board[index].isEmpty() && winner.isEmpty()) {
            val newBoard = board.toMutableList()
            newBoard[index] = currentPlayer
            board = newBoard
            checkWinner()
            currentPlayer = if (currentPlayer == "X") "O" else "X"
        }
    }

    private fun checkWinner() {
        val lines = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // rows
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // columns
            listOf(0, 4, 8), listOf(2, 4, 6) // diagonals
        )
        for (line in lines) {
            val (a, b, c) = line
            if (board[a].isNotEmpty() && board[a] == board[b] && board[a] == board[c]) {
                winner = board[a]
                lastWinner = winner
                if (winner == "X") sessionPlayer1Score++ else sessionPlayer2Score++
                return
            }
        }
        if (board.all { it.isNotEmpty() }) {
            winner = "Tie"
            lastWinner = null
        }
    }

    fun resetGame() {
        board = List(9) { "" }
        winner = ""
        currentPlayer = lastWinner ?: "X"
    }

    fun newGame(){
        totalPlayer1Score += sessionPlayer1Score
        totalPlayer2Score += sessionPlayer2Score
        sessionPlayer1Score = 0
        sessionPlayer2Score = 0
        resetGame()
        lastWinner = null
    }

    fun setStartingPlayer(player: String) {
        currentPlayer = player
        lastWinner = null
    }
}
