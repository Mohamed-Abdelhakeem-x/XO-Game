package com.example.xogame.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.xogame.databinding.FragmentScoreboardBinding

// Corrected import
import com.example.xogame.ui.GameViewModel

class ScoreboardFragment : Fragment() {
    private var _binding: FragmentScoreboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoreboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.totalPlayer1Score.observe(viewLifecycleOwner) { score ->
            binding.player1Score.text = "Player 1 (X): $score"
        }

        viewModel.totalPlayer2Score.observe(viewLifecycleOwner) { score ->
            binding.player2Score.text = "Player 2 (O): $score"
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
