package com.oddlyspaced.covid19india

import android.util.Log
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL

class CovidDataJsonParser {

    private val tag = "CovidDataJsonParser"
    private val link = "https://api.covid19india.org/data.json"
    var fetched = false
    var processed = false

    lateinit var statList: ArrayList<StatContainerItem>
    lateinit var caseList: ArrayList<CaseContainerItem>

    fun fetchData() {
        doAsync {
            val apiResponse : String = URL(link).readText()
            Log.d(tag, "JSON Fetched")
            parseData(apiResponse)
            fetched = true
        }
    }

    private fun parseData(data: String) {
        val jsonObj = JSONObject(data)
        val statewise = jsonObj.getJSONArray("statewise")
        // first item in statewise is total stats
        statList = ArrayList()
        val total = JSONObject(statewise.get(0).toString())
        statList.add(StatContainerItem("Confirmed", total.getInt("confirmed"), "+${total.get("deltaconfirmed")}"))
        statList.add(StatContainerItem("Active", total.getInt("active"), ""))
        statList.add(StatContainerItem("Recovered", total.getInt("recovered"), "+${total.get("deltarecovered")}"))
        statList.add(StatContainerItem("Deceased", total.getInt("deaths"), "+${total.get("deltadeaths")}"))
        statList.add(StatContainerItem("Confirmed", total.getInt("confirmed"), "As of ${total.get("lastupdatedtime").toString().substringBeforeLast("/")}"))

        caseList = ArrayList()
        for (item in 1 until statewise.length()) {
            val state = JSONObject(statewise.get(item).toString())
            Log.e("tssd", statewise.get(item).toString())
            caseList.add(CaseContainerItem(state.getString("state"), state.getInt("confirmed"), state.getInt("deltaconfirmed"), state.getInt("active")))
        }

        processed = true
    }

}