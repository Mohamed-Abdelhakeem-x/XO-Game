package com.example.xogame.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val _gridState = MutableLiveData(List(9) { "" })
    val gridState: LiveData<List<String>> = _gridState

    private val _activeTurn = MutableLiveData("X")
    val activeTurn: LiveData<String> = _activeTurn

    private val _roundScoreX = MutableLiveData(0)
    val roundScoreX: LiveData<Int> = _roundScoreX

    private val _roundScoreO = MutableLiveData(0)
    val roundScoreO: LiveData<Int> = _roundScoreO

    private val _totalScoreX = MutableLiveData(0)
    val totalScoreX: LiveData<Int> = _totalScoreX

    private val _totalScoreO = MutableLiveData(0)
    val totalScoreO: LiveData<Int> = _totalScoreO

    private val _roundOutcome = MutableLiveData("")
    val roundOutcome: LiveData<String> = _roundOutcome

    private var previousVictor: String? = null

    fun handleCellSelection(selectedIndex: Int) {
        if (_gridState.value?.get(selectedIndex).isNullOrEmpty() && _roundOutcome.value.isNullOrEmpty()) {
            val updatedGrid = _gridState.value!!.toMutableList()
            updatedGrid[selectedIndex] = _activeTurn.value!!
            _gridState.value = updatedGrid
            evaluateBoardState()
            _activeTurn.value = if (_activeTurn.value == "X") "O" else "X"
        }
    }

    private fun evaluateBoardState() {
        val victoryPaths = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // rows
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // columns
            listOf(0, 4, 8), listOf(2, 4, 6) // diagonals
        )
        for (path in victoryPaths) {
            val (a, b, c) = path
            if (_gridState.value!![a].isNotEmpty() && _gridState.value!![a] == _gridState.value!![b] && _gridState.value!![a] == _gridState.value!![c]) {
                _roundOutcome.value = _gridState.value!![a]
                previousVictor = _roundOutcome.value
                if (_roundOutcome.value == "X") {
                    _roundScoreX.value = (_roundScoreX.value ?: 0) + 1
                } else {
                    _roundScoreO.value = (_roundScoreO.value ?: 0) + 1
                }
                return
            }
        }
        if (_gridState.value!!.all { it.isNotEmpty() }) {
            _roundOutcome.value = "Tie"
            previousVictor = null
        }
    }

    fun initiateNextRound() {
        _gridState.value = List(9) { "" }
        _roundOutcome.value = ""
        _activeTurn.value = previousVictor ?: "X"
    }

    fun startNewGameSession(){
        _totalScoreX.value = (_totalScoreX.value ?: 0) + (_roundScoreX.value ?: 0)
        _totalScoreO.value = (_totalScoreO.value ?: 0) + (_roundScoreO.value ?: 0)
        _roundScoreX.value = 0
        _roundScoreO.value = 0
        initiateNextRound()
        previousVictor = null
    }

    fun assignStartingPlayer(player: String) {
        _activeTurn.value = player
        previousVictor = null
    }
}
