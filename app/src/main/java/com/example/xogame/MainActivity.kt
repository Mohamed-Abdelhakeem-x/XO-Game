package com.example.xogame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.xogame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // The NavHostFragment from the activity_main.xml layout handles all navigation.
        // No further setup is needed in the MainActivity.
    }
}
