package com.oddlyspaced.covid19india.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.oddlyspaced.covid19india.R
import com.oddlyspaced.covid19india.util.StateWiseDataParser
import com.robinhood.spark.SparkAdapter
import com.robinhood.spark.animation.MorphSparkAnimator
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_home.viewTouchFaq
import kotlinx.android.synthetic.main.activity_home.viewTouchHome
import kotlinx.android.synthetic.main.activity_home.viewTouchLinks
import java.util.*
import kotlin.collections.ArrayList

class DashboardActivity : AppCompatActivity() {

    private lateinit var stateWiseDataParser: StateWiseDataParser
    private var stateCode = "tt"
    private lateinit var confirmedAdapter: GraphAdapter
    private lateinit var activeAdapter: GraphAdapter
    private lateinit var recoveredAdapter: GraphAdapter
    private lateinit var deceasedAdapter: GraphAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setupBottomNavigation()
        stateWiseDataParser = StateWiseDataParser()
        stateWiseDataParser.fetchData()
        loadData()
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

        viewTouchMonth.setOnClickListener {
            stripToMonth()
        }
    }

    private fun loadData() {
        Handler().postDelayed({
            if (stateWiseDataParser.fetched) {
                setupGraphs()
                loadGraphs()
                setupOnTouch()
            }
            else {
                loadData()
            }
        }, 100)
    }

    private fun setupGraphs() {
        val animator = MorphSparkAnimator()
        animator.setDuration(1000L)

        lineConfirmed.lineColor = getColor(R.color.colorRedMedium)
        lineConfirmed.cornerRadius = 32F
        lineConfirmed.sparkAnimator = animator

        lineActive.lineColor = getColor(R.color.colorBlueMedium)
        lineActive.cornerRadius = 32F
        lineActive.sparkAnimator = animator

        lineRecovered.lineColor = getColor(R.color.colorGreenMedium)
        lineRecovered.cornerRadius = 32F
        lineRecovered.sparkAnimator = animator

        lineDeceased.lineColor = getColor(R.color.colorGrayMedium)
        lineDeceased.cornerRadius = 32F
        lineDeceased.sparkAnimator = animator
    }

    private fun loadGraphsCumulative() {
        confirmedAdapter.updateData(convertIntArrayListToFloatArray(stateWiseDataParser.generateCumulative(stateWiseDataParser.getDataConfirmed(stateCode))))
        confirmedAdapter.update()

        activeAdapter.updateData(convertIntArrayListToFloatArray(stateWiseDataParser.generateCumulative(stateWiseDataParser.getDataActive(stateCode))))
        activeAdapter.update()

        recoveredAdapter.updateData(convertIntArrayListToFloatArray(stateWiseDataParser.generateCumulative(stateWiseDataParser.getDataRecovered(stateCode))))
        recoveredAdapter.update()

        deceasedAdapter.updateData(convertIntArrayListToFloatArray(stateWiseDataParser.generateCumulative(stateWiseDataParser.getDataDeceased(stateCode))))
        deceasedAdapter.update()


        if (confirmedAdapter.data.equals(recoveredAdapter.data)) {
            Log.e("wow", "weird")
        }
        else {
            Log.e("wwww", "niooooooooo")
        }

    }

    private fun loadGraphsDaily() {
        confirmedAdapter.updateData(convertIntArrayListToFloatArray(stateWiseDataParser.getDataConfirmed(stateCode)))
        confirmedAdapter.update()

        activeAdapter.updateData(convertIntArrayListToFloatArray(stateWiseDataParser.getDataActive(stateCode)))
        activeAdapter.update()

        recoveredAdapter.updateData(convertIntArrayListToFloatArray(stateWiseDataParser.getDataRecovered(stateCode)))
        recoveredAdapter.update()

        deceasedAdapter.updateData(convertIntArrayListToFloatArray(stateWiseDataParser.getDataDeceased(stateCode)))
        deceasedAdapter.update()

    }

    private fun loadGraphs() {
        confirmedAdapter = GraphAdapter()
        lineConfirmed.adapter = confirmedAdapter
        txConfirmedMain.text = stateWiseDataParser.getConfirmedTotal(stateCode)
        txConfirmedInc.text = stateWiseDataParser.getConfirmedInc(stateCode)

        activeAdapter = GraphAdapter()
        lineActive.adapter = activeAdapter
        txActiveMain.text = stateWiseDataParser.getActiveTotal(stateCode)
        txActiveInc.text = stateWiseDataParser.getActiveInc(stateCode)

        recoveredAdapter = GraphAdapter()
        lineRecovered.adapter = confirmedAdapter
        txRecMain.text = stateWiseDataParser.getRecoveredTotal(stateCode)
        txRecInc.text = stateWiseDataParser.getRecoveredInc(stateCode)

        deceasedAdapter = GraphAdapter()
        lineDeceased.adapter = confirmedAdapter
        txDecMain.text = stateWiseDataParser.getDeceasedTotal(stateCode)
        txDecInc.text = stateWiseDataParser.getDeceasedInc(stateCode)

        loadGraphsCumulative()
    }

    private fun setupBottomNavigation() {
        viewTouchHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
        viewTouchFaq.setOnClickListener {
            startActivity(Intent(this, FaqActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
        viewTouchLinks.setOnClickListener {
            startActivity(Intent(this, LinksActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }

    private fun convertIntArrayListToFloatArray(d: ArrayList<Int>): Array<Float> {
        return Array(d.size) {
                i -> d[i].toFloat()
        }.copyOf()
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
            Log.e("changed", "hmmm")
            notifyDataSetChanged()
        }

    }

    private fun stripToBeginning() {
    }

    private fun stripToMonth() {
        confirmedAdapter.data = Array<Float>(10){i -> i*2F}
        confirmedAdapter.update()
    }

    private fun stripToWeeks() {

    }
}
