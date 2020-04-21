package com.oddlyspaced.covid19india.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.oddlyspaced.covid19india.R
import com.oddlyspaced.covid19india.util.StateWiseDataParser
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_home.viewTouchFaq
import kotlinx.android.synthetic.main.activity_home.viewTouchHome
import kotlinx.android.synthetic.main.activity_home.viewTouchLinks

class DashboardActivity : AppCompatActivity() {

    private lateinit var stateWiseDataParser: StateWiseDataParser
    private var stCode = "tt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setupBottomNavigation()
        stateWiseDataParser = StateWiseDataParser()
        checkJsonLoaded()
    }

    private fun setupOnTouch() {
        viewTouchGraphType.setOnClickListener {
            /*if (txGraphType.text == "Cumulative") {
                loadGraphsDaily(stCode)
                txGraphType.text = "Daily"
            }
            else {
                loadGraphsCumulative(stCode)
                txGraphType.text = "Cumulative"
            }*/
        }
    }

    // this checks if json was fetched
    private fun checkJsonLoaded() {
        val bundle = intent.extras
        val jsonFetched = bundle?.getInt("fetched")
        if (jsonFetched == 1) {
            stateWiseDataParser.json = bundle.getString("json").toString()
            setupGraphs()
            loadGraphs(stCode) // total
            setupOnTouch()

        }
        else {
            stateWiseDataParser.fetchData()
            loadData()
        }
    }

    private fun callActivity() {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.putExtra("json", stateWiseDataParser.json)
        intent.putExtra("fetched", 1)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun loadData() {
        Handler().postDelayed({
            if (stateWiseDataParser.fetched) {
                callActivity()
            }
            else {
                loadData()
            }
        }, 100)
    }

    private fun setupGraphs() {
        /*
        lineConfirmed.sparkLineColor = getColor(R.color.colorRedMedium)
        lineConfirmed.markerBorderSize = 8F
        lineConfirmed.sparkLineThickness = 8F
        lineConfirmed.sparkLineBezier = 0.5F
        lineConfirmed.markerBorderColor = getColor(R.color.colorRedDark)
        lineConfirmed.markerBackgroundColor = getColor(R.color.colorRedDark)

        lineActive.sparkLineColor = getColor(R.color.colorBlueMedium)
        lineActive.markerBorderSize = 8F
        lineActive.sparkLineThickness = 8F
        lineActive.sparkLineBezier = 0.5F
        lineActive.markerBorderColor = getColor(R.color.colorBlueDark)
        lineActive.markerBackgroundColor = getColor(R.color.colorBlueDark)

        lineRecovered.sparkLineColor = getColor(R.color.colorGreenMedium)
        lineRecovered.markerBorderSize = 8F
        lineRecovered.sparkLineThickness = 8F
        lineRecovered.sparkLineBezier = 0.5F
        lineRecovered.markerBorderColor = getColor(R.color.colorGreenDark)
        lineRecovered.markerBackgroundColor = getColor(R.color.colorGreenDark)

        lineDeceased.sparkLineColor = getColor(R.color.colorGrayMedium)
        lineDeceased.markerBorderSize = 8F
        lineDeceased.sparkLineThickness = 8F
        lineDeceased.sparkLineBezier = 0.5F
        lineDeceased.markerBorderColor = getColor(R.color.colorGrayDark)
        lineDeceased.markerBackgroundColor = getColor(R.color.colorGrayDark)
        */

    }

    private fun loadGraphsCumulative(stateCode: String) {
        /*
        lineConfirmed.setData(stateWiseDataParser.generateCumulative(stateWiseDataParser.getDataConfirmed(stateCode)))
        lineActive.setData(stateWiseDataParser.generateCumulative(stateWiseDataParser.getDataActive(stateCode)))
        lineRecovered.setData(stateWiseDataParser.generateCumulative(stateWiseDataParser.getDataRecovered(stateCode)))
        lineDeceased.setData(stateWiseDataParser.generateCumulative(stateWiseDataParser.getDataDeceased(stateCode)))
         */
    }

    private fun loadGraphsDaily(stateCode: String) {
        /*
        lineConfirmed.setData(stateWiseDataParser.getDataConfirmed(stateCode))
        lineActive.setData(stateWiseDataParser.getDataActive(stateCode))
        lineRecovered.setData(stateWiseDataParser.getDataRecovered(stateCode))
        lineDeceased.setData(stateWiseDataParser.getDataDeceased(stateCode))
         */
    }

    private fun loadGraphs(stateCode: String) {
        /*
        lineConfirmed.setData(stateWiseDataParser.getDataConfirmed(stateCode))
        txConfirmedMain.text = stateWiseDataParser.getConfirmedTotal(stateCode)
        txConfirmedInc.text = stateWiseDataParser.getConfirmedInc(stateCode)

        lineConfirmed.setData(stateWiseDataParser.getDataConfirmed(stateCode))

        lineActive.setData(stateWiseDataParser.generateCumulative(stateWiseDataParser.getDataActive(stateCode)))
        txActiveMain.text = stateWiseDataParser.getActiveTotal(stateCode)
        txActiveInc.text = stateWiseDataParser.getActiveInc(stateCode)

        lineRecovered.setData(stateWiseDataParser.generateCumulative(stateWiseDataParser.getDataRecovered(stateCode)))
        txRecMain.text = stateWiseDataParser.getRecoveredTotal(stateCode)
        txRecInc.text = stateWiseDataParser.getRecoveredInc(stateCode)

        lineDeceased.setData(stateWiseDataParser.generateCumulative(stateWiseDataParser.getDataDeceased(stateCode)))
        txDecMain.text = stateWiseDataParser.getDeceasedTotal(stateCode)
        txDecInc.text = stateWiseDataParser.getDeceasedInc(stateCode)

         */
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
}
