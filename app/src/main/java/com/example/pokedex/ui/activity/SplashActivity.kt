package com.example.pokedex.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}