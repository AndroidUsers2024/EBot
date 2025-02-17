package com.example.ebot.actvities

import CarouselAdapter
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.ebot.R

class BikeViewDetailsActivity : AppCompatActivity() {
    private lateinit var dotsLayout: LinearLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var seekBar: SeekBar

    private val YOUR_TOTAL_PROGRESS = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bike_view_details)
         viewPager = findViewById(R.id.viewPager)
         dotsLayout = findViewById(R.id.dotsLayout)
        seekBar = findViewById(R.id.seekBar1)

        val images = listOf(R.drawable.bike1, R.drawable.bike2, R.drawable.bike3, R.drawable.bike1)
        val adapter = CarouselAdapter(images)
        viewPager.adapter = adapter
        setupDots(images.size)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateDots(position)
            }
        })

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

    private fun setupDots(count: Int) {
        dotsLayout.removeAllViews()
        val dots = Array(count) { ImageView(this) }
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(8, 0, 8, 0)
        }

        for (i in dots.indices) {
            dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bar_inactive))
            dots[i].layoutParams = layoutParams
            dotsLayout.addView(dots[i])
        }
        // Highlight the first dot
        if (dots.isNotEmpty()) {
            dots[0].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bar_active))
        }
    }
    private fun updateDots(position: Int) {
        for (i in 0 until dotsLayout.childCount) {
            val dot = dotsLayout.getChildAt(i) as ImageView
            dot.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    if (i == position) R.drawable.bar_active else R.drawable.bar_inactive
                )
            )
        }
    }
}