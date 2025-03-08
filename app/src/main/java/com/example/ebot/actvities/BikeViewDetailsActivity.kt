package com.example.ebot.actvities

import CarouselAdapter
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.ebot.R
import com.example.ebot.common.Utils
import com.example.ebot.models.HomeBannerList
import com.example.ebot.models.Vehicle

class BikeViewDetailsActivity : AppCompatActivity() {
    private lateinit var dotsLayout: LinearLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var seekBar: SeekBar

    private lateinit var ll_back: LinearLayout
    private lateinit var tv_km: TextView
    private lateinit var tv_speed: TextView
    private lateinit var tv_battery_type: TextView
    private lateinit var starting_at: TextView
    private lateinit var tv_of_Battery_Type: TextView
    private lateinit var tv_of_charger_no: TextView
    private lateinit var tv_of_speed: TextView
    private lateinit var tv_of_milage: TextView
    private lateinit var tv_of_driver_license: TextView
    private lateinit var tv_of_Capacity: TextView
    private lateinit var tv_title: TextView
    private lateinit var swipeText: TextView
    private lateinit var tv_description_text: TextView
    private lateinit var tv_rsa_description: TextView
    private lateinit var tv_Note: TextView
    private lateinit var dialog: ProgressDialog
    private  var vehicleData: Vehicle=Vehicle()
    private var isMove:Boolean=false

    private val YOUR_TOTAL_PROGRESS = 55
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bike_view_details)
         viewPager = findViewById(R.id.viewPager)
         dotsLayout = findViewById(R.id.dotsLayout)
        seekBar = findViewById(R.id.seekBar1)
        swipeText = findViewById(R.id.swipeText)

        ll_back= findViewById(R.id.ll_back)
        tv_km= findViewById(R.id.tv_km)
        tv_speed= findViewById(R.id.tv_speed)
        tv_battery_type= findViewById(R.id.tv_battery_type)
        starting_at= findViewById(R.id.starting_at)
        tv_of_Battery_Type= findViewById(R.id.tv_of_Battery_Type)
        tv_of_charger_no= findViewById(R.id.tv_of_charger_no)
        tv_of_speed= findViewById(R.id.tv_of_speed)
        tv_of_milage= findViewById(R.id.tv_of_milage)
        tv_of_driver_license= findViewById(R.id.tv_of_driver_license)
        tv_of_Capacity= findViewById(R.id.tv_of_Capacity)
        tv_title= findViewById(R.id.tv_title)
        tv_description_text= findViewById(R.id.tv_description_text)
        tv_rsa_description= findViewById(R.id.tv_rsa_description)
        tv_Note= findViewById(R.id.tv_Note)

        vehicleData= intent.getParcelableExtra<Vehicle>("vehicle")!!
        val bikeImg=Utils.IMG_ROOT_URL+vehicleData.bike_image
        val imgs:ArrayList<HomeBannerList> = arrayListOf()
        val temp=HomeBannerList(image =bikeImg )
        imgs.add((temp))




        val images = listOf(R.drawable.bike1, R.drawable.bike2, R.drawable.bike3, R.drawable.bike1)
        val adapter = CarouselAdapter(images,imgs,this)
        viewPager.adapter = adapter
        if (vehicleData.bike_image.isNullOrEmpty()){
            setupDots(images.size)
            viewPager.isEnabled=true
            dotsLayout.visibility=View.VISIBLE
            setupDots(images.size)

        }else{
            if (imgs.size>1){
                viewPager.isEnabled=true
                dotsLayout.visibility=View.VISIBLE
                setupDots(images.size)
            }else{
                viewPager.isEnabled=false
                dotsLayout.visibility=View.GONE
            }

        }
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
//                Toast.makeText(applicationContext, "Seekbar progress: $progress", Toast.LENGTH_SHORT).show()
                if (progress >= 20) {
                    swipeText.setBackgroundResource((R.drawable.roundedcorners))
                }else{
                    swipeText.setBackgroundResource((R.drawable.button_bg))

                }
                // If progress reaches the total value, hide the SeekBar and show the circle
                if(progress >= YOUR_TOTAL_PROGRESS) {
                    if (!isMove) {
                        seekBar!!.progress = 5
                        isMove=true
                        val intent = Intent(this@BikeViewDetailsActivity, HubListScreen::class.java)
                        intent.putExtra("vehicleData", vehicleData)
                        startActivity(intent)

                    }


                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Toast when the user starts touching the SeekBar
//                Toast.makeText(applicationContext, "Seekbar touch started!", Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Toast when the user stops touching the SeekBar
//                Toast.makeText(applicationContext, "Seekbar touch stopped!", Toast.LENGTH_SHORT).show()

                // If the SeekBar's progress is less than the total, reset it to 0
                if (seekBar?.progress ?: 0 < YOUR_TOTAL_PROGRESS) {
                    seekBar?.progress = 5
                }
            }
        })
        ll_back.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })


        tv_km.text = vehicleData.range
        tv_speed.text = vehicleData.speed
        tv_battery_type.text = vehicleData.battery_type
        starting_at.text = " Starting at "+vehicleData.bike_price
        tv_of_Battery_Type.text = vehicleData.battery_type
        tv_of_charger_no.text = vehicleData.charges
        tv_of_speed.text = vehicleData.speed
        tv_of_milage.text = vehicleData.milage
        tv_of_driver_license.text = vehicleData.drivers_license
        tv_of_Capacity.text = vehicleData.load_capacity
        tv_title.text = vehicleData.bike_name
        tv_description_text.text = vehicleData.description
        tv_rsa_description.text = vehicleData.assistance
        tv_Note.text ="Note: "+ vehicleData.note
        swipeText.text="Submit to Swipe   "+vehicleData.bike_price
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

    override fun onRestart() {
        super.onRestart()
        seekBar.progress = 5
        isMove=false
    }


}