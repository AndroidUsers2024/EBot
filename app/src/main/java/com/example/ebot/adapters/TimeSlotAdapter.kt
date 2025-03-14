package com.example.ebot.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ebot.R
import com.example.ebot.models.BlockTimeSlot
import com.example.ebot.models.TimeSlot
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class TimeSlotAdapter(
    private var timerSoltList: ArrayList<TimeSlot>,
    private var slotBlockingList: ArrayList<BlockTimeSlot>,
    private var selectedDate: String,
    private val onTimeSelected: (TimeSlot) -> Unit
) : RecyclerView.Adapter<TimeSlotAdapter.ViewHolder>() {
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
        val time = timerSoltList[pos]


        holder.timeText.text = time.timeslot
        holder.dateText.visibility=View.GONE


        val selectedColor = ContextCompat.getColor(holder.itemView.context, R.color.secondary)
        val unselectedColor = ContextCompat.getColor(holder.itemView.context, R.color.primary_low)

        if (pos == selectedPosition) {
            holder.timeText.backgroundTintList = ColorStateList.valueOf(selectedColor)
            holder.timeText.setBackgroundResource(R.drawable.select_item)
            holder.timeText.setTextColor(Color.WHITE)
        } else {
            holder.timeText.setBackgroundResource(R.drawable.ic_border)
            holder.timeText.setTextColor(Color.BLACK)
            holder.timeText.backgroundTintList = ColorStateList.valueOf(unselectedColor)

        }
        var blockTime:BlockTimeSlot?=null
        if (slotBlockingList.isNotEmpty()) {
            blockTime = slotBlockingList[pos]
            try{
                val inputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH)
                val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val localDate = LocalDate.parse(selectedDate, inputFormatter)
                selectedDate = localDate.format(outputFormatter).toString()
            }catch (e:Exception){
                Log.e("dateStr",e.message.toString())
            }
        }

        if (blockTime != null && blockTime.time_slot == time.timeslot && selectedDate == blockTime.date) {

            holder.timeText.backgroundTintList = ColorStateList.valueOf(unselectedColor)
            holder.timeText.setBackgroundResource(R.drawable.select_item)
            holder.timeText.setTextColor(Color.BLACK)
            holder.timeText.isEnabled=false
        }
        val position=pos
        holder.timeText.setOnClickListener {
            val previousSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedPosition)

            onTimeSelected(time)

        }
    }

    override fun getItemCount(): Int = timerSoltList.size

    fun updateList(list:List<TimeSlot>,blocklist:List<BlockTimeSlot>,date:String?){
        timerSoltList= list as ArrayList<TimeSlot>
        slotBlockingList= blocklist as ArrayList<BlockTimeSlot>
        selectedDate=date!!
        notifyDataSetChanged()
    }


}
