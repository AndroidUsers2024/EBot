package com.example.ebot.actvities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.ebot.R
import com.example.ebot.fragments.BikesFragment
import com.example.ebot.fragments.HomeFragment
import com.example.ebot.fragments.MyRidesFragment
import com.example.ebot.fragments.ProfileFragment
import com.example.ebot.fragments.WalletFragment

class MainActivity : AppCompatActivity() {
    private lateinit var lnr_home: LinearLayout
    private lateinit var vw_home: View
    private lateinit var ic_home: View
    private lateinit var tv_Home: TextView
    
    private lateinit var lnr_my_rides: LinearLayout
    private lateinit var vw_my_rides: View
    private lateinit var ic_my_rides: View
    private lateinit var tv_myRides: TextView

    private lateinit var lnr_bikes: LinearLayout
    private lateinit var vw_bikes: View
    private lateinit var lnr_wallet: LinearLayout
    private lateinit var ic_wallet: View
    private lateinit var vw_wallet: View
    private lateinit var tv_wallet: TextView
    
    private lateinit var lnr_profile: LinearLayout
    private lateinit var ic_profile: View
    private lateinit var vw_profile: View
    private lateinit var tv_profile: TextView

    private var  mobile:String?=""
    private var  user_id:String?=""
    private var  screenNo:Int?=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lnr_home = findViewById(R.id.lnr_home)
        ic_home = findViewById(R.id.ic_home)

        lnr_my_rides = findViewById(R.id.lnr_my_rides)
        ic_my_rides = findViewById(R.id.ic_my_rides)

        lnr_my_rides = findViewById(R.id.lnr_my_rides)
        ic_my_rides = findViewById(R.id.ic_my_rides)

        lnr_bikes = findViewById(R.id.lnr_bikes)

        lnr_wallet = findViewById(R.id.lnr_wallet)
        ic_wallet = findViewById(R.id.ic_wallet)

        lnr_profile = findViewById(R.id.lnr_profile)
        ic_profile = findViewById(R.id.ic_profile)



        vw_home = findViewById(R.id.vw_home)
        vw_my_rides = findViewById(R.id.vw_my_rides)
        vw_bikes = findViewById(R.id.vw_bikes)
        vw_wallet = findViewById(R.id.vw_wallet)
        vw_profile = findViewById(R.id.vw_profile)


        tv_Home = findViewById(R.id.tv_Home)
        tv_myRides = findViewById(R.id.tv_myRides)
        tv_wallet = findViewById(R.id.tv_wallet)
        tv_profile = findViewById(R.id.tv_profile)
        replaceFragment(HomeFragment())
        lnr_home.performClick()

        lnr_home.setOnClickListener {
            // deselect all bottom items
            // select Item
            selectBottomItems(0)
        }

        lnr_my_rides.setOnClickListener {

            selectBottomItems(1)
        }

        lnr_bikes.setOnClickListener {
            selectBottomItems(2)
        }

        lnr_wallet.setOnClickListener {

            selectBottomItems(3)
        }

        lnr_profile.setOnClickListener {
            selectBottomItems(4)
        }



    }

    private fun desSelectBottomItems() {
        val color_vw = ContextCompat.getColor(this, R.color.black)
        val color_ic = ContextCompat.getColorStateList(this, R.color.icons)
        val color_tx = ContextCompat.getColor(this, R.color.icons)



        vw_home.setBackgroundColor(color_vw)
        vw_my_rides.setBackgroundColor(color_vw)
        vw_bikes.setBackgroundColor(color_vw)
        vw_wallet.setBackgroundColor(color_vw)
        vw_profile.setBackgroundColor(color_vw)

        ic_home.backgroundTintList=color_ic
        ic_my_rides.backgroundTintList=color_ic
        ic_wallet.backgroundTintList=color_ic
        ic_profile.backgroundTintList=color_ic

        tv_Home.setTextColor(color_tx)
        tv_myRides.setTextColor(color_tx)
        tv_profile.setTextColor(color_tx)
        tv_wallet.setTextColor(color_tx)
    }
    private fun selectBottomItems(position: Int) {
        val color = ContextCompat.getColorStateList(this, R.color.primary)
        val textColor = ContextCompat.getColor(this, R.color.primary)
        desSelectBottomItems()

        when(position){
            0-> {
                screenNo=0
                vw_home.setBackgroundColor(textColor)
                ic_home.backgroundTintList = color
                tv_Home.setTextColor(textColor)
                replaceFragment(HomeFragment())

            }
            1->{
                screenNo=1
                ic_my_rides.backgroundTintList=color
                vw_my_rides.setBackgroundColor(textColor)
                tv_myRides.setTextColor(textColor)
                replaceFragment(MyRidesFragment())

            }
            2->{
                screenNo=2
                vw_bikes.setBackgroundColor(textColor)
                replaceFragment(BikesFragment())

            }
            3->{
                screenNo=3
                vw_wallet.setBackgroundColor(textColor)
                ic_wallet.backgroundTintList=color
                tv_wallet.setTextColor(textColor)
                replaceFragment(WalletFragment())

            }
            4->{
                screenNo=4
                vw_profile.setBackgroundColor(textColor)
                ic_profile.backgroundTintList=color
                tv_profile.setTextColor(textColor)
                replaceFragment(ProfileFragment())


            }
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer,fragment)
            .commit()
    }

    override fun onBackPressed() {

        if (screenNo==0){
            super.onBackPressed()
        }else{
            screenNo=screenNo!!-1
            selectBottomItems(0)

        }
    }

}