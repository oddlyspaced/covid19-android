package com.oddlyspaced.covid19india.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.Spannable
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.oddlyspaced.covid19india.R
import com.oddlyspaced.covid19india.modal.LinksItem


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

        val spanText: Spannable = Spannable.Factory.getInstance().newSpannable(item.link.toLowerCase())
        spanText.setSpan(BackgroundColorSpan(context.getColor(R.color.colorBlueLight)), 0, item.link.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        holder.touch.text = spanText

        holder.touch.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(item.link.toLowerCase())
            context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.txLinkTitle)
        var touch: TextView = itemView.findViewById(R.id.txLinkTouch)
    }

}