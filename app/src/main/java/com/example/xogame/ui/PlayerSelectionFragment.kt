package com.example.xogame.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.xogame.R
import com.example.xogame.databinding.FragmentPlayerSelectionBinding

class PlayerSelectionFragment : Fragment() {
    private var _binding: FragmentPlayerSelectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.player1Button.setOnClickListener {
            val action = PlayerSelectionFragmentDirections.actionPlayerSelectionFragmentToGameFragment("X")
            findNavController().navigate(action)
        }

        binding.player2Button.setOnClickListener {
            val action = PlayerSelectionFragmentDirections.actionPlayerSelectionFragmentToGameFragment("O")
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
