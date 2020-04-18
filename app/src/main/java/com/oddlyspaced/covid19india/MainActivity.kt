package com.oddlyspaced.covid19india

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateStats()
        updateStatsStateWise()
    }

    private fun updateStats() {
        val list = ArrayList<StatContainerItem>()
        list.add(StatContainerItem("Confirmed", "14,352", "+920"))
        list.add(StatContainerItem("Confirmed", "14,352", "+920"))
        list.add(StatContainerItem("Confirmed", "14,352", "+920"))
        list.add(StatContainerItem("Confirmed", "14,352", "+920"))
        list.add(StatContainerItem("Confirmed", "14,352", "+920"))

        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER

        recyclerStats.layoutManager = layoutManager

        val adapter = StatContainerAdapter(list, this)
        recyclerStats.adapter = adapter
    }

    private fun updateStatsStateWise() {
        val list = ArrayList<CaseContainerItem>()
        list.add(CaseContainerItem("Maharashtra", 3320, 0, 2788))
        list.add(CaseContainerItem("Maharashtra", 3320, 118, 2788))
        list.add(CaseContainerItem("Maharashtra", 3320, 118, 2788))
        list.add(CaseContainerItem("Maharashtra", 3320, 0, 2788))
        list.add(CaseContainerItem("Maharashtra", 3320, 18, 2788))
        list.add(CaseContainerItem("Maharashtra", 3320, 11, 2788))
        list.add(CaseContainerItem("Maharashtra", 3320, 1, 2788))

        val layoutManager = LinearLayoutManager(this)
        recyclerCases.setHasFixedSize(true)
        recyclerCases.layoutManager = layoutManager

        val adapter = CaseContainerAdapter(list, this)
        recyclerCases.adapter = adapter
    }
}
