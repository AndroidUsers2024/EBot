package com.example.ebot.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ebot.R
import com.example.ebot.actvities.MyRidesScreen
import com.example.ebot.models.MyRides
import com.example.ebot.models.Vehicle

class MyRidesAdapter (private var ridesList: ArrayList<MyRides>, private val context: Context): RecyclerView.Adapter<MyRidesAdapter.MyRidesViewHolder>() {
    class MyRidesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_bike_area: TextView = view.findViewById(R.id.tv_bike_area)
        val viewDetails: View = view.findViewById(R.id.viewDetails)
        val tv_date_time: TextView = view.findViewById(R.id.tv_date_time)
        val tv_status: TextView = view.findViewById(R.id.tv_status)
        val bikeImage: ImageView = view.findViewById(R.id.bikeImage)
        val ll_header: CardView = view.findViewById(R.id.ll_header)
        val ll_my_ride_item: LinearLayout = view.findViewById(R.id.ll_my_ride_item)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyRidesViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.my_rides_view, parent, false)
        return MyRidesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyRidesViewHolder, position: Int) {
        val ride_data:MyRides=ridesList[position]

        holder.tv_bike_area.text =  ride_data.location
        holder.tv_date_time.text = ride_data.created_date +", "+ride_data.created_time
//        holder.tv_status.text = ride_data.status

        /*Glide.with(context).load("https://ritps.com/ebot/"+ride_data.image)
            .into(holder.bikeImage)*/
        if(ride_data.status.equals("1")){
            holder.tv_status.text = "Pending at Admin"
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.yellow))
        }else if (ride_data.status.equals("2")){
            holder.tv_status.text = "Confirmed"
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.secondary))
        }else if(ride_data.status.equals("3")){
            holder.tv_status.text = "Rejected"
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.red))
        }else if (ride_data.status.equals("4")){
            holder.tv_status.text = "On Going Ride"
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.blue))
        }else if(ride_data.status.equals("5")){
            holder.tv_status.text = "Completed"
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.secondary))
        }else if (ride_data.status.equals("6")){
            holder.tv_status.text = "Canceled"
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.primary_text))
        }



        /*holder.viewDetails.setOnClickListener(View.OnClickListener {

            val intent= Intent(context,MyRidesScreen::class.java)
            intent.putExtra("ride_data",ride_data)
            context.startActivity(intent)

        })*/

        /*holder.ll_header.setOnClickListener { View.OnClickListener{
            val intent= Intent(context,MyRidesScreen::class.java)
            intent.putExtra("ride_data",ride_data)
            context.startActivity(intent)
        }}*/

       /* holder.ll_my_ride_item.setOnClickListener { View.OnClickListener{
            val intent= Intent(context,MyRidesScreen::class.java)
            intent.putExtra("ride_data",ride_data)
            context.startActivity(intent)
        }}*/
        holder.ll_my_ride_item.setOnClickListener {
            val intent = Intent(context, MyRidesScreen::class.java)
            intent.putExtra("ride_data", ride_data)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return ridesList.size
    }
    fun updateVehiclesList(list: List<MyRides>){
        ridesList = list as ArrayList<MyRides>
        notifyDataSetChanged()
    }
}