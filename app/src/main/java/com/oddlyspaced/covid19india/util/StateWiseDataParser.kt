package com.oddlyspaced.covid19india.util

import android.util.Log
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL

class StateWiseDataParser {

    private val tag = "StateDataParser"
    private val link = "https://api.covid19india.org/states_daily.json"
    var json = ""
    var fetched = false

    fun fetchData() {
        doAsync {
            json = URL(link).readText()
            Log.d(tag, "JSON Fetched")
            fetched = true
        }
    }

    fun getDataTotal(stateCode: String, type: String): ArrayList<Int> {
        val daily = JSONObject(json).getJSONArray("states_daily")
        val values = ArrayList<Int>()
        for (item in 1 until daily.length()) {
            if (JSONObject(daily.get(item).toString()).getString("status") == type) {
                values.add(JSONObject(daily.get(item).toString()).getInt(stateCode))
            }
        }
        return values
    }

    private fun generateCumulative(x: ArrayList<Int>): ArrayList<Int> {
        val y = ArrayList<Int>()
        for (i in 0 until x.size) {
            var sum = 0
            for (j in 0..i) {
                sum += x[j]
            }
            y.add(sum)
        }
        return y
    }

}