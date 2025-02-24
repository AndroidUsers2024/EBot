package com.example.ebot.adapters

import android.content.Context
import android.content.Intent
import android.provider.SyncStateContract.Constants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ebot.R
import com.example.ebot.actvities.BikeViewDetailsActivity
import com.example.ebot.actvities.MainActivity
import com.example.ebot.models.Vehicle
import java.util.Locale

class BikesAdapter  (private var packagesList: ArrayList<Vehicle>, private val context: Context) : RecyclerView.Adapter<BikesAdapter.VehiclesViewHolder>() {
    class VehiclesViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val tv_title:TextView=view.findViewById(R.id.tv_title)
        val tv_subTitle:TextView=view.findViewById(R.id.tv_subTitle)
        val tv_km:TextView=view.findViewById(R.id.tv_km)
        val tv_speed:TextView=view.findViewById(R.id.tv_speed)
        val tv_battery_type:TextView=view.findViewById(R.id.tv_battery_type)
        val iv_vehicle:ImageView=view.findViewById(R.id.iv_vehicle)
        val ll_view_details:LinearLayout=view.findViewById(R.id.ll_view_details)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiclesViewHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.bikes_cardview,parent,false)
        return VehiclesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return packagesList.size
    }

    override fun onBindViewHolder(holder: VehiclesViewHolder, position: Int) {
        val vehicle_data:Vehicle=packagesList[position]
            holder.tv_title.text =  vehicle_data.bike_name
            holder.tv_subTitle.text = " Starting at "+vehicle_data.bike_price

        holder.tv_km.text = vehicle_data.range
            holder.tv_speed.text = vehicle_data.speed
            holder.tv_battery_type.text = vehicle_data.battery_type
            Glide.with(context).load("https://ritps.com/ebot/"+vehicle_data.bike_image)
                .into(holder.iv_vehicle)



        holder.ll_view_details.setOnClickListener(View.OnClickListener {
            val intent = Intent(context,BikeViewDetailsActivity::class.java)
            intent.putExtra("vehicle",vehicle_data)
            context.startActivity(intent)
        })

    }

    fun updateVehiclesList(list: List<Vehicle>){
        packagesList = list as ArrayList<Vehicle>
        notifyDataSetChanged()
    }
}