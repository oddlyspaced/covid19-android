package com.oddlyspaced.covid19india

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CaseContainerAdapter(private val list: ArrayList<CaseContainerItem>, private val context: Context): RecyclerView.Adapter<CaseContainerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_container_case, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.state.text = item.state
        holder.active.text = item.active.toString()
        holder.confirmed.text = item.confirmed.toString()
        holder.inc.text = when(item.confirmedInc) {
            0 -> ""
            else -> "↑${item.confirmedInc}"
        }

        if (position%2 == 0) {
            setLightBg(holder.cardState)
            setLightBg(holder.cardConfirmed)
            setLightBg(holder.cardActive)
        }

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val cardState: CardView = itemView.findViewById(R.id.cvState)
        val cardConfirmed: CardView = itemView.findViewById(R.id.cvConfirmed)
        val cardActive: CardView = itemView.findViewById(R.id.cvActive)
        val state: TextView = itemView.findViewById(R.id.txState)
        val confirmed: TextView = itemView.findViewById(R.id.txConfirmed)
        val inc: TextView = itemView.findViewById(R.id.txConfirmedInc)
        val active: TextView = itemView.findViewById(R.id.txActive)
    }

    private fun setLightBg(cv: CardView) {
        val bg = cv.background
        bg.setTint(context.getColor(R.color.colorBackgroundLight))
        cv.background = bg
    }

}