package com.example.ebot.actvities

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebot.R
import com.example.ebot.adapters.BankDetailsAdapter
import com.example.ebot.adapters.HubListAdapter
import com.example.ebot.models.HomeBannerList
import com.example.ebot.models.HubList
import com.example.ebot.models.Vehicle
import com.example.ebot.services.ServiceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BikeBookingDetails : AppCompatActivity() {
    private lateinit var ll_back: LinearLayout
    private lateinit var ll_NearBikes: LinearLayout
    private lateinit var ll_BookingSlot: LinearLayout
    private lateinit var ll_BookingSummary: LinearLayout
    private lateinit var ll_BookingStatus: LinearLayout
    private lateinit var ll_bottom: LinearLayout
    private lateinit var ll_view_all: LinearLayout
    private lateinit var view1: View
    private lateinit var view2: View
    private lateinit var view3: View
    private lateinit var swipeText: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var rc_nearBikes: RecyclerView
    private lateinit var progressbar: ProgressBar
    private lateinit var hubListAdapter: HubListAdapter
    private lateinit var dialog: ProgressDialog
    private var isHub: Boolean = false
    private var isSlot: Boolean = false
    private var isSummary: Boolean = false
    private var isStatus: Boolean = false
    private var hubList: ArrayList<HubList> = arrayListOf()
    private var selectedHub: HubList = HubList()
    private var screen: Int = 0
    private val YOUR_TOTAL_PROGRESS = 55
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bike_booking_details)
        updateXML()

    }

    private fun updateXML() {
        try {
            screen = 0
            ll_back = findViewById(R.id.ll_back)
            ll_view_all = findViewById(R.id.ll_view_all)
            ll_NearBikes = findViewById(R.id.ll_NearBikes)
            ll_BookingSlot = findViewById(R.id.ll_BookingSlot)
            ll_BookingSummary = findViewById(R.id.ll_BookingSummary)
            ll_BookingStatus = findViewById(R.id.ll_BookingStatus)
            ll_bottom = findViewById(R.id.ll_bottom)
            view1 = findViewById(R.id.view1)
            view2 = findViewById(R.id.view2)
            view3 = findViewById(R.id.view3)
            swipeText = findViewById(R.id.swipeText)
            seekBar = findViewById(R.id.seekBar)
            hubList = arrayListOf()
            seekBar.progress = 5
            setScreens(screen)
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (progress >= 20) {
                        swipeText.setBackgroundResource((R.drawable.roundedcorners))
                    } else {
                        swipeText.setBackgroundResource((R.drawable.button_bg))

                    }
                    if (progress >= YOUR_TOTAL_PROGRESS) {
                        if (!isHub) {
                            seekBar!!.progress = 5
                            isHub = true
                            screen += 1
                            setScreens(screen)
                        }

                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                    if ((seekBar?.progress ?: 0) < YOUR_TOTAL_PROGRESS) {
                        seekBar?.progress = 5
                    }
                }
            })
            ll_back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })

        } catch (e: Exception) {
            Log.e("BikeBookingDetails", e.message.toString())
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showHubList() {
        try {
            ll_NearBikes.removeAllViews()
            val inflater = LayoutInflater.from(this)
            val layout = inflater.inflate(R.layout.near_bikes_screen, ll_NearBikes, false)
            rc_nearBikes = layout.findViewById(R.id.rc_nearBikes)
            progressbar = layout.findViewById(R.id.progressbar)
            progressbar.visibility=View.VISIBLE
            rc_nearBikes.layoutManager = LinearLayoutManager(this)
            hubListAdapter = HubListAdapter(hubList, this@BikeBookingDetails) { selected ->
                selectedHub = selected
            }
            if (hubList.isEmpty()){
                progressbar.visibility=View.VISIBLE
            }else{
                progressbar.visibility=View.GONE
            }

            rc_nearBikes.adapter = hubListAdapter
            ll_NearBikes.addView(layout)

        } catch (e: Exception) {
            Log.e("BikeBookingDetails.showHubList: ", e.message.toString())
        }
    }

    private fun showBookingSlot() {
        try {
            ll_BookingSlot.removeAllViews()
            val inflater = LayoutInflater.from(this)
            val layout = inflater.inflate(R.layout.booking_slot_screen, ll_BookingSlot, false)
            ll_BookingSlot.addView(layout)

        } catch (e: Exception) {
            Log.e("BikeBookingDetails.showBookingSlot: ", e.message.toString())
        }
    }

    private fun showBookingSummary() {
        try {
            ll_BookingSummary.removeAllViews()
            val inflater = LayoutInflater.from(this)
            val layout = inflater.inflate(R.layout.booking_summary_screen, ll_BookingSummary, false)
            ll_BookingSummary.addView(layout)

        } catch (e: Exception) {
            Log.e("BikeBookingDetails.showBookingSummary: ", e.message.toString())
        }
    }

    private fun showBookingStatus() {
        try {
            ll_BookingStatus.removeAllViews()
            val inflater = LayoutInflater.from(this)
            val layout = inflater.inflate(R.layout.booking_summary_screen, ll_BookingStatus, false)
            ll_BookingStatus.addView(layout)

        } catch (e: Exception) {
            Log.e("BikeBookingDetails.showBookingStatus: ", e.message.toString())
        }
    }

    private fun setScreens(screenNo: Int) {
        seekBar.progress = 5
        if (screenNo == 0) {
            ll_bottom.visibility = View.VISIBLE
            swipeText.text = "Swipe to Continue"
            ll_NearBikes.visibility = View.VISIBLE
            ll_BookingSlot.visibility = View.GONE
            ll_BookingSummary.visibility = View.GONE
            ll_BookingStatus.visibility = View.GONE
            ll_NearBikes.removeAllViews()
            view1.setBackgroundResource(R.drawable.bar_active)
            view2.setBackgroundResource(R.drawable.bar_inactive)
            view3.setBackgroundResource(R.drawable.bar_inactive)
            showHubList()
            getHubList()

        } else if (screenNo == 1) {
            ll_bottom.visibility = View.VISIBLE
            swipeText.text = "Swipe to Continue"
            ll_NearBikes.visibility = View.GONE
            ll_BookingSlot.visibility = View.VISIBLE
            ll_BookingSummary.visibility = View.GONE
            ll_BookingStatus.visibility = View.GONE
            ll_BookingSlot.removeAllViews()
            view1.setBackgroundResource(R.drawable.bar_inactive)
            view2.setBackgroundResource(R.drawable.bar_active)
            view3.setBackgroundResource(R.drawable.bar_inactive)

        } else if (screenNo == 2) {
            ll_bottom.visibility = View.VISIBLE
            swipeText.text = "Swipe to Confirm Booking"
            ll_NearBikes.visibility = View.GONE
            ll_BookingSlot.visibility = View.GONE
            ll_BookingSummary.visibility = View.VISIBLE
            ll_BookingStatus.visibility = View.GONE
            ll_BookingSummary.removeAllViews()
            view1.setBackgroundResource(R.drawable.bar_inactive)
            view2.setBackgroundResource(R.drawable.bar_inactive)
            view3.setBackgroundResource(R.drawable.bar_active)


        } else if (screenNo == 3) {
            ll_bottom.visibility = View.GONE
            ll_view_all.visibility = View.GONE
            ll_NearBikes.visibility = View.GONE
            ll_BookingSlot.visibility = View.GONE
            ll_BookingSummary.visibility = View.GONE
            ll_BookingStatus.visibility = View.VISIBLE
            ll_BookingStatus.removeAllViews()


        }else{
            ll_view_all.visibility=View.VISIBLE
        }
    }

    private fun getHubList() {
        try {
            val dataManager = ServiceManager.getDataManager()

            val otpCallback = object : Callback<ArrayList<HubList>> {
                override fun onResponse(
                    call: Call<ArrayList<HubList>>,
                    response: Response<ArrayList<HubList>>
                ) {
                    progressbar.visibility=View.GONE

                    if (response.isSuccessful) {
                        val responseData = response.body()!!
                        if (responseData.isNotEmpty()) {
                            hubListAdapter.updateHubList(responseData)
                        }

                    } else {

                        // Handle error
                        println("Failed to hub_list. ${response.message()}")
                    }

                }


                override fun onFailure(call: Call<ArrayList<HubList>>, t: Throwable) {
                    progressbar.visibility=View.GONE

                    println("Failed to hub_list. ${t.message}")
                }
            }

            dataManager.getHubList(otpCallback)
        } catch (e: Exception) {
            progressbar.visibility=View.GONE
            e.printStackTrace()
        }
    }


    override fun onBackPressed() {

        if (screen > 0) {
            screen -= 1
            setScreens(screen)
        } else {
            super.onBackPressed()
        }

    }

    override fun onRestart() {
        super.onRestart()
        seekBar.progress = 5
        if (screen == 0) {
            isHub = false
        } else if (screen == 1) {
            isSlot = false
        }
    }
}