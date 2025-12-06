package com.example.xogame.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.xogame.R
import com.example.xogame.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startGameButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_playerSelectionFragment)
        }

        binding.scoreboardButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_scoreboardFragment)
        }

        binding.howToPlayButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_howToPlayFragment)
        }

        binding.exitButton.setOnClickListener {
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
