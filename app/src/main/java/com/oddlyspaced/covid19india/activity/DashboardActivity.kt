package com.oddlyspaced.covid19india.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.oddlyspaced.covid19india.R
import com.oddlyspaced.covid19india.util.CheckInternet
import com.oddlyspaced.covid19india.util.CovidDataJsonParser
import com.oddlyspaced.covid19india.util.StateWiseDataParser
import com.robinhood.spark.SparkAdapter
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_dashboard.viewTouchFeatures
import kotlinx.android.synthetic.main.activity_home.viewTouchFaq
import kotlinx.android.synthetic.main.activity_home.viewTouchHome
import kotlinx.android.synthetic.main.activity_home.viewTouchLinks
import kotlin.collections.ArrayList

class DashboardActivity : AppCompatActivity() {

    private lateinit var stateWiseDataParser: StateWiseDataParser
    private lateinit var covidDataJsonParser: CovidDataJsonParser

    private var stateCode = "tt"
    private lateinit var stateCodeList: ArrayList<String>
    private lateinit var stateNameList: ArrayList<String>
    private lateinit var stateDateList: ArrayList<String>
    private var stateIndex: Int = 0

    private lateinit var confirmedAdapter: GraphAdapter
    private lateinit var activeAdapter: GraphAdapter
    private lateinit var recoveredAdapter: GraphAdapter
    private lateinit var deceasedAdapter: GraphAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setupBottomNavigation()

