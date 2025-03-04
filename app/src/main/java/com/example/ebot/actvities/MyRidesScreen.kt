package com.example.ebot.actvities

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.compose.material3.RadioButton
import androidx.core.content.ContextCompat
import com.example.ebot.R
import com.example.ebot.common.Utils
import com.example.ebot.models.CancelData
import com.example.ebot.models.HubList
import com.example.ebot.models.MyRides
import com.example.ebot.models.RegisterData
import com.example.ebot.models.RegisterResponse
import com.example.ebot.models.RideCancelResponse
import com.example.ebot.models.Vehicle
import com.example.ebot.services.ServiceManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.imageview.ShapeableImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class MyRidesScreen : AppCompatActivity() {
    private lateinit var back:View
    private lateinit var card_header:CardView
    private lateinit var tv_status:TextView
    private lateinit var tv_date_time:TextView
    private lateinit var tv_total_bill:TextView
    private lateinit var tv_reason:TextView
    private lateinit var tv_bike_title:TextView
    private lateinit var tv_bike_name:TextView
    private lateinit var tv_address:TextView
    private lateinit var bikeImage:ImageView
    private lateinit var tv_booking_date_time_below:TextView
    private lateinit var tv_price:TextView
    private lateinit var btn_cancelRide:Button
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var ll_cardView: LinearLayout

    private  var ride_data: MyRides= MyRides()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_rides_screen)
        updateXML()

    }
    private fun updateXML(){
        try{
            back =findViewById(R.id.back)
            card_header =findViewById(R.id.card_header)
            tv_status =findViewById(R.id.tv_status)
            tv_date_time =findViewById(R.id.tv_date_time)
            tv_total_bill =findViewById(R.id.tv_total_bill)
            tv_reason =findViewById(R.id.tv_reason)
            tv_bike_title =findViewById(R.id.tv_bike_title)
            tv_bike_name =findViewById(R.id.tv_bike_name)
            tv_address =findViewById(R.id.tv_address)
            bikeImage =findViewById(R.id.bikeImage)
            tv_booking_date_time_below =findViewById(R.id.tv_booking_date_time_below)
            tv_price =findViewById(R.id.tv_price)
            btn_cancelRide =findViewById(R.id.btn_cancelRide)
            ll_cardView =findViewById(R.id.ll_cardView)
            ride_data= intent.getParcelableExtra<MyRides>("ride_data")!!

//            tv_bike_name.text = ride_data.
            tv_total_bill.text = "Total Amount : ₹ "+ride_data.total_amount
            tv_booking_date_time_below.text = ride_data.date+" ,"+ride_data.time_slot
            getVehiclesList()
            if(ride_data.status.equals("1")){
                pendingRide()
            }else if (ride_data.status.equals("2")){
                confirmedRide()
            }else if(ride_data.status.equals("3")){
                rejectedRide()
            }else if (ride_data.status.equals("4")){
                onGoingRide()
            }else if(ride_data.status.equals("5")){
                completedRide()
            }else if (ride_data.status.equals("6")){
                canceledRide()
            }

            back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })

        }catch (e:Exception){
            Log.e("MyRidesScreen.updateXML: ",e.message.toString())
        }
    }

    @SuppressLint("NewApi")
    private fun pendingRide(){
        try{
            val color_tx = ContextCompat.getColor(this, R.color.icons)
            val color_card = ContextCompat.getColor(this, R.color.yellow_low)

            //MyRides(id=5, vehicle_id=3, hublist_id=3, date=2025-02-20, time_slot=10:00 AM - 12:00 PM,
            // total_amount=200, location=City Center, created_at=2025-02-20 13:04:15, created_by=1,
            // updated_at=2025-02-20 13:24:41, updated_by=, status=5, reason=test, created_date=20-Feb-2025,
            // created_time=01:04 PM)

            tv_status.text="Pending at Admin"
            tv_date_time.text="Booked at "+ride_data.created_date+" ,"+ride_data.created_time
            tv_status.setTextColor(color_tx)
            card_header.setCardBackgroundColor(color_card)
//            card_header.outlineSpotShadowColor=color_card
            ll_cardView.setBackgroundColor(color_card)
            tv_total_bill.visibility=View.GONE
            tv_reason.visibility=View.GONE
            btn_cancelRide.visibility=View.VISIBLE
            btn_cancelRide.setOnClickListener(View.OnClickListener {
                showCancelReasons()
            })

        }catch (e:Exception){
            Log.e("MyRidesScreen.updateXML: ",e.message.toString())
        }
    }
    @SuppressLint("NewApi")
    private fun completedRide(){
        try{
            val color_tx = ContextCompat.getColor(this, R.color.secondary)
            val color_card = ContextCompat.getColor(this, R.color.secondary_low)

            tv_status.text="Completed Your Ride"
            tv_date_time.text="Booked at "+ride_data.created_date+" ,"+ride_data.created_time
            tv_status.setTextColor(color_tx)
            card_header.setCardBackgroundColor(color_card)
//            card_header.outlineSpotShadowColor=color_card
            ll_cardView.setBackgroundColor(color_card)
            tv_total_bill.visibility=View.VISIBLE
            tv_reason.visibility=View.GONE
            btn_cancelRide.visibility=View.INVISIBLE
        }catch (e:Exception){
            Log.e("MyRidesScreen.completedRide : ",e.message.toString())
        }
    }

    @SuppressLint("NewApi")
    private fun confirmedRide(){
        try{
            val color_tx = ContextCompat.getColor(this, R.color.secondary)
            val color_card = ContextCompat.getColor(this, R.color.secondary_low)

            tv_status.text="Confirmed your Ride"
            tv_date_time.text="Booked at "+ride_data.created_date+" ,"+ride_data.created_time
            tv_status.setTextColor(color_tx)
            card_header.setCardBackgroundColor(color_card)
//            card_header.outlineSpotShadowColor=color_card
            ll_cardView.setBackgroundColor(color_card)
            tv_total_bill.visibility=View.GONE
            tv_reason.visibility=View.GONE
            btn_cancelRide.visibility=View.INVISIBLE
        }catch (e:Exception){
            Log.e("MyRidesScreen.confirmedRide: ",e.message.toString())
        }
    }
    @SuppressLint("NewApi")
    private fun onGoingRide(){
        try{
            // Define the current time and the end of the time slot
            val currentTime = LocalTime.now()  // Get the current time
            var endTimeArray = ride_data.time_slot?.substring(11,16)?.split(":")
//            val endTime = LocalTime.of(endTimeArray?.get(0)?.toInt()!!, endTimeArray.get(1).toInt())  // End time is 12:00 PM
            val endTime = LocalTime.of(21, 0)  // End time is 12:00 PM

            // Check if the current time is within the time slot
            if (currentTime.isBefore(endTime)) {
                val remainingTime = ChronoUnit.MINUTES.between(currentTime, endTime)  // Calculate remaining time in minutes
                tv_reason.text = "Your Ride will complete in $remainingTime minutes"
                println("Remaining time: $remainingTime minutes")
            } else {
                println("The time slot has already passed.")
            }
            val color_tx = ContextCompat.getColor(this, R.color.blue)
            val color_txs = ContextCompat.getColor(this, R.color.yellow)
            val color_card = ContextCompat.getColor(this, R.color.blue_low)
            tv_status.text="On Going Ride"
            tv_date_time.text="Time Slot: "+ride_data.date+" ,"+ride_data.time_slot
            tv_status.setTextColor(color_tx)
            tv_reason.setTextColor(color_txs)



            card_header.setCardBackgroundColor(color_card)
//            card_header.outlineSpotShadowColor=color_card
            ll_cardView.setBackgroundColor(color_card)
            tv_total_bill.visibility=View.GONE
            tv_reason.visibility=View.VISIBLE
//            tv_reason.text="Your ride will completed in 46 minutes"
            btn_cancelRide.visibility=View.INVISIBLE
        }catch (e:Exception){
            Log.e("MyRidesScreen.onGoingRide: ",e.message.toString())
        }
    }

    @SuppressLint("NewApi")
    private fun canceledRide(){
        try{
            val color_tx = ContextCompat.getColor(this, R.color.primary_text)
            val color_txs = ContextCompat.getColor(this, R.color.gray)
            val color_card = ContextCompat.getColor(this, R.color.primary_low)

            tv_status.text="Canceled"
            tv_status.setTextColor(color_tx)
            tv_reason.setTextColor(color_txs)
            card_header.setCardBackgroundColor(color_card)
//            card_header.outlineSpotShadowColor=color_card
            ll_cardView.setBackgroundColor(color_card)
            tv_total_bill.visibility=View.GONE
            tv_date_time.visibility=View.GONE
            tv_reason.visibility=View.VISIBLE
            tv_reason.text="Reason: Planned changed"
            btn_cancelRide.visibility=View.INVISIBLE
        }catch (e:Exception){
            Log.e("MyRidesScreen.canceledRide: ",e.message.toString())
        }
    }

    @SuppressLint("NewApi")
    private fun rejectedRide(){
        try{
            val color_tx = ContextCompat.getColor(this, R.color.red)
            val color_txs = ContextCompat.getColor(this, R.color.gray)
            val color_card = ContextCompat.getColor(this, R.color.red_low)

            tv_status.text="Rejected"
            tv_status.setTextColor(color_tx)
            tv_reason.setTextColor(color_txs)
            card_header.setCardBackgroundColor(color_card)
//            card_header.outlineSpotShadowColor=color_card
            ll_cardView.setBackgroundColor(color_card)
            tv_total_bill.visibility=View.GONE
            tv_date_time.visibility=View.GONE
            tv_reason.visibility=View.VISIBLE
            tv_reason.text="Reason: Vehicle not available "
            btn_cancelRide.visibility=View.INVISIBLE
        }catch (e:Exception){
            Log.e("MyRidesScreen.canceledRide: ",e.message.toString())
        }
    }

    private fun showCancelReasons(){
        try{
            bottomSheetDialog = BottomSheetDialog(this,R.style.BottomSheetTheme)
            val view:View=
                LayoutInflater.from(this).inflate(R.layout.cancel_reasons_view,null )
            val close: ShapeableImageView =view.findViewById(R.id.close)
            /*val tv_location: TextView =view.findViewById(R.id.rb_location)
            val tv_priceHigh: TextView =view.findViewById(R.id.rb_priceHigh)
            val tv_wrongLocation: TextView =view.findViewById(R.id.rb_wrongLocation)
            val tv_changePlane: TextView =view.findViewById(R.id.rb_changePlan)*/
            val btn_cancel:Button=view.findViewById(R.id.btn_cancel)
            val btn_submit:Button=view.findViewById(R.id.btn_submit)
            val radioGroup:RadioGroup = view.findViewById(R.id.radioGroup)
            close.setOnClickListener(View.OnClickListener {
                bottomSheetDialog.dismiss()
            })
            btn_cancel.setOnClickListener(View.OnClickListener {
                bottomSheetDialog.dismiss()

            })
            var selectedRadioButton:RadioButton?=null

            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                 selectedRadioButton = view.findViewById(checkedId)
            }

            // Submit Button Click
            btn_submit.setOnClickListener {
                val reason=selectedRadioButton!!.text.toString().trim()
                if (reason.isNotEmpty() || reason!="null") {
                    cancelRideAPI(ride_data.id.toString(),reason)
                } else {
                    Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
                }
            }

            // Cancel Button Click
            btn_cancel.setOnClickListener {
                radioGroup.clearCheck()
                Toast.makeText(this, "Selection cleared", Toast.LENGTH_SHORT).show()
            }


            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
            bottomSheetDialog.setCancelable(false)

        }catch (e:Exception){
            Log.e("Wallet.showAddMoney()",e.message.toString())
        }
    }

    private lateinit var openDialog: ProgressDialog
    private fun cancelRideAPI(id:String,reason:String){
        try{
            openDialog = Utils.openDialog(this)
            val service = ServiceManager.getDataManager()
            val callback = object : Callback<RideCancelResponse> {
                override fun onResponse(
                    call: Call<RideCancelResponse>,
                    response: Response<RideCancelResponse>
                ) {
                    if (openDialog.isShowing){
                        openDialog.dismiss()
                    }
                    if (response.isSuccessful) {
//                        if (response.body()?.status!!.equals("success")) {
                        val body = response.body()
                        Utils.showToast(this@MyRidesScreen, body!!.message.toString())
                        if(bottomSheetDialog.isShowing){
                            bottomSheetDialog.dismiss()
                        }

                        /*}else{
                            Utils.showToast(this@ContactDetails, response.body()!!.message!!)
                        }*/

                        Log.e("Response", "response" + response.message().toString())

                    } else {

                        println("Failed to send OTP. ${response.message()}")
                        Utils.showToast(this@MyRidesScreen, response.body()!!.message!!)

                    }
                }

                override fun onFailure(call: Call<RideCancelResponse>, t: Throwable) {
                    println("Failed to send OTP. ${t.message}")
                    if (openDialog.isShowing){
                        openDialog.dismiss()

                    }
                    Utils.showToast(this@MyRidesScreen, "Please try again")
                }

            }
            service.calcelRide(callback,id,reason)
        }catch (e:Exception){
            if (openDialog.isShowing){
                openDialog.dismiss()

            }
            Log.e("ContactDetails.RegisterAPI ",e.message.toString())
        }
    }

    private var vehiclesList: ArrayList<Vehicle> = ArrayList()
    private var vehicle_data: Vehicle = Vehicle()
    private fun getVehiclesList(){
        try{
            val dataManager:ServiceManager=ServiceManager.getDataManager()

            val callback=object : Callback<List<Vehicle>> {
                override fun onResponse(
                    call: Call<List<Vehicle>>,
                    response: Response<List<Vehicle>>
                ) {
                    if (response.isSuccessful){
                        vehiclesList.clear()
                        vehiclesList.addAll(response.body()!!)
                        vehicle_data=vehiclesList.find { it.id == ride_data.vehicle_id }!!
                        Log.e("Response","response"+response.body().toString())
                        tv_bike_name.text = vehicle_data.bike_name
                        tv_price.text = vehicle_data.bike_price
                    }else{
                        println("Failed to get bikes. ${response.message()}")
                        Utils.showToast(this@MyRidesScreen,"Failed to get bikes. ${response.message()}")
                    }


                }

                override fun onFailure(call: Call<List<Vehicle>>, t: Throwable) {
                    println("Failed to get bikes. ${t.message}")


                    Utils.showToast(this@MyRidesScreen,"Failed to get bikes. ${t.message}")
                }

            }
            dataManager.getVehicles(callback)


        }catch (e:Exception){
            Log.e("HomeFragment.bikes",e.message.toString())
        }finally {
            getHubList()
        }
    }

    private var hubLists: ArrayList<HubList> = ArrayList()
    var hubList_data :HubList = HubList()
    private fun getHubList() {
        try {
            val dataManager = ServiceManager.getDataManager()

            val otpCallback = object : Callback<ArrayList<HubList>> {
                override fun onResponse(
                    call: Call<ArrayList<HubList>>,
                    response: Response<ArrayList<HubList>>
                ) {
                    if (response.isSuccessful) {
                        val responseData = response.body()!!
                        hubLists.clear()
                        hubLists.addAll(responseData)
                        hubList_data = hubLists.find { it.id == ride_data.hublist_id }!!
                        tv_address.text = hubList_data.description
                        tv_bike_title.text = hubList_data.title
                    } else {
                        // Handle error
                        println("Failed to hub_list. ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<ArrayList<HubList>>, t: Throwable) {
                    println("Failed to hub_list. ${t.message}")
                }
            }

            dataManager.getHubList(otpCallback)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}