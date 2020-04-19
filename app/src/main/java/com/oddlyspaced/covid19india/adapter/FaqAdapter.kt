package com.oddlyspaced.covid19india.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.oddlyspaced.covid19india.R
import com.oddlyspaced.covid19india.modal.FaqItem
import com.oddlyspaced.covid19india.modal.StatContainerItem
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class FaqAdapter(private val list: ArrayList<FaqItem>, private val context: Context): RecyclerView.Adapter<FaqAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_faq_item, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.ques.text = item.question
        holder.ans.text = item.answer
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var ques: TextView = itemView.findViewById(R.id.txQues)
        var ans: TextView = itemView.findViewById(R.id.txAns)
    }

    private fun getFormatted(num: Int): String {
        val numberFormat = NumberFormat.getInstance(Locale("en", "US"))
        return numberFormat.format(num)
    }

}