package com.oddlyspaced.covid19india.util

import android.util.Log
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

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

    fun getDataConfirmed(stateCode: String): ArrayList<Int> {
        val daily = JSONObject(json).getJSONArray("states_daily")
        val values = ArrayList<Int>()
        for (item in 1 until daily.length()) {
            if (JSONObject(daily.get(item).toString()).getString("status") == "Confirmed") {
                values.add(JSONObject(daily.get(item).toString()).getInt(stateCode))
            }
        }
        Log.e("len", values.size.toString())
        return values
    }

    fun getConfirmedTotal(stateCode: String): String {
        return getFormatted(getDataConfirmed(stateCode).sum())
    }

    fun getConfirmedInc(stateCode: String): String {
        return "+${getFormatted(getDataConfirmed(stateCode).last())}"
    }

    fun getDataRecovered(stateCode: String): ArrayList<Int> {
        val daily = JSONObject(json).getJSONArray("states_daily")
        val values = ArrayList<Int>()
        for (item in 1 until daily.length()) {
            if (JSONObject(daily.get(item).toString()).getString("status") == "Recovered") {
                values.add(JSONObject(daily.get(item).toString()).getInt(stateCode))
            }
        }
        Log.e("len", values.size.toString())
        return values
    }

    fun getRecoveredTotal(stateCode: String): String {
        return getFormatted(getDataRecovered(stateCode).sum())
    }

    fun getRecoveredInc(stateCode: String): String {
        return "+${getFormatted(getDataRecovered(stateCode).last())}"
    }

    fun getDataDeceased(stateCode: String): ArrayList<Int> {
        val daily = JSONObject(json).getJSONArray("states_daily")
        val values = ArrayList<Int>()
        for (item in 1 until daily.length()) {
            if (JSONObject(daily.get(item).toString()).getString("status") == "Deceased") {
                values.add(JSONObject(daily.get(item).toString()).getInt(stateCode))
            }
        }
        Log.e("len", values.size.toString())
        return values
    }

    fun getDeceasedTotal(stateCode: String): String {
        return getFormatted(getDataDeceased(stateCode).sum())
    }

    fun getDeceasedInc(stateCode: String): String {
        return "+${getFormatted(getDataDeceased(stateCode).last())}"
    }

    fun getDataActive(stateCode: String): ArrayList<Int> {
        val con = getDataConfirmed(stateCode)
        val rec = getDataRecovered(stateCode)
        val dec = getDataDeceased(stateCode)
        val act = ArrayList<Int>()
        for (i in 0 until con.size) {
            act.add(con[i] - rec[i] - dec[i])
        }
        return act
    }

    fun getActiveTotal(stateCode: String): String {
        return getFormatted(getDataActive(stateCode).sum())
    }

    fun getActiveInc(stateCode: String): String {
        return "+${getFormatted(getDataActive(stateCode).last())}"
    }

    fun generateCumulative(x: ArrayList<Int>): ArrayList<Int> {
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

    private fun getFormatted(num: Int): String {
        val numberFormat = NumberFormat.getInstance(Locale("en", "US"))
        return numberFormat.format(num)
    }

}