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
import com.example.ebot.actvities.BikeBookingDetails
import com.example.ebot.actvities.BikeViewDetailsActivity
import com.example.ebot.actvities.CameraActivity
import com.example.ebot.actvities.LoginActivity
import com.example.ebot.actvities.MainActivity
import com.example.ebot.common.Utils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
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
    private fun isLogin() {
       val user_id=Utils.getData(this,"user_id","")as String
        if (user_id.isNotEmpty()) {
            navigateToMenuScreen()
        } else {
            navigateToLoginScreen()
        }
    }
    private fun navigateToMenuScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToLoginScreen() {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}