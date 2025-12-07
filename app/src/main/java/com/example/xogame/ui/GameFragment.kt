package com.example.xogame.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.xogame.R
import com.example.xogame.databinding.FragmentGameBinding

// Corrected import
import com.example.xogame.ui.GameViewModel

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameViewModel by activityViewModels()
    private val args: GameFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Corrected the method name from setStartingPlayer to setFirstTurn
        viewModel.setFirstTurn(args.startPlayer)

        viewModel.gameGrid.observe(viewLifecycleOwner) { board ->
            updateBoard(board)
        }

        viewModel.matchP1Score.observe(viewLifecycleOwner) { score ->
            binding.player1Score.text = "Player 1 (X): $score"
        }

        viewModel.matchP2Score.observe(viewLifecycleOwner) { score ->
            binding.player2Score.text = "Player 2 (O): $score"
        }

        viewModel.turnPlayer.observe(viewLifecycleOwner) { player ->
            binding.turnText.text = "Current Turn: $player"
        }

        viewModel.matchResult.observe(viewLifecycleOwner) { winner ->
            if (winner?.isNotEmpty() == true) {
                binding.winnerText.visibility = View.VISIBLE
                binding.playAgainButton.visibility = View.VISIBLE
                binding.winnerText.text = if (winner == "Tie") "It's a tie!" else "Player $winner wins!"
            } else {
                binding.winnerText.visibility = View.GONE
                binding.playAgainButton.visibility = View.GONE
            }
        }

        binding.playAgainButton.setOnClickListener {
            viewModel.startNextMatch()
        }

        binding.newGameButton.setOnClickListener {
            viewModel.resetSeries()
        }

        binding.exitGameButton.setOnClickListener {
            findNavController().navigate(R.id.action_gameFragment_to_menuFragment)
        }

        val boardButtons = listOf(
            binding.cell1, binding.cell2, binding.cell3,
            binding.cell4, binding.cell5, binding.cell6,
            binding.cell7, binding.cell8, binding.cell9
        )

        boardButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                viewModel.placeMarker(index)
            }
        }
    }

    private fun updateBoard(board: List<String>) {
        val boardButtons = listOf(
            binding.cell1, binding.cell2, binding.cell3,
            binding.cell4, binding.cell5, binding.cell6,
            binding.cell7, binding.cell8, binding.cell9
        )
        board.forEachIndexed { index, cellValue ->
            val button = boardButtons[index]
            button.text = cellValue
            when(cellValue){
                "X" -> button.setTextColor(ContextCompat.getColor(requireContext(), R.color.x_marker_gold))
                "O" -> button.setTextColor(ContextCompat.getColor(requireContext(), R.color.o_marker_silver))
                else -> button.setTextColor(ContextCompat.getColor(requireContext(), R.color.forest_dark_green_bg))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
