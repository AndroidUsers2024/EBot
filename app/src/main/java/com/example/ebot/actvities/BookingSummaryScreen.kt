package com.example.ebot.actvities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.ebot.R
import com.example.ebot.common.Utils
import com.example.ebot.models.BookVehicle
import com.example.ebot.models.BookingResponse
import com.example.ebot.models.HubList
import com.example.ebot.models.TimeSlot
import com.example.ebot.models.Vehicle
import com.example.ebot.services.ServiceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class BookingSummaryScreen : AppCompatActivity() {
    private lateinit var ll_back: LinearLayout
    private lateinit var ll_view_all: LinearLayout
    private lateinit var ll_BookingStatus: LinearLayout
    private lateinit var ll_bottom: LinearLayout
    private lateinit var vw_status: View
    private lateinit var tv_status: TextView
    private lateinit var tv_statusMsg: TextView
    private lateinit var tv_header: TextView
    private lateinit var tv_title: TextView
    private lateinit var tv_range: TextView
    private lateinit var tv_speed: TextView
    private lateinit var tv_battery_type: TextView
    private lateinit var tv_Name: TextView
    private lateinit var tv_distance: TextView
    private lateinit var tv_address: TextView
    private lateinit var tv_direction: TextView
    private lateinit var tv_date_time: TextView
    private lateinit var tv_paymentSummary: TextView
    private lateinit var tv_amountPerHour: TextView
    private lateinit var tv_totalAmount: TextView
    private lateinit var img_bike: ImageView
    private lateinit var btn_goBackHome: Button
    private lateinit var swipeText: TextView
    private lateinit var seekBar: SeekBar
    private var isMove: Boolean = false
    private lateinit var openDialog:ProgressDialog


    private var vehicleData: Vehicle? = null
    private var selectedHub: HubList? = null
    private var time: String? = ""
    private var date: String? = ""
    private val YOUR_TOTAL_PROGRESS = 55
    private var isBooked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booking_summary_screen)
        updateXML()
    }

    private fun updateXML() {
        try {
            ll_back = findViewById(R.id.ll_back)
            ll_view_all = findViewById(R.id.ll_view_all)
            ll_BookingStatus = findViewById(R.id.ll_BookingStatus)
            ll_bottom = findViewById(R.id.ll_bottom)
            vw_status = findViewById(R.id.vw_status)
            tv_status = findViewById(R.id.tv_status)
            tv_statusMsg = findViewById(R.id.tv_statusMsg)
            tv_header = findViewById(R.id.tv_header)
            tv_title = findViewById(R.id.tv_title)
            tv_range = findViewById(R.id.tv_range)
            tv_speed = findViewById(R.id.tv_speed)
            tv_battery_type = findViewById(R.id.tv_battery_type)
            tv_Name = findViewById(R.id.tv_Name)
            tv_distance = findViewById(R.id.tv_distance)
            tv_address = findViewById(R.id.tv_address)
            tv_direction = findViewById(R.id.tv_direction)
            tv_date_time = findViewById(R.id.tv_date_time)
            tv_paymentSummary = findViewById(R.id.tv_paymentSummary)
            tv_amountPerHour = findViewById(R.id.tv_amountPerHour)
            tv_totalAmount = findViewById(R.id.tv_totalAmount)
            img_bike = findViewById(R.id.img_bike)
            btn_goBackHome = findViewById(R.id.btn_goBackHome)
            swipeText = findViewById(R.id.swipeText)
            seekBar = findViewById(R.id.seekBar)
            swipeText.text = "Swipe to Confirm Booking"

            selectedHub = intent.getParcelableExtra<HubList>("selectedHub")
            vehicleData = intent.getParcelableExtra<Vehicle>("vehicleData")!!
            time = intent.getStringExtra("time")
            date = intent.getStringExtra("date")
            var dateStr=""
            try{
                val inputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH)
                val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val localDate = LocalDate.parse(date, inputFormatter)
                 dateStr = localDate.format(outputFormatter).toString()
            }catch (e:Exception){
                Log.e("dateStr",e.message.toString())
            }
            val userId=Utils.getData(this@BookingSummaryScreen,"user_id","").toString()
            if (selectedHub != null) {
                tv_Name.text = selectedHub!!.title
                tv_address.text = selectedHub!!.description
                tv_distance.text = ""
            }
            if (vehicleData != null) {
               try {
                    val bikeImg = Utils.IMG_ROOT_URL + vehicleData!!.bike_image
                    tv_distance.text = "(" + vehicleData!!.range + ")"
                    tv_speed.text = vehicleData!!.speed
                    tv_battery_type.text = vehicleData!!.battery_type
                    tv_title.text = vehicleData!!.bike_name
                    if (bikeImg.isNotEmpty()) {
                        bikeImg.let { url ->
                            Glide.with(this).load(url)
                                .into(img_bike)

                        }
                    }
                    tv_amountPerHour.text ="₹ "+ vehicleData!!.bike_price!!.filter { it.isDigit() }

                   println("tv_amountPerHour.text"+tv_amountPerHour.text.toString())
                }catch (e:Exception){
                   Log.e("vehicleData",e.message.toString())
               }
            }
            var totalAmount:Float=0f
                tv_date_time.text = "$date, $time"
                val times = time!!.trim().split("-")

                val hours = getHourNumber(times[1]) - getHourNumber(times[0]).toFloat()
                val perH = tv_amountPerHour.text.toString().trim().replace("₹ ", "").replace(" ","").trim()
                 totalAmount = hours * (perH.toFloat())
                tv_totalAmount.text = "₹ " + totalAmount

            showStatusScreen(isBooked)

            ll_back.setOnClickListener(View.OnClickListener {
                if (isBooked) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    onBackPressed()
                }
            })
            btn_goBackHome.setOnClickListener(View.OnClickListener {

                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()

            })
            seekBar.progress=5
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
                        if (!isMove) {
                            isMove = true
                            seekBar!!.progress = 5
                            val request=BookVehicle(vehicleData!!.id,selectedHub!!.id,dateStr,time,selectedHub!!.description,totalAmount.toString(),userId)
                            vehicleBooking(request)

                        }

                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    isMove = false
                    seekBar?.progress = 5
                    if ((seekBar?.progress ?: 0) < YOUR_TOTAL_PROGRESS) {
                        seekBar?.progress = 5
                    }
                }
            })


        } catch (e: Exception) {
            Log.e("BookingSummaryScreen.updateXML()", e.message.toString())
        }
    }

    fun getHourNumber(hour: String): Int {
        var res = 0
        if (hour.contains("AM")) {
            val h = (hour.replace("AM", ""))
            if (h.toInt() == 0) {
                res = 0
            }
            if (h.toInt() == 1) {
                res = 1
            }
            if (h.toInt() == 2) {
                res = 1
            }
            if (h.toInt() == 3) {
                res = 3
            }
            if (h.toInt() == 4) {
                res = 4
            }
            if (h.toInt() == 5) {
                res = 5
            }
            if (h.toInt() == 6) {
                res = 6
            }
            if (h.toInt() == 7) {
                res = 7
            }
            if (h.toInt() == 8) {
                res = 8
            }
            if (h.toInt() == 9) {
                res = 9
            }
            if (h.toInt() == 10) {
                res = 10
            }
            if (h.toInt() == 11) {
                res = 11
            }
            if (h.toInt() == 12) {
                res = 12
            }

        } else if (hour.contains("PM")) {
            val h = (hour.replace("PM", ""))
            if (h.toInt() == 1) {
                res = 13
            }

            if (h.toInt() == 2) {
                res = 14
            }
            if (h.toInt() == 3) {
                res = 15
            }
            if (h.toInt() == 4) {
                res = 16
            }
            if (h.toInt() == 5) {
                res = 17
            }
            if (h.toInt() == 6) {
                res = 18
            }
            if (h.toInt() == 7) {
                res = 19
            }
            if (h.toInt() == 8) {
                res = 20
            }
            if (h.toInt() == 9) {
                res = 21
            }
            if (h.toInt() == 10) {
                res = 22
            }
            if (h.toInt() == 11) {
                res = 23
            }
            if (h.toInt() == 12) {
                res = 14
            }

        }


        return res
    }

    private fun showStatusScreen(isShows:Boolean){
        try {
            if (isShows){
                ll_view_all.visibility=View.GONE
                ll_BookingStatus.visibility=View.VISIBLE
                btn_goBackHome.visibility=View.VISIBLE
                ll_bottom.visibility=View.GONE

            }else{
                ll_view_all.visibility=View.VISIBLE
                ll_BookingStatus.visibility=View.GONE
                btn_goBackHome.visibility=View.GONE
                ll_bottom.visibility=View.VISIBLE
            }

        }catch (e:Exception){
            Log.e("showStatusScreen",e.message.toString())
        }
    }
    private fun vehicleBooking(bookingData:BookVehicle){
        try {
            openDialog=Utils.openDialog(this@BookingSummaryScreen)
            val dataManager = ServiceManager.getDataManager()
            val otpCallback = object : Callback<BookingResponse> {
                override fun onResponse(
                    call: Call<BookingResponse>,
                    response: Response<BookingResponse>
                ) {
                    if (openDialog.isShowing){
                        openDialog.dismiss()
                    }
                    if (response.isSuccessful) {
                        val res = response.body()!!
                        if (res.status!!.toUpperCase().equals("SUCCESS")){
                            Toast.makeText(this@BookingSummaryScreen,res.message,Toast.LENGTH_SHORT).show()
                            isBooked=true
                            showStatusScreen(isBooked)
                        }else{
                            Toast.makeText(this@BookingSummaryScreen,res.message,Toast.LENGTH_SHORT).show()

                        }



                    } else {

                        println("Failed to TimeSlot. ${response.message()}")
                    }

                }


                override fun onFailure(call: Call<BookingResponse>, t: Throwable) {
                    if (openDialog.isShowing){
                        openDialog.dismiss()
                    }
                    println("Failed to TimeSlot. ${t.message}")
                }
            }

            dataManager.vehicleBooking(otpCallback, bookingData)
        } catch (e: Exception) {
            if (openDialog.isShowing){
                openDialog.dismiss()
            }
            Log.e("BikeBooking.getTimeSlot",e.message.toString())
        }
    }

}