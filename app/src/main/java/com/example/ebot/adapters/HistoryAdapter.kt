package com.example.ebot.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ebot.R
import com.example.ebot.models.TransactionList
import java.text.SimpleDateFormat
import java.util.Locale


class HistoryAdapter(
    private var transactionList: List<TransactionList>,
    private val context: Context
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vw_ic_Type: View = view.findViewById(R.id.vw_ic_Type)
        val tv_transactionType: TextView = view.findViewById(R.id.tv_transactionType)
        val tv_addOrWithdraw_ic: TextView = view.findViewById(R.id.tv_addOrWithdraw_ic)
        val tv_Amount: TextView = view.findViewById(R.id.tv_Amount)
        val tv_dateTime: TextView = view.findViewById(R.id.tv_dateTime)
        val tv_status: TextView = view.findViewById(R.id.tv_status)
        val ll_header: CardView = view.findViewById(R.id.cardview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.transactions_list_view, parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val obj = transactionList[position]

        holder.tv_Amount.text = obj.amount
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy, hh:mm a", Locale.getDefault())

        val date = inputFormat.parse(obj!!.created_at)
        val dateTime= outputFormat.format(date!!)
        holder.tv_dateTime.text =dateTime
        val status=obj.pro_status
        if (status.equals("2")) {
            holder.tv_status.text = "Successfully Sent"
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.secondary))
        } else if (status.equals("1")) {
            holder.tv_status.text = "InProgress"
            holder.tv_status.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.yellow
                )
            )
        } else if (status.equals("4")) {
            holder.tv_status.text = "Completed"
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.primary))
        } else if (status.equals("3")) {
            holder.tv_status.text = "Failed"
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.red))
        }else if (status.equals("5")) {
            holder.tv_status.text = "Credited"
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.secondary))
            holder.tv_addOrWithdraw_ic.text = "+ "
            holder.vw_ic_Type.setBackgroundResource(R.drawable.ic_add)
            holder.vw_ic_Type.backgroundTintList = ContextCompat.getColorStateList(context, R.color.yellow)
        }else if (status.equals("6")) {
            holder.tv_status.text = "Debited"
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.red))
            holder.tv_addOrWithdraw_ic.text = "- "
            holder.vw_ic_Type.setBackgroundResource(R.drawable.withdraw_ic)
            holder.vw_ic_Type.backgroundTintList =
                ContextCompat.getColorStateList(context, R.color.blue)
        }else{
            holder.tv_status.text = "Pending...!"
            holder.tv_status.setTextColor(ContextCompat.getColor(context, R.color.red_low))
        }
        if (obj.type.equals("withdraw")) {

                holder.tv_transactionType.text = "Withdrawal"
                holder.tv_addOrWithdraw_ic.text = "- "
                holder.vw_ic_Type.setBackgroundResource(R.drawable.withdraw_ic)
                holder.vw_ic_Type.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.blue)
        }else if (obj.type.equals("wallet")) {

                holder.tv_transactionType.text = "Wallet Money"
                holder.tv_addOrWithdraw_ic.text = "+ "
                holder.vw_ic_Type.setBackgroundResource(R.drawable.ic_wallet)
                holder.vw_ic_Type.backgroundTintList = ContextCompat.getColorStateList(context, R.color.secondary)

        }else if (obj.type.equals("add_money")) {

                holder.tv_transactionType.text = "Added Money"
                holder.tv_addOrWithdraw_ic.text = "+ "
                holder.vw_ic_Type.setBackgroundResource(R.drawable.ic_add)
                holder.vw_ic_Type.backgroundTintList = ContextCompat.getColorStateList(context, R.color.yellow)

        }


    }


    fun updateData(newList: List<TransactionList>) {
        transactionList = newList
        notifyDataSetChanged()
    }

}