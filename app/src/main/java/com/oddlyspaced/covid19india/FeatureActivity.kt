package com.oddlyspaced.covid19india

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_feature.*

class FeatureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature)

        cbNotificationToggle.isChecked = isServiceRunning(CovidNotificationService::class.java)

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
    }

    private fun isServiceRunning(serviceClass: Class<CovidNotificationService>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

}
