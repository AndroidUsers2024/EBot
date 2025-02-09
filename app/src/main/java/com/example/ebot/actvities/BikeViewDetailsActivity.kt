package com.example.ebot.actvities

import CarouselAdapter
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.ebot.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class BikeViewDetailsActivity : AppCompatActivity() {
    private lateinit var dotsLayout: LinearLayout
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bike_view_details)
         viewPager = findViewById(R.id.viewPager)
         dotsLayout = findViewById(R.id.dotsLayout)
        val images = listOf(R.drawable.bike1, R.drawable.bike2, R.drawable.bike3)
        val adapter = CarouselAdapter(images)
        viewPager.adapter = adapter
        setupDots(images.size)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateDots(position)
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
            dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_inactive))
            dots[i].layoutParams = layoutParams
            dotsLayout.addView(dots[i])
        }
        // Highlight the first dot
        if (dots.isNotEmpty()) {
            dots[0].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_active))
        }
    }
    private fun updateDots(position: Int) {
        for (i in 0 until dotsLayout.childCount) {
            val dot = dotsLayout.getChildAt(i) as ImageView
            dot.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    if (i == position) R.drawable.dot_active else R.drawable.dot_inactive
                )
            )
        }
    }
}