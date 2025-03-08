package com.example.ebot.actvities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebot.R
import com.example.ebot.adapters.CalendarAdapter
import com.example.ebot.adapters.TimeSlotAdapter
import java.util.*
import com.example.ebot.common.Utils
import com.example.ebot.models.HubList
import com.example.ebot.models.MainResponse
import com.example.ebot.models.TimeSlot
import com.example.ebot.models.Vehicle
import com.example.ebot.services.ServiceManager
import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class BikeBookingSlotScreen : AppCompatActivity() {
    private lateinit var ll_back: LinearLayout
    private lateinit var tv_Name: TextView
    private lateinit var tv_distance: TextView
    private lateinit var tv_address: TextView
    private lateinit var tv_direction: TextView
    private lateinit var btn_Change: Button
    private lateinit var rc_selectDate: RecyclerView
    private lateinit var rc_selectTime: RecyclerView
    private lateinit var swipeText: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var timeSlotAdapter: TimeSlotAdapter
    private lateinit var timeSlotList: ArrayList<TimeSlot>
    private lateinit var timeSlot: TimeSlot
    private lateinit var progressbar: ProgressBar
    private var selectedHub: HubList? = null
    private var isMove: Boolean = false
    private val YOUR_TOTAL_PROGRESS = 100
    private var selectedDate: String? = ""

    private var vehicleData: Vehicle? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booking_slot_screen)
        updateXML()
    }

    private fun updateXML() {
        try {
            ll_back = findViewById(R.id.ll_back)
            tv_Name = findViewById(R.id.tv_Name)
            tv_distance = findViewById(R.id.tv_distance)
            tv_direction = findViewById(R.id.tv_direction)
            btn_Change = findViewById(R.id.btn_Change)
            rc_selectDate = findViewById(R.id.rc_selectDate)
            rc_selectTime = findViewById(R.id.rc_selectTime)
            tv_address = findViewById(R.id.tv_address)
            swipeText = findViewById(R.id.swipeText)
            seekBar = findViewById(R.id.seekBar)
            progressbar = findViewById(R.id.progressbar)
            timeSlotList = arrayListOf()

            selectedHub = intent.getParcelableExtra<HubList>("selectedHub")
            vehicleData = intent.getParcelableExtra<Vehicle>("vehicleData")!!
            if (selectedHub != null) {
                tv_Name.text = selectedHub!!.title
                tv_address.text = selectedHub!!.description
                tv_distance.text ="("+vehicleData!!.range+")"
            }

            ll_back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })
            btn_Change.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })

            rc_selectDate.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            val calendarList = generateCalendarDates()
            val adapter = CalendarAdapter(calendarList) { data ->
                selectedDate = "${Utils.getDayNumber(data.get(Calendar.DAY_OF_MONTH))} ${
                    data.getDisplayName(
                        Calendar.MONTH,
                        Calendar.LONG,
                        Locale.getDefault()
                    )
                } ${data.get(Calendar.YEAR)}"
            }

            rc_selectDate.adapter = adapter

            rc_selectTime.layoutManager = GridLayoutManager(this@BikeBookingSlotScreen, 3)
            timeSlotAdapter = TimeSlotAdapter(timeSlotList) { selected ->
                timeSlot = selected
            }
            rc_selectTime.adapter = timeSlotAdapter
            if (timeSlotList.isEmpty()) {
                progressbar.visibility = View.GONE
            } else {
                progressbar.visibility = View.VISIBLE

            }
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
                            if(timeSlot.timeslot !="" && selectedDate!=""){
                                val intent = Intent(this@BikeBookingSlotScreen, BookingSummaryScreen::class.java)
                                intent.putExtra("time", timeSlot.timeslot)
                                intent.putExtra("date", selectedDate)
                                intent.putExtra("selectedHub", selectedHub)
                                intent.putExtra("vehicleData", vehicleData)
                                startActivity(intent)
                            }else{
                                Utils.showToast(this@BikeBookingSlotScreen, "Please select Date and Time Slot")
                                seekBar!!.progress = 5
                            }
                        }

                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    if (selectedHub == null) {
                        isMove = true
                        seekBar!!.progress = 5
                        Utils.showToast(this@BikeBookingSlotScreen, "please select Hub")
                    }


                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    isMove = false
                    seekBar?.progress = 5
                    if ((seekBar?.progress ?: 0) < YOUR_TOTAL_PROGRESS) {
                        seekBar?.progress = 5
                    }
                }
            })


            getTimeSlots()
        } catch (e: Exception) {
            Log.e("BikeBookingSlotScreen.updateXML", e.message.toString())
        }
    }

    private fun generateCalendarDates(): List<Calendar> {
        val calendarList = mutableListOf<Calendar>()
        val calendar = Calendar.getInstance() // Start from today

        for (i in 0..25) { // Today + Next 30 Days
            val day = calendar.clone() as Calendar
            calendarList.add(day)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return calendarList
    }

    override fun onRestart() {
        super.onRestart()
        seekBar.progress = 5
        isMove = false
    }

    private fun getTimeSlots() {
        try {
            val dataManager = ServiceManager.getDataManager()
            val otpCallback = object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        response.body()?.let { responseBody ->
                            try {
                                val jsonString = responseBody.string()
                                val jsonElement = JsonParser.parseString(jsonString)
                                val gson = Gson()

                                val timeSlots: List<TimeSlot> = when {
                                    jsonElement.isJsonArray -> {
                                        gson.fromJson(jsonElement, Array<TimeSlot>::class.java).toList()
                                    }
                                    jsonElement.isJsonObject -> {
                                        val jsonObject = jsonElement.asJsonObject
                                        if (jsonObject.has("message")) {
                                            println("No records found: ${jsonObject["message"].asString}")
                                            emptyList()
                                        } else {
                                            listOf(gson.fromJson(jsonObject, TimeSlot::class.java))
                                        }
                                    }
                                    else -> emptyList()
                                }
                                if(ArrayList(timeSlots).isEmpty()){
                                    Utils.showConfirmAlert(this@BikeBookingSlotScreen,"No TimeSlots Available", onOkClick = {
                                        onBackPressed()
                                    })
                                }else{
                                    timeSlotAdapter.updateList(ArrayList(timeSlots))

                                }

                            } catch (e: Exception) {
                                println("JSON Parsing Error: ${e.message}")
                            }
                        }
                    } else {
                        // Handle API error
                        println("Failed to fetch TimeSlots: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    println("API Call Failed: ${t.message}")
                }
            }

            dataManager.getTimeSlot(otpCallback, vehicleData!!.id.toString(), selectedHub?.id)
        } catch (e: Exception) {
            Log.e("BikeBooking.getTimeSlot", e.message.toString())
        }
    }





}