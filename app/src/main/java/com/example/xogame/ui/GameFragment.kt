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

        viewModel.assignStartingPlayer(args.startPlayer)

        viewModel.gridState.observe(viewLifecycleOwner) { grid ->
            updateGridUI(grid)
        }

        viewModel.roundScoreX.observe(viewLifecycleOwner) { score ->
            binding.player1Score.text = "Player 1 (X): $score"
        }

        viewModel.roundScoreO.observe(viewLifecycleOwner) { score ->
            binding.player2Score.text = "Player 2 (O): $score"
        }

        viewModel.activeTurn.observe(viewLifecycleOwner) { player ->
            binding.turnText.text = "Current Turn: $player"
        }

        viewModel.roundOutcome.observe(viewLifecycleOwner) { outcome ->
            if (outcome?.isNotEmpty() == true) {
                binding.winnerText.visibility = View.VISIBLE
                binding.playAgainButton.visibility = View.VISIBLE
                binding.winnerText.text = if (outcome == "Tie") "It's a tie!" else "Player $outcome wins!"
            } else {
                binding.winnerText.visibility = View.GONE
                binding.playAgainButton.visibility = View.GONE
            }
        }

        binding.playAgainButton.setOnClickListener {
            viewModel.initiateNextRound()
        }

        binding.newGameButton.setOnClickListener {
            viewModel.startNewGameSession()
        }

        binding.exitGameButton.setOnClickListener {
            findNavController().navigate(R.id.action_gameFragment_to_menuFragment)
        }

        val gridCells = listOf(
            binding.cell1, binding.cell2, binding.cell3,
            binding.cell4, binding.cell5, binding.cell6,
            binding.cell7, binding.cell8, binding.cell9
        )

        gridCells.forEachIndexed { index, button ->
            button.setOnClickListener {
                viewModel.handleCellSelection(index)
            }
        }
    }

    private fun updateGridUI(grid: List<String>) {
        val gridCells = listOf(
            binding.cell1, binding.cell2, binding.cell3,
            binding.cell4, binding.cell5, binding.cell6,
            binding.cell7, binding.cell8, binding.cell9
        )
        grid.forEachIndexed { index, cellValue ->
            val button = gridCells[index]
            button.text = cellValue
            when(cellValue){
                "X" -> button.setTextColor(ContextCompat.getColor(requireContext(), R.color.x_magenta))
                "O" -> button.setTextColor(ContextCompat.getColor(requireContext(), R.color.o_cyan))
                else -> button.setTextColor(ContextCompat.getColor(requireContext(), R.color.noir_background))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
