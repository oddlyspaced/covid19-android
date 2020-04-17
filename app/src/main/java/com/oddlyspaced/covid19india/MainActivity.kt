package com.oddlyspaced.covid19india

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class MainActivity : AppCompatActivity() {

    private val statRecycler by lazy { findViewById<RecyclerView>(R.id.recyclerStats) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateStats()
    }

    private fun updateStats() {
        val list = ArrayList<CaseContainerItem>()
        list.add(CaseContainerItem("Confirmed", "14,352", "+920"))
        list.add(CaseContainerItem("Confirmed", "14,352", "+920"))
        list.add(CaseContainerItem("Confirmed", "14,352", "+920"))
        list.add(CaseContainerItem("Confirmed", "14,352", "+920"))
        list.add(CaseContainerItem("Confirmed", "14,352", "+920"))

        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER

        statRecycler.layoutManager = layoutManager

        val adapter = CaseContainerAdapter(list, this)
        statRecycler.adapter = adapter
    }
}
