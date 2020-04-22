package com.oddlyspaced.covid19india.util

import android.util.Log
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL
import java.nio.file.Files.size
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class StateWiseDataParser {

    private val tag = "StateDataParser"
    private val link = "https://api.covid19india.org/states_daily.json"
    private var json = ""
    var fetched = false

    private lateinit var stateCode: String

    lateinit var dataConfirmed: Array<Float>
    lateinit var dataActive: Array<Float>
    lateinit var dataRecovered: Array<Float>
    lateinit var dataDeceased: Array<Float>

    fun fetchData() {
        doAsync {
            json = URL(link).readText()
            Log.d(tag, "JSON Fetched")
            fetched = true
        }
    }

    fun parseData(sc: String) {
        stateCode = sc
        parseDataConfirmed()
        parseDataDeceased()
        parseDataRecovered()
        parseDataActive()
    }

    private fun parseDataConfirmed(){
        val daily = JSONObject(json).getJSONArray("states_daily")
        dataConfirmed = Array(daily.length()/3) { 0F }
        var index = 0
        for (item in 1 until daily.length()) {
            if (JSONObject(daily.get(item).toString()).getString("status") == "Confirmed") {
                dataConfirmed[index++] = JSONObject(daily.get(item).toString()).getInt(stateCode).toFloat()
            }
        }
    }

    private fun parseDataRecovered(){
        val daily = JSONObject(json).getJSONArray("states_daily")
        dataRecovered = Array(daily.length()/3) { 0F }
        var index = 0
        for (item in 1 until daily.length()) {
            if (JSONObject(daily.get(item).toString()).getString("status") == "Recovered") {
                dataRecovered[index++] = JSONObject(daily.get(item).toString()).getInt(stateCode).toFloat()
            }
        }
    }

    private fun parseDataDeceased(){
        val daily = JSONObject(json).getJSONArray("states_daily")
        dataDeceased = Array(daily.length()/3) { 0F }
        var index = 0
        for (item in 1 until daily.length()) {
            if (JSONObject(daily.get(item).toString()).getString("status") == "Deceased") {
                dataDeceased[index++] = JSONObject(daily.get(item).toString()).getInt(stateCode).toFloat()
            }
        }
    }

    private fun parseDataActive(){
        val daily = JSONObject(json).getJSONArray("states_daily")
        dataActive = Array(daily.length()/3) { 0F }

        for (i in dataConfirmed.indices) {
            dataActive[i] = dataConfirmed[i] - dataDeceased[i] - dataRecovered[i]
        }
    }

    fun getConfirmedTotal(): String {
        return getFormatted(dataConfirmed.sum().toInt())
    }

    fun getActiveTotal(): String {
        return getFormatted(dataActive.sum().toInt())
    }

    fun getRecoveredTotal(): String {
        return getFormatted(dataRecovered.sum().toInt())
    }

    fun getDeceasedTotal(): String {
        return getFormatted(dataDeceased.sum().toInt())
    }

    fun getConfirmedInc(): String {
        return "+${getFormatted(dataConfirmed.last().toInt())}"
    }

    fun getActiveInc(): String {
        return "+${getFormatted(dataActive.last().toInt())}"
    }

    fun getRecoveredInc(): String {
        return "+${getFormatted(dataRecovered.last().toInt())}"
    }

    fun getDeceasedInc(): String {
        return "+${getFormatted(dataDeceased.last().toInt())}"
    }

    fun generateCumulative(x: Array<Float>): Array<Float> {
        val y = Array(x.size) {0.0F}
        for ((yi, i) in x.indices.withIndex()) {
            var sum = 0F
            for (j in 0..i) {
                sum += x[j]
            }
            y[yi] = sum
        }
        return y
    }

    private fun getFormatted(num: Int): String {
        val numberFormat = NumberFormat.getInstance(Locale("en", "US"))
        return numberFormat.format(num)
    }

}