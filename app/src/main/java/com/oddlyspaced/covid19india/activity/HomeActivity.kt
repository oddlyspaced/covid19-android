package com.oddlyspaced.covid19india.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.oddlyspaced.covid19india.util.CovidDataJsonParser
import com.oddlyspaced.covid19india.R
import com.oddlyspaced.covid19india.adapter.CaseContainerAdapter
import com.oddlyspaced.covid19india.adapter.StatContainerAdapter
import com.oddlyspaced.covid19india.util.CheckInternet
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.viewTouchDashboard
import kotlinx.android.synthetic.main.activity_home.viewTouchFaq
import kotlinx.android.synthetic.main.activity_home.viewTouchFeatures
import kotlinx.android.synthetic.main.activity_home.viewTouchLinks


class HomeActivity : AppCompatActivity() {

    private lateinit var covidDataJsonParser: CovidDataJsonParser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupBottomNavigation()
        checkNetwork()
    }

    private fun checkNetwork() {
        if (CheckInternet().isConnectionAvailable(this)) {
            yesNetwork()
        }
        else {
            noNetwork()
        }
    }

    private fun noNetwork() {
      txNoInternet.visibility = View.VISIBLE
    }

    private fun yesNetwork() {
        covidDataJsonParser = CovidDataJsonParser()
        covidDataJsonParser.fetchData()
        isDataAvailable()
    }

    private fun setupBottomNavigation() {
        viewTouchDashboard.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
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

    private fun isDataAvailable() {
        Handler().postDelayed({
            if (covidDataJsonParser.processed) {
                updateStats()
                updateStatsStateWise()
            }
            else {
                isDataAvailable()
            }
        }, 200)
    }

    private fun updateStats() {
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        recyclerStats.layoutManager = layoutManager
        val adapter = StatContainerAdapter(
            covidDataJsonParser.statList,
            this
        )
        recyclerStats.adapter = adapter
        txUpdateTime.text = covidDataJsonParser.date
        animateTopStats()
    }

    private fun updateStatsStateWise() {
        val layoutManager = LinearLayoutManager(this)
        recyclerCases.setHasFixedSize(true)
        recyclerCases.layoutManager = layoutManager
        val adapter = CaseContainerAdapter(
            covidDataJsonParser.caseList,
            this
        )
        recyclerCases.adapter = adapter
        animateBottomCases()
    }

    private fun animateTopStats() {
        recyclerStats.animate()
            .setDuration(1)
            .translationY(recyclerStats.height.toFloat())
            .alpha(1.0f)
            .setListener(null)

        Handler().postDelayed({
            // Prepare the View for the animation
            recyclerStats.visibility = View.VISIBLE;
            recyclerStats.alpha = 0.0f
            recyclerStats.y = recyclerStats.y * 1.5F

            // Start the animation
            recyclerStats.animate()
                .setDuration(500)
                .translationY(0F)
                .alpha(1.0f)
                .setListener(null)

        }, 250)
    }

    private fun animateBottomCases() {

        txInfo1.animate()
            .setDuration(1)
            .translationY(txInfo1.height.toFloat())
            .alpha(1.0f)
            .setListener(null)

        Handler().postDelayed({
            // Prepare the View for the animation
            txInfo1.visibility = View.VISIBLE;
            txInfo1.alpha = 0.0f
            txInfo1.y = txInfo1.y * 1.1F

            // Start the animation
            txInfo1.animate()
                .setDuration(1000)
                .translationY(0F)
                .alpha(1.0f)
                .setListener(null)

        }, 250)


        Handler().postDelayed({
            // Prepare the View for the animation
        }, 250)

        clHeader.animate()
            .setDuration(1)
            .translationY(clHeader.height.toFloat())
            .alpha(1.0f)
            .setListener(null)

        Handler().postDelayed({
            // Prepare the View for the animation
            clHeader.visibility = View.VISIBLE;
            clHeader.alpha = 0.0f
            clHeader.y = clHeader.y * 1.1F

            // Start the animation
            clHeader.animate()
                .setDuration(750)
                .translationY(0F)
                .alpha(1.0f)
                .setListener(null)

        }, 250)

        recyclerCases.animate()
            .setDuration(1)
            .translationY(recyclerCases.height.toFloat())
            .alpha(1.0f)
            .setListener(null)

        Handler().postDelayed({
            // Prepare the View for the animation
            recyclerCases.visibility = View.VISIBLE;
            recyclerCases.alpha = 0.0f
            recyclerCases.y = recyclerCases.y * 1.1F

            // Start the animation
            recyclerCases.animate()
                .setDuration(1000)
                .translationY(0F)
                .alpha(1.0f)
                .setListener(null)

        }, 250)
    }
}
