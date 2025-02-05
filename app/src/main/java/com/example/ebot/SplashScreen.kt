package com.example.ebot

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var ll_main: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        ll_main = findViewById(R.id.ll_main)
        val zoom: Animation = AnimationUtils.loadAnimation(this, R.anim.zoom_animation)
        lifecycleScope.launch {
            delay(100)
            ll_main.startAnimation(zoom)
            delay(800)
            isLogin()
        }
    }

    private fun navigateToMenuScreen(id: String) {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToLoginScreen() {
        val intent = Intent(this,Sign_in::class.java)
        startActivity(intent)
        finish()
    }
}