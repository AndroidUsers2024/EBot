package com.example.ebot

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SeekBarTestActivity : AppCompatActivity() {

    private lateinit var seekBar: SeekBar
//    private lateinit var circle: View
    private val YOUR_TOTAL_PROGRESS = 100  // Replace with your desired total progress value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seek_bar_test)

        // Initialize SeekBar and circle View (Assuming these are in your layout)
        seekBar = findViewById(R.id.seekBar1)
//        circle = findViewById(R.id.circleView)
        seekBar.progress = 5
        // Set the SeekBar listener
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Show the progress as a Toast
                Toast.makeText(applicationContext, "Seekbar progress: $progress", Toast.LENGTH_SHORT).show()

                // If progress reaches the total value, hide the SeekBar and show the circle
                if (progress == YOUR_TOTAL_PROGRESS) {
                    seekBar?.visibility = View.GONE
//                    circle.visibility = View.VISIBLE
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Toast when the user starts touching the SeekBar
                Toast.makeText(applicationContext, "Seekbar touch started!", Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Toast when the user stops touching the SeekBar
                Toast.makeText(applicationContext, "Seekbar touch stopped!", Toast.LENGTH_SHORT).show()

                // If the SeekBar's progress is less than the total, reset it to 0
                if (seekBar?.progress ?: 0 < YOUR_TOTAL_PROGRESS) {
                    seekBar?.progress = 5
                }
            }
        })
    }
}