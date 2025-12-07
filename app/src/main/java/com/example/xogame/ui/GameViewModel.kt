package com.example.xogame.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val _gameGrid = MutableLiveData(List(9) { "" })
    val gameGrid: LiveData<List<String>> = _gameGrid

    private val _turnPlayer = MutableLiveData("X")
    val turnPlayer: LiveData<String> = _turnPlayer

    private val _matchP1Score = MutableLiveData(0)
    val matchP1Score: LiveData<Int> = _matchP1Score

    private val _matchP2Score = MutableLiveData(0)
    val matchP2Score: LiveData<Int> = _matchP2Score

    private val _seriesP1Wins = MutableLiveData(0)
    val seriesP1Wins: LiveData<Int> = _seriesP1Wins

    private val _seriesP2Wins = MutableLiveData(0)
    val seriesP2Wins: LiveData<Int> = _seriesP2Wins

    private val _matchResult = MutableLiveData("")
    val matchResult: LiveData<String> = _matchResult

    private var lastMatchWinner: String? = null

    fun placeMarker(gridIndex: Int) {
        if (_gameGrid.value?.get(gridIndex).isNullOrEmpty() && _matchResult.value.isNullOrEmpty()) {
            val newGrid = _gameGrid.value!!.toMutableList()
            newGrid[gridIndex] = _turnPlayer.value!!
            _gameGrid.value = newGrid
            evaluateMatch()
            _turnPlayer.value = if (_turnPlayer.value == "X") "O" else "X"
        }
    }

    private fun evaluateMatch() {
        val winPatterns = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // rows
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // columns
            listOf(0, 4, 8), listOf(2, 4, 6) // diagonals
        )
        for (pattern in winPatterns) {
            val (pos1, pos2, pos3) = pattern
            if (_gameGrid.value!![pos1].isNotEmpty() && _gameGrid.value!![pos1] == _gameGrid.value!![pos2] && _gameGrid.value!![pos1] == _gameGrid.value!![pos3]) {
                _matchResult.value = _gameGrid.value!![pos1]
                lastMatchWinner = _matchResult.value
                if (_matchResult.value == "X") {
                    _matchP1Score.value = (_matchP1Score.value ?: 0) + 1
                } else {
                    _matchP2Score.value = (_matchP2Score.value ?: 0) + 1
                }
                return
            }
        }
        if (_gameGrid.value!!.all { it.isNotEmpty() }) {
            _matchResult.value = "Tie"
            lastMatchWinner = null
        }
    }

    fun startNextMatch() {
        _gameGrid.value = List(9) { "" }
        _matchResult.value = ""
        _turnPlayer.value = lastMatchWinner ?: "X"
    }

    fun resetSeries(){
        _seriesP1Wins.value = (_seriesP1Wins.value ?: 0) + (_matchP1Score.value ?: 0)
        _seriesP2Wins.value = (_seriesP2Wins.value ?: 0) + (_matchP2Score.value ?: 0)
        _matchP1Score.value = 0
        _matchP2Score.value = 0
        startNextMatch()
        lastMatchWinner = null
    }

    fun setFirstTurn(player: String) {
        _turnPlayer.value = player
        lastMatchWinner = null
    }
}
