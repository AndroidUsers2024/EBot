package com.example.ebot.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ebot.R
import com.example.ebot.models.HubList
import com.example.ebot.models.Vehicle

class HubListAdapter  (private var hubLists: ArrayList<HubList>, private val context: Context,private val onBankSelected: ((HubList) -> Unit)?) : RecyclerView.Adapter<HubListAdapter.HubListViewHolder>() {
    class HubListViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val vw_isChoose:View=view.findViewById(R.id.vw_isChoose)
        val tv_Name:TextView=view.findViewById(R.id.tv_Name)
        val tv_distance:TextView=view.findViewById(R.id.tv_distance)
        val tv_address:TextView=view.findViewById(R.id.tv_address)
        val tv_direction:TextView=view.findViewById(R.id.tv_direction)




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HubListViewHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.near_bikes_list_view,parent,false)
        return HubListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hubLists.size
    }

    override fun onBindViewHolder(holder: HubListViewHolder, position: Int) {
        val data:HubList=hubLists[position]
        holder.tv_Name.text=data.title
        holder.tv_address.text=data.description
        holder.tv_distance.text=""
        holder.vw_isChoose
        if (data.isChoose) {
            holder.vw_isChoose.setBackgroundResource(R.drawable.ic_progress_wait)

        } else {
            holder.vw_isChoose.setBackgroundResource(R.drawable.ic_round)
        }

        holder.vw_isChoose.tag = position
        holder.vw_isChoose.setOnClickListener { v ->
            val pos = v.tag as Int
            for (k in hubLists.indices) {
                if (pos != k) hubLists[k].isChoose = false
            }
            hubLists[pos].isChoose = !hubLists[pos].isChoose
            notifyDataSetChanged()
            if (hubLists[pos].isChoose) {
                onBankSelected?.let { it(hubLists[pos]) }
            }

        }

        holder.vw_isChoose.tag = position
        holder.vw_isChoose.setOnClickListener { v ->
            val pos = v.tag as Int
            for (k in hubLists.indices) {
                if (pos != k) hubLists[k].isChoose = false
            }
            hubLists[pos].isChoose = !hubLists[pos].isChoose
            notifyDataSetChanged()
            if (hubLists[pos].isChoose) {
                onBankSelected?.let { it(hubLists[pos]) }
            }
        }



    }

    fun updateHubList(list: List<HubList>){
        hubLists = list as ArrayList<HubList>
        notifyDataSetChanged()
    }
}