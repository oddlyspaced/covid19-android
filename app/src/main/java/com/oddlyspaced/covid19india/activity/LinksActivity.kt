package com.oddlyspaced.covid19india.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.oddlyspaced.covid19india.R
import com.oddlyspaced.covid19india.adapter.LinksAdapter
import com.oddlyspaced.covid19india.modal.LinksItem
import kotlinx.android.synthetic.main.activity_links.*

class LinksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_links)

        loadLinks()
    }

    private fun loadLinks() {
        val list = ArrayList<LinksItem>()
        list.add(LinksItem("HELPLINE NUMBERS [by State]", "HTTPS://WWW.MOHFW.GOV.IN/PDF/CORONVAVIRUSHELPLINENUMBER.PDF"))
        list.add(LinksItem("Ministry of Health and Family Welfare, Gov. of India", "HTTPS://WWW.MOHFW.GOV.IN/"))
        list.add(LinksItem("WHO : COVID-19 Home Page", "HTTPS://WWW.WHO.INT/EMERGENCIES/DISEASES/NOVEL-CORONAVIRUS-2019"))
        list.add(LinksItem("CDC", "HTTPS://WWW.CDC.GOV/CORONAVIRUS/2019-NCOV/FAQ.HTML"))
        list.add(LinksItem("Crowdsourced list of Resources & Essentials from across India", "HTTPS://BIT.LY/COVID19RESOURCELIST"))
        list.add(LinksItem("COVID-19 Global Tracker", "HTTPS://CORONAVIRUS.THEBASELAB.COM/"))
        val layoutManager = LinearLayoutManager(this)
        recyclerLinks.setHasFixedSize(true)
        recyclerLinks.layoutManager = layoutManager
        val adapter = LinksAdapter(list, this)
        recyclerLinks.adapter = adapter
    }
}
