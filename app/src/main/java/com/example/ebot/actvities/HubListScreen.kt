package com.example.ebot.actvities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebot.R
import com.example.ebot.adapters.HubListAdapter
import com.example.ebot.common.Utils
import com.example.ebot.models.HubList
import com.example.ebot.models.Vehicle
import com.example.ebot.services.ServiceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HubListScreen : AppCompatActivity() {
    private lateinit var ll_back: LinearLayout
    private lateinit var swipeText: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var rc_hubList: RecyclerView
    private lateinit var progressbar: ProgressBar
    private lateinit var hubListAdapter: HubListAdapter
    private var isHub: Boolean = false
    private var hubList: ArrayList<HubList> = arrayListOf()
    private var selectedHub: HubList? = null
    private var screen: Int = 0
    private val YOUR_TOTAL_PROGRESS = 55
    private lateinit var vehicleData: Vehicle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hub_list_screen)
        updateXML()

    }

    private fun updateXML() {
        try {
            screen = 0
            ll_back = findViewById(R.id.ll_back)
            swipeText = findViewById(R.id.swipeText)
            seekBar = findViewById(R.id.seekBar)
            hubList = arrayListOf()
            seekBar.progress = 5
            rc_hubList = findViewById(R.id.rc_hubList)
            progressbar = findViewById(R.id.progressbar)
            progressbar.visibility=View.VISIBLE
            rc_hubList.layoutManager = LinearLayoutManager(this)
            vehicleData = intent.getParcelableExtra<Vehicle>("vehicleData")!!
            hubListAdapter = HubListAdapter(hubList, this@HubListScreen) { selected ->
                selectedHub = selected
                if(selected.id==null){
                    seekBar.isEnabled=false
                }else{
                    seekBar.isEnabled=true

                }
            }
            if (hubList.isEmpty()){
                progressbar.visibility=View.VISIBLE
            }else{
                progressbar.visibility=View.GONE
            }

            rc_hubList.adapter = hubListAdapter
            getHubList()
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                @SuppressLint("SuspiciousIndentation")
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
                        seekBar?.progress = 5
                        if (!isHub && selectedHub !=null) {
                                isHub = true
                            seekBar?.progress = 5
                            val intent = Intent(this@HubListScreen, BikeBookingSlotScreen::class.java)
                                intent.putExtra("selectedHub", selectedHub)
                                intent.putExtra("vehicleData", vehicleData)
                                startActivity(intent)

                        }else if(progress==55){
                            seekBar?.progress = 5
                            Toast.makeText(this@HubListScreen,"please select near hub",Toast.LENGTH_SHORT).show()
                        }


                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    isHub = false


                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    isHub = false

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



    override fun onRestart() {
        super.onRestart()
        seekBar.progress = 5
       isHub=false
    }
}