        checkInternet()
    }

    private fun checkInternet() {
        if (CheckInternet().isConnectionAvailable(this)) {
            rvMain.visibility = View.VISIBLE
            covidDataJsonParser = CovidDataJsonParser()
            covidDataJsonParser.fetchData()

            stateWiseDataParser = StateWiseDataParser()
            stateWiseDataParser.fetchData()
            loadStateList()
        }
        else {
            txNoInternet.visibility = View.VISIBLE
        }
    }

    private fun setupOnTouch() {
        viewTouchGraphType.setOnClickListener {
            if (txGraphType.text == "Cumulative") {
                loadGraphsDaily()
                txGraphType.text = "Daily"
            }
            else {
                loadGraphsCumulative()
                txGraphType.text = "Cumulative"
            }
        }

        viewTouchBeginning.setOnClickListener {
            consBeginning.background = getDrawable(R.color.colorOrangeMedium)
            consMonth.background = getDrawable(R.color.colorOrangeLight)
            consWeek.background = getDrawable(R.color.colorOrangeLight)
            stripToBeginning()
        }

        viewTouchMonth.setOnClickListener {
            consBeginning.background = getDrawable(R.color.colorOrangeLight)
            consMonth.background = getDrawable(R.color.colorOrangeMedium)
            consWeek.background = getDrawable(R.color.colorOrangeLight)
            stripToMonth()
        }

        viewTouchWeek.setOnClickListener {
            consBeginning.background = getDrawable(R.color.colorOrangeLight)
            consMonth.background = getDrawable(R.color.colorOrangeLight)
            consWeek.background = getDrawable(R.color.colorOrangeMedium)
            stripToWeeks()
        }

        txState.setOnClickListener {
            if (stateIndex == stateCodeList.size - 1)
                stateIndex = 0
            else
                stateIndex++
            stateCode = stateCodeList[stateIndex]
            txState.text = stateNameList[stateIndex]
            txDate.text = "Last Updated on ${stateDateList[stateIndex]}"
            reload()
        }
    }

    private fun loadStateList() {
        Handler().postDelayed({
            if (covidDataJsonParser.fetched) {

                covidDataJsonParser.parseData()
                stateCodeList = covidDataJsonParser.stateCodeList
                stateNameList = covidDataJsonParser.stateNameList
                stateDateList = covidDataJsonParser.stateDateList
                stateCode = stateCodeList[0]
                txState.text = stateNameList[0]
                txDate.text = "Last Updated on ${stateDateList[0]}"
                Snackbar.make(rvMain, "Tap on the location name to cycle between locations!", Snackbar.LENGTH_LONG).show()
                loadData()
            }
            else {
                loadStateList()
            }
        }, 100)
    }

    private fun loadData() {
        Handler().postDelayed({
            if (stateWiseDataParser.fetched) {
                reload()
            }
            else {
                loadData()
            }
        }, 100)
    }

    private fun reload() {
        stateWiseDataParser.parseData(stateCode)
        setupGraphs()
        loadGraphs()
        setupOnTouch()
        txGraphType.text = "Cumulative"
        consBeginning.background = getDrawable(R.color.colorOrangeMedium)
        consMonth.background = getDrawable(R.color.colorOrangeLight)
        consWeek.background = getDrawable(R.color.colorOrangeLight)
    }

    private fun setupGraphs() {
        lineConfirmed.lineColor = getColor(R.color.colorRedMedium)
        lineConfirmed.cornerRadius = 16F

        lineActive.lineColor = getColor(R.color.colorBlueMedium)
        lineActive.cornerRadius = 32F

        lineRecovered.lineColor = getColor(R.color.colorGreenMedium)
        lineRecovered.cornerRadius = 32F

        lineDeceased.lineColor = getColor(R.color.colorGrayMedium)
        lineDeceased.cornerRadius = 32F
    }

    private fun loadGraphsCumulative() {

        confirmedAdapter.updateData(stateWiseDataParser.generateCumulative(stateWiseDataParser.dataConfirmed))
        confirmedAdapter.update()

        activeAdapter.updateData(stateWiseDataParser.generateCumulative(stateWiseDataParser.dataActive))
        activeAdapter.update()

        recoveredAdapter.updateData(stateWiseDataParser.generateCumulative(stateWiseDataParser.dataRecovered))
        recoveredAdapter.update()

        deceasedAdapter.updateData(stateWiseDataParser.generateCumulative(stateWiseDataParser.dataDeceased))
        deceasedAdapter.update()

    }

    private fun loadGraphsDaily() {
        confirmedAdapter.updateData(stateWiseDataParser.dataConfirmed)
        confirmedAdapter.update()

        activeAdapter.updateData(stateWiseDataParser.dataActive)
        activeAdapter.update()

        recoveredAdapter.updateData(stateWiseDataParser.dataRecovered)
        recoveredAdapter.update()

        deceasedAdapter.updateData(stateWiseDataParser.dataDeceased)
        deceasedAdapter.update()

    }

    private fun loadGraphs() {

        confirmedAdapter = GraphAdapter()
        if (txGraphType.text == "Cumulative")
            confirmedAdapter.updateData(stateWiseDataParser.generateCumulative(stateWiseDataParser.dataConfirmed))
        else
            confirmedAdapter.updateData(stateWiseDataParser.dataConfirmed)
        lineConfirmed.adapter = confirmedAdapter
        confirmedAdapter.update()

        recoveredAdapter = GraphAdapter()
        if (txGraphType.text == "Cumulative")
            recoveredAdapter.updateData(stateWiseDataParser.generateCumulative(stateWiseDataParser.dataRecovered))
        else
            recoveredAdapter.updateData(stateWiseDataParser.dataRecovered)
        lineRecovered.adapter = recoveredAdapter
        recoveredAdapter.update()

        activeAdapter = GraphAdapter()
        if (txGraphType.text == "Cumulative")
            activeAdapter.updateData(stateWiseDataParser.generateCumulative(stateWiseDataParser.dataActive))
        else
            activeAdapter.updateData(stateWiseDataParser.dataActive)
        lineActive.adapter = activeAdapter
        activeAdapter.update()


        deceasedAdapter = GraphAdapter()
        if (txGraphType.text == "Cumulative")
            deceasedAdapter.updateData(stateWiseDataParser.generateCumulative(stateWiseDataParser.dataDeceased))
        else
            deceasedAdapter.updateData(stateWiseDataParser.dataDeceased)
        lineDeceased.adapter = deceasedAdapter
        deceasedAdapter.update()

        txConfirmedMain.text = stateWiseDataParser.getConfirmedTotal()
        txConfirmedInc.text = stateWiseDataParser.getConfirmedInc()

        txActiveMain.text = stateWiseDataParser.getActiveTotal()
        txActiveInc.text = stateWiseDataParser.getActiveInc()

        txRecMain.text = stateWiseDataParser.getRecoveredTotal()
        txRecInc.text = stateWiseDataParser.getRecoveredInc()

        txDecMain.text = stateWiseDataParser.getDeceasedTotal()
        txDecInc.text = stateWiseDataParser.getDeceasedInc()

        loadGraphsCumulative()
    }

    private fun setupBottomNavigation() {
        viewTouchHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
        viewTouchLinks.setOnClickListener {
            startActivity(Intent(this, LinksActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
        viewTouchFeatures.setOnClickListener {
            startActivity(Intent(this, FeatureActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
        viewTouchFaq.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }

    class GraphAdapter : SparkAdapter() {

        var data = floatArrayOf(0F).toTypedArray()
        lateinit var dataCopy: Array<Float>

        override fun getY(index: Int): Float {
            return data[index]
        }

        override fun getItem(index: Int): Any {
            return data[index]
        }

        override fun getCount(): Int {
            return data.size
        }

        fun updateData(d: Array<Float>) {
            data = d.copyOf()
            dataCopy = d.copyOf()
        }

        fun update() {
            notifyDataSetChanged()
        }

    }

    private fun stripToBeginning() {
        confirmedAdapter = GraphAdapter()
        if (txGraphType.text == "Cumulative")
            confirmedAdapter.updateData(stateWiseDataParser.generateCumulative(stateWiseDataParser.dataConfirmed))
        else
            confirmedAdapter.updateData(stateWiseDataParser.dataConfirmed)
        lineConfirmed.adapter = confirmedAdapter
        confirmedAdapter.update()

        recoveredAdapter = GraphAdapter()
        if (txGraphType.text == "Cumulative")
            recoveredAdapter.updateData(stateWiseDataParser.generateCumulative(stateWiseDataParser.dataRecovered))
        else
            recoveredAdapter.updateData(stateWiseDataParser.dataRecovered)
        lineRecovered.adapter = recoveredAdapter
        recoveredAdapter.update()

        activeAdapter = GraphAdapter()
        if (txGraphType.text == "Cumulative")
            activeAdapter.updateData(stateWiseDataParser.generateCumulative(stateWiseDataParser.dataActive))
        else
            activeAdapter.updateData(stateWiseDataParser.dataActive)
        lineActive.adapter = activeAdapter
        activeAdapter.update()


        deceasedAdapter = GraphAdapter()
        if (txGraphType.text == "Cumulative")
            deceasedAdapter.updateData(stateWiseDataParser.generateCumulative(stateWiseDataParser.dataDeceased))
        else
            deceasedAdapter.updateData(stateWiseDataParser.dataDeceased)
        lineDeceased.adapter = deceasedAdapter
        deceasedAdapter.update()
    }

    private fun stripToMonth() {
        confirmedAdapter = GraphAdapter()
        if (txGraphType.text == "Cumulative")
            confirmedAdapter.updateData(stateWiseDataParser.generateCumulative(stateWiseDataParser.dataConfirmed))
        else
            confirmedAdapter.updateData(stateWiseDataParser.dataConfirmed)
        confirmedAdapter.data = confirmedAdapter.dataCopy.sliceArray(confirmedAdapter.dataCopy.size-31 until confirmedAdapter.dataCopy.size)
        lineConfirmed.adapter = confirmedAdapter
        confirmedAdapter.update()

        recoveredAdapter = GraphAdapter()
        if (txGraphType.text == "Cumulative")
            recoveredAdapter.updateData(stateWiseDataParser.generateCumulative(stateWiseDataParser.dataRecovered))
        else
            recoveredAdapter.updateData(stateWiseDataParser.dataRecovered)
        recoveredAdapter.data = recoveredAdapter.dataCopy.sliceArray(recoveredAdapter.dataCopy.size-31 until recoveredAdapter.dataCopy.size)
        lineRecovered.adapter = recoveredAdapter
        recoveredAdapter.update()

        activeAdapter = GraphAdapter()
        if (txGraphType.text == "Cumulative")
            activeAdapter.updateData(stateWiseDataParser.generateCumulative(stateWiseDataParser.dataActive))
        else
            activeAdapter.updateData(stateWiseDataParser.dataActive)
        activeAdapter.data = activeAdapter.dataCopy.sliceArray(activeAdapter.dataCopy.size-31 until activeAdapter.dataCopy.size)
        lineActive.adapter = activeAdapter
        activeAdapter.update()

        deceasedAdapter = GraphAdapter()
        if (txGraphType.text == "Cumulative")
            deceasedAdapter.updateData(stateWiseDataParser.generateCumulative(stateWiseDataParser.dataDeceased))
        else
            deceasedAdapter.updateData(stateWiseDataParser.dataDeceased)
        deceasedAdapter.data = deceasedAdapter.dataCopy.sliceArray(deceasedAdapter.dataCopy.size-31 until deceasedAdapter.dataCopy.size)
        lineDeceased.adapter = deceasedAdapter
        deceasedAdapter.update()

    }

    private fun stripToWeeks() {
        confirmedAdapter = GraphAdapter()
        if (txGraphType.text == "Cumulative")
            confirmedAdapter.updateData(stateWiseDataParser.generateCumulative(stateWiseDataParser.dataConfirmed))
        else
            confirmedAdapter.updateData(stateWiseDataParser.dataConfirmed)
        confirmedAdapter.data = confirmedAdapter.dataCopy.sliceArray(confirmedAdapter.dataCopy.size-14 until confirmedAdapter.dataCopy.size)
        lineConfirmed.adapter = confirmedAdapter
        confirmedAdapter.update()

        recoveredAdapter = GraphAdapter()
        if (txGraphType.text == "Cumulative")
            recoveredAdapter.updateData(stateWiseDataParser.generateCumulative(stateWiseDataParser.dataRecovered))
        else
            recoveredAdapter.updateData(stateWiseDataParser.dataRecovered)
        recoveredAdapter.data = recoveredAdapter.dataCopy.sliceArray(recoveredAdapter.dataCopy.size-14 until recoveredAdapter.dataCopy.size)
        lineRecovered.adapter = recoveredAdapter
        recoveredAdapter.update()

        activeAdapter = GraphAdapter()
        if (txGraphType.text == "Cumulative")
            activeAdapter.updateData(stateWiseDataParser.generateCumulative(stateWiseDataParser.dataActive))
        else
            activeAdapter.updateData(stateWiseDataParser.dataActive)
        activeAdapter.data = activeAdapter.dataCopy.sliceArray(activeAdapter.dataCopy.size-14 until activeAdapter.dataCopy.size)
        lineActive.adapter = activeAdapter
        activeAdapter.update()


        deceasedAdapter = GraphAdapter()
        if (txGraphType.text == "Cumulative")
            deceasedAdapter.updateData(stateWiseDataParser.generateCumulative(stateWiseDataParser.dataDeceased))
        else
            deceasedAdapter.updateData(stateWiseDataParser.dataDeceased)
        deceasedAdapter.data = deceasedAdapter.dataCopy.sliceArray(deceasedAdapter.dataCopy.size-14 until deceasedAdapter.dataCopy.size)
        lineDeceased.adapter = deceasedAdapter
        deceasedAdapter.update()
    }
}
