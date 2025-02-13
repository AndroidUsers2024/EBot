package com.example.ebot.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ebot.R

class MyRidesAdapter ( private val context: Context): RecyclerView.Adapter<MyRidesAdapter.MyRidesViewHolder>() {
    class MyRidesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_bike_area: TextView = view.findViewById(R.id.tv_bike_area)
        val viewDetails: View = view.findViewById(R.id.viewDetails)
        val tv_date_time: TextView = view.findViewById(R.id.tv_date_time)
        val tv_status: TextView = view.findViewById(R.id.tv_status)
        val bikeImage: ImageView = view.findViewById(R.id.bikeImage)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyRidesViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.my_rides_view, parent, false)
        return MyRidesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyRidesViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}