package com.example.ebot.actvities

import CarouselAdapter
import android.os.Bundle
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bike_view_details)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        val images = listOf(R.drawable.bike1, R.drawable.bike2, R.drawable.bike3)

        viewPager.adapter = CarouselAdapter(images)

        // Optional: Enable infinite scrolling (pseudo-infinite)
        viewPager.offscreenPageLimit = 3


        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this@BikeViewDetailsActivity, R.color.secondary))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this@BikeViewDetailsActivity, R.color.secondary))
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this@BikeViewDetailsActivity, R.color.red))
            }

            override fun onTabReselected(tab: TabLayout.Tab) { }
        })


        // Link TabLayout (dots) with ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

    }
}