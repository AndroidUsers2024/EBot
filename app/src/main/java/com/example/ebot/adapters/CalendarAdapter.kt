package com.example.ebot.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ebot.R
import java.text.SimpleDateFormat
import java.util.*

class CalendarAdapter(
    private val calendarList: List<Calendar>,
    private val onDateSelected: (Calendar) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
    private var selectedPosition: Int = -1 // Track selected position

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateText: TextView = itemView.findViewById(R.id.dateText)
        val timeText: TextView = itemView.findViewById(R.id.timeText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_date_time_slot, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        val calendar = calendarList[pos]

        val sdfM = SimpleDateFormat("MMM", Locale.getDefault())
        val sdfD = SimpleDateFormat("dd", Locale.getDefault())
        val month=sdfM.format(calendar.time)
        val day=sdfD.format(calendar.time)
        holder.dateText.text = month+"\n"+day
        holder.timeText.visibility=View.GONE


        val selectedColor = ContextCompat.getColor(holder.itemView.context, R.color.secondary)
        val unselectedColor = ContextCompat.getColor(holder.itemView.context, R.color.primary_low)

        if (pos == selectedPosition) {
            holder.dateText.backgroundTintList = ColorStateList.valueOf(selectedColor)
            holder.dateText.setBackgroundResource(R.drawable.select_item)
            holder.dateText.setTextColor(Color.WHITE)
        } else {
            holder.dateText.setBackgroundResource(R.drawable.ic_round)
            holder.dateText.setTextColor(Color.BLACK)
            holder.dateText.backgroundTintList = ColorStateList.valueOf(unselectedColor)

        }
        val position=pos
        holder.dateText.setOnClickListener {
            val previousSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedPosition)

            onDateSelected(calendar)

        }
    }

    override fun getItemCount(): Int = calendarList.size
}
