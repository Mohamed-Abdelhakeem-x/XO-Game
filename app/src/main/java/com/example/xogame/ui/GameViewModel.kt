package com.example.xogame.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val _board = MutableLiveData(List(9) { "" })
    val board: LiveData<List<String>> = _board

    private val _currentPlayer = MutableLiveData("X")
    val currentPlayer: LiveData<String> = _currentPlayer

    private val _sessionPlayer1Score = MutableLiveData(0)
    val sessionPlayer1Score: LiveData<Int> = _sessionPlayer1Score

    private val _sessionPlayer2Score = MutableLiveData(0)
    val sessionPlayer2Score: LiveData<Int> = _sessionPlayer2Score

    private val _totalPlayer1Score = MutableLiveData(0)
    val totalPlayer1Score: LiveData<Int> = _totalPlayer1Score

    private val _totalPlayer2Score = MutableLiveData(0)
    val totalPlayer2Score: LiveData<Int> = _totalPlayer2Score

    private val _winner = MutableLiveData("")
    val winner: LiveData<String> = _winner

    private var lastWinner: String? = null

    fun onCellClicked(index: Int) {
        if (_board.value?.get(index).isNullOrEmpty() && _winner.value.isNullOrEmpty()) {
            val newBoard = _board.value!!.toMutableList()
            newBoard[index] = _currentPlayer.value!!
            _board.value = newBoard
            checkWinner()
            _currentPlayer.value = if (_currentPlayer.value == "X") "O" else "X"
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
            if (_board.value!![a].isNotEmpty() && _board.value!![a] == _board.value!![b] && _board.value!![a] == _board.value!![c]) {
                _winner.value = _board.value!![a]
                lastWinner = _winner.value
                if (_winner.value == "X") {
                    _sessionPlayer1Score.value = (_sessionPlayer1Score.value ?: 0) + 1
                } else {
                    _sessionPlayer2Score.value = (_sessionPlayer2Score.value ?: 0) + 1
                }
                return
            }
        }
        if (_board.value!!.all { it.isNotEmpty() }) {
            _winner.value = "Tie"
            lastWinner = null
        }
    }

    fun resetGame() {
        _board.value = List(9) { "" }
        _winner.value = ""
        _currentPlayer.value = lastWinner ?: "X"
    }

    fun newGame(){
        _totalPlayer1Score.value = (_totalPlayer1Score.value ?: 0) + (_sessionPlayer1Score.value ?: 0)
        _totalPlayer2Score.value = (_totalPlayer2Score.value ?: 0) + (_sessionPlayer2Score.value ?: 0)
        _sessionPlayer1Score.value = 0
        _sessionPlayer2Score.value = 0
        resetGame()
        lastWinner = null
    }

    fun setStartingPlayer(player: String) {
        _currentPlayer.value = player
        lastWinner = null
    }
}
