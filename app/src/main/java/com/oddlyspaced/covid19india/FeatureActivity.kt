package com.oddlyspaced.covid19india

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_feature.*

class FeatureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature)

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
