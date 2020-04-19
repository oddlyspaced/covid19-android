package com.oddlyspaced.covid19india.util

import android.util.Log
import com.oddlyspaced.covid19india.modal.CaseContainerItem
import com.oddlyspaced.covid19india.modal.StatContainerItem
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL

class CovidDataJsonParser {

    private val tag = "CovidDataJsonParser"
    private val link = "https://api.covid19india.org/data.json"
    var fetched = false
    var processed = false

    lateinit var date: String
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
        parseDate(total.getString("lastupdatedtime"))

        statList.add(
            StatContainerItem(
                "Confirmed",
                total.getInt("confirmed"),
                "+${total.get("deltaconfirmed")}"
            )
        )
        statList.add(
            StatContainerItem(
                "Active",
                total.getInt("active"),
                ""
            )
        )
        statList.add(
            StatContainerItem(
                "Recovered",
                total.getInt("recovered"),
                "+${total.get("deltarecovered")}"
            )
        )
        statList.add(
            StatContainerItem(
                "Deceased",
                total.getInt("deaths"),
                "+${total.get("deltadeaths")}"
            )
        )
        statList.add(
            StatContainerItem(
                "Confirmed",
                total.getInt("confirmed"),
                "As of ${date.substringBeforeLast(",")}"
            )
        )
        caseList = ArrayList()
        for (item in 1 until statewise.length()) {
            val state = JSONObject(statewise.get(item).toString())
            caseList.add(
                CaseContainerItem(
                    state.getString("state"),
                    state.getInt("confirmed"),
                    state.getInt("deltaconfirmed"),
                    state.getInt("active")
                )
            )
        }


        processed = true
    }

    private fun parseDate(d: String){
        // d -- 18/04/2020 00:45:05
        Log.e("asdsdsdsds", d.substring(d.indexOf("/")+1, d.lastIndexOf("/")))
        var dd = d.substringBefore("/")
        val mm = when ((d.substring(d.indexOf("/") + 1, d.lastIndexOf("/")))) {
            "01" -> "Jan"
            "02" -> "Feb"
            "03" -> "Mar"
            "04" -> "Apr"
            "05" -> "May"
            "06" -> "Jun"
            "07" -> "Jul"
            "08" -> "Aug"
            "09" -> "Sep"
            "10" -> "Oct"
            "11" -> "Nov"
            "12" -> "Dec"
            else -> "???"
        }
        Log.e("Ssds", "cacac")
        dd = "$dd $mm"
        val time = d.substring(d.indexOf(" ")+1, d.lastIndexOf(":"))
        dd = "$dd, $time IST"
        date = dd
    }

}