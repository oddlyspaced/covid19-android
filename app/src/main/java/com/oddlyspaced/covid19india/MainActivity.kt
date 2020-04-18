package com.oddlyspaced.covid19india

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var covidDataJsonParser: CovidDataJsonParser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // start fetching
        covidDataJsonParser = CovidDataJsonParser()
        covidDataJsonParser.fetchData()

        isDataAvailable()
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
        val adapter = StatContainerAdapter(covidDataJsonParser.statList, this)
        recyclerStats.adapter = adapter
        txUpdateTime.text = covidDataJsonParser.date

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

        }, 1000)
    }

    private fun updateStatsStateWise() {
        val layoutManager = LinearLayoutManager(this)
        recyclerCases.setHasFixedSize(true)
        recyclerCases.layoutManager = layoutManager
        val adapter = CaseContainerAdapter(covidDataJsonParser.caseList, this)
        recyclerCases.adapter = adapter
    }
}
