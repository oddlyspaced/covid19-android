package com.oddlyspaced.covid19india.activity

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.oddlyspaced.covid19india.R
import com.oddlyspaced.covid19india.service.CovidNotificationService
import com.oddlyspaced.covid19india.service.HandWashService
import kotlinx.android.synthetic.main.activity_feature.*
import kotlinx.android.synthetic.main.activity_feature.viewTouchDashboard
import kotlinx.android.synthetic.main.activity_feature.viewTouchFaq
import kotlinx.android.synthetic.main.activity_feature.viewTouchFeatures
import kotlinx.android.synthetic.main.activity_feature.viewTouchHome
import kotlinx.android.synthetic.main.activity_feature.viewTouchLinks

class FeatureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature)

        setupBottomNavigation()

        cbNotificationToggle.isChecked = isStatsServiceRunning()
        cbNotificationToggle.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                val intent = Intent(this, CovidNotificationService::class.java)
                intent.action = CovidNotificationService().actionStopForegroundService
                startService(intent)
            } else {
                val intent = Intent(this, CovidNotificationService::class.java)
                intent.action = CovidNotificationService().actionStartForegroundService
                startService(intent)

            }
        }

        cbHandWash.isChecked = isHandWashServiceRunning()
        cbHandWash.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                val intent = Intent(this, HandWashService::class.java)
                intent.action = CovidNotificationService().actionStopForegroundService
                startService(intent)
            } else {
                val intent = Intent(this, HandWashService::class.java)
                intent.action = CovidNotificationService().actionStartForegroundService
                startService(intent)

            }
        }
    }

    private fun setupBottomNavigation() {
        viewTouchHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
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

    private fun isStatsServiceRunning(): Boolean {
        val serviceClass = CovidNotificationService::class.java
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    private fun isHandWashServiceRunning(): Boolean {
        val serviceClass = HandWashService::class.java
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

}
