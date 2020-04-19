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
import com.oddlyspaced.covid19india.modal.LinksItem
import com.oddlyspaced.covid19india.modal.StatContainerItem
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class LinksAdapter(private val list: ArrayList<LinksItem>, private val context: Context): RecyclerView.Adapter<LinksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_links_item, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.title.text = item.title
        holder.touch.text = item.link
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.txLinkTitle)
        var touch: TextView = itemView.findViewById(R.id.txLinkTouch)
    }

}