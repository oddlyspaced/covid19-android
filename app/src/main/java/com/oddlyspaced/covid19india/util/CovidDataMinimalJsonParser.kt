package com.oddlyspaced.covid19india.util

import android.util.Log
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL


class CovidDataMinimalJsonParser {

    private val link = "https://corona.lmao.ninja/v2/countries/india"
    var totalCases = -1
    var todayCases = -1
    var totalDeaths: Int = -1
    var todayDeaths: Int = -1
    var recovered: Int = -1
    var active: Int = -1
    var fetched = false

    fun fetchData() {
        doAsync {
            val apiResponse : String = URL(link).readText()
            parseData(apiResponse)
            fetched = true
        }
    }

    private fun parseData(data: String) {
        val jsonObj = JSONObject(data)
        totalCases = jsonObj.getInt("cases")
        todayCases = jsonObj.getInt("todayCases")
        totalDeaths = jsonObj.getInt("deaths")
        todayDeaths = jsonObj.getInt("todayDeaths")
        recovered = jsonObj.getInt("recovered")
        active = jsonObj.getInt("active")
        Log.e("dwowewe", "Wewewewe")
    }

}