package com.oddlyspaced.covid19india.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.oddlyspaced.covid19india.R
import com.oddlyspaced.covid19india.modal.StatContainerItem
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class StatContainerAdapter(private val list: ArrayList<StatContainerItem>, val context: Context): RecyclerView.Adapter<StatContainerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_container_stat, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.heading.text = item.heading
        holder.value.text = getFormatted(item.value)
        holder.desc.text = item.desc

        // cosmetic tweaks
        val bg = holder.card.background
        bg.setTint(context.getColor(when (position) {
            0 -> R.color.colorRedLight
            1 -> R.color.colorBlueLight
            2 -> R.color.colorGreenLight
            3 -> R.color.colorGrayLight
            4 -> R.color.colorPurpleLight
            else -> R.color.colorPurpleDark
        }))
        holder.card.background = bg

        holder.heading.setTextColor(context.getColor(when (position) {
            0 -> R.color.colorRedMedium
            1 -> R.color.colorBlueMedium
            2 -> R.color.colorGreenMedium
            3 -> R.color.colorGrayMedium
            4 -> R.color.colorPurpleMedium
            else -> R.color.colorPurpleDark
        }))

        holder.desc.setTextColor(context.getColor(when (position) {
            0 -> R.color.colorRedMedium
            1 -> R.color.colorBlueMedium
            2 -> R.color.colorGreenMedium
            3 -> R.color.colorGrayMedium
            4 -> R.color.colorPurpleMedium
            else -> R.color.colorPurpleDark
        }))

        holder.value.setTextColor(context.getColor(when (position) {
            0 -> R.color.colorRedDark
            1 -> R.color.colorBlueDark
            2 -> R.color.colorGreenDark
            3 -> R.color.colorGrayDark
            4 -> R.color.colorPurpleDark
            else -> R.color.colorPurpleDark
        }))

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView.findViewById(R.id.cvCaseStat)
        var heading: TextView = itemView.findViewById(R.id.tvStatHeading)
        var value: TextView = itemView.findViewById(R.id.tvStatValue)
        var desc: TextView = itemView.findViewById(R.id.tvStatDesc)
    }

    private fun getFormatted(num: Int): String {
        val numberFormat = NumberFormat.getInstance(Locale("en", "US"))
        return numberFormat.format(num)
    }

}