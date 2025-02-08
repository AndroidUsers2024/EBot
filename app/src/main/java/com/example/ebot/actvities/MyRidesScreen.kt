package com.example.ebot.actvities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.ebot.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.imageview.ShapeableImageView

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
            pendingRide()

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

            tv_status.text="Pending at Admin"
            tv_date_time.text="Booked at 21 Jan 2025 , 10:00 am"
            tv_status.setTextColor(color_tx)
            card_header.setCardBackgroundColor(color_card)
            card_header.outlineSpotShadowColor=color_card
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
            tv_date_time.text="Booked at 21 Jan 2025 , 12:00 pm"
            tv_status.setTextColor(color_tx)
            card_header.setCardBackgroundColor(color_card)
            card_header.outlineSpotShadowColor=color_card
            tv_total_bill.visibility=View.VISIBLE
            tv_total_bill.text="Total Amount : ₹ 198.00"
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
            tv_date_time.text="Booked at 21 Jan 2025 , 12:00 pm"
            tv_status.setTextColor(color_tx)
            card_header.setCardBackgroundColor(color_card)
            card_header.outlineSpotShadowColor=color_card
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
            val color_tx = ContextCompat.getColor(this, R.color.blue)
            val color_txs = ContextCompat.getColor(this, R.color.yellow)
            val color_card = ContextCompat.getColor(this, R.color.blue_low)

            tv_status.text="On Going Ride"
            tv_date_time.text="Time slot: 21 Jan 2025 , 10 AM to 12 PM"
            tv_status.setTextColor(color_tx)
            tv_reason.setTextColor(color_txs)
            card_header.setCardBackgroundColor(color_card)
            card_header.outlineSpotShadowColor=color_card
            tv_total_bill.visibility=View.GONE
            tv_reason.visibility=View.VISIBLE
            tv_reason.text="Your ride will completed in 46 minutes"
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
            card_header.outlineSpotShadowColor=color_card
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

            tv_status.text="Canceled"
            tv_status.setTextColor(color_tx)
            tv_reason.setTextColor(color_txs)
            card_header.setCardBackgroundColor(color_card)
            card_header.outlineSpotShadowColor=color_card
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
            val tv_location: TextView =view.findViewById(R.id.tv_location)
            val tv_priceHigh: TextView =view.findViewById(R.id.tv_priceHigh)
            val tv_wrongLocation: TextView =view.findViewById(R.id.tv_wrongLocation)
            val tv_changePlane: TextView =view.findViewById(R.id.tv_changePlane)
            val btn_cancel:Button=view.findViewById(R.id.btn_cancel)
            val btn_submit:Button=view.findViewById(R.id.btn_submit)
            close.setOnClickListener(View.OnClickListener {
                bottomSheetDialog.dismiss()
            })
            btn_cancel.setOnClickListener(View.OnClickListener {
                bottomSheetDialog.dismiss()

            })
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
            bottomSheetDialog.setCancelable(false)

        }catch (e:Exception){
            Log.e("Wallet.showAddMoney()",e.message.toString())
        }
    }
}