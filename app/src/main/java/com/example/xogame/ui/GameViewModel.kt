package com.example.xogame.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val _gameState = MutableLiveData(List(9) { "" })
    val gameState: LiveData<List<String>> = _gameState

    private val _activePlayerSymbol = MutableLiveData("X")
    val activePlayerSymbol: LiveData<String> = _activePlayerSymbol

    private val _p1RoundScore = MutableLiveData(0)
    val p1RoundScore: LiveData<Int> = _p1RoundScore

    private val _p2RoundScore = MutableLiveData(0)
    val p2RoundScore: LiveData<Int> = _p2RoundScore

    private val _p1TotalWins = MutableLiveData(0)
    val p1TotalWins: LiveData<Int> = _p1TotalWins

    private val _p2TotalWins = MutableLiveData(0)
    val p2TotalWins: LiveData<Int> = _p2TotalWins

    private val _roundResult = MutableLiveData("")
    val roundResult: LiveData<String> = _roundResult

    private var previousRoundWinner: String? = null

    fun registerPlayerInput(gridPosition: Int) {
        if (_gameState.value?.get(gridPosition).isNullOrEmpty() && _roundResult.value.isNullOrEmpty()) {
            val newGameState = _gameState.value!!.toMutableList()
            newGameState[gridPosition] = _activePlayerSymbol.value!!
            _gameState.value = newGameState
            calculateRoundResult()
            _activePlayerSymbol.value = if (_activePlayerSymbol.value == "X") "O" else "X"
        }
    }

    private fun calculateRoundResult() {
        val winningPaths = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // rows
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // columns
            listOf(0, 4, 8), listOf(2, 4, 6) // diagonals
        )
        for (path in winningPaths) {
            val (a, b, c) = path
            if (_gameState.value!![a].isNotEmpty() && _gameState.value!![a] == _gameState.value!![b] && _gameState.value!![a] == _gameState.value!![c]) {
                _roundResult.value = _gameState.value!![a]
                previousRoundWinner = _roundResult.value
                if (_roundResult.value == "X") {
                    _p1RoundScore.value = (_p1RoundScore.value ?: 0) + 1
                } else {
                    _p2RoundScore.value = (_p2RoundScore.value ?: 0) + 1
                }
                return
            }
        }
        if (_gameState.value!!.all { it.isNotEmpty() }) {
            _roundResult.value = "Tie"
            previousRoundWinner = null
        }
    }

    fun advanceToNextRound() {
        _gameState.value = List(9) { "" }
        _roundResult.value = ""
        _activePlayerSymbol.value = previousRoundWinner ?: "X"
    }

    fun resetScoreboardAndStartNewGame(){
        _p1TotalWins.value = (_p1TotalWins.value ?: 0) + (_p1RoundScore.value ?: 0)
        _p2TotalWins.value = (_p2TotalWins.value ?: 0) + (_p2RoundScore.value ?: 0)
        _p1RoundScore.value = 0
        _p2RoundScore.value = 0
        advanceToNextRound()
        previousRoundWinner = null
    }

    fun setInitialPlayer(player: String) {
        _activePlayerSymbol.value = player
        previousRoundWinner = null
    }
}
