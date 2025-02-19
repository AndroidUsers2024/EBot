package com.example.ebot.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ebot.R
import com.example.ebot.models.Faqs

class FAQAdapter (private var faqs: List<Faqs>, private val context: Context) : RecyclerView.Adapter<FAQAdapter.FaqViewHolder>() {

    class FaqViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_question: TextView = view.findViewById(R.id.tv_question)
        val tv_answer: TextView = view.findViewById(R.id.tv_answer)
        val ll_header: LinearLayout = view.findViewById(R.id.ll_header)
        val vw_isOpen: View = view.findViewById(R.id.vw_isOpen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.faq_list_view, parent, false)
        return FaqViewHolder(view)
    }

    override fun getItemCount(): Int {
        return faqs.size
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        val obj = faqs[position]
        holder.tv_question.setText("${obj.title}")
        holder.tv_answer.setText("${obj.description}")
        val tintColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.primary))
        holder.vw_isOpen.backgroundTintList=tintColor
        if (obj.isExpand) {
            holder.tv_answer.visibility = View.VISIBLE
            holder.vw_isOpen.setBackgroundResource(R.drawable.ic_arrow_up)

        } else {
            holder.tv_answer.visibility = View.GONE
            holder.vw_isOpen.setBackgroundResource(R.drawable.ic_arrow_down)
        }
        holder.ll_header.tag = position
        holder.ll_header.setOnClickListener { v ->
            val pos = v.tag as Int
            for (k in faqs.indices) {
                if (pos != k) faqs[k].isExpand = false
            }
            faqs[pos].isExpand = !faqs[pos].isExpand
            notifyDataSetChanged()
        }
    }
    fun updateData(newFaqs: List<Faqs>) {
        faqs = newFaqs
        notifyDataSetChanged()
    }
}