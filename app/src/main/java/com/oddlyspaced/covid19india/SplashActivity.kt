package com.oddlyspaced.covid19india

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    private var loc: Float = -1F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //Handler().postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        //}, 1000)
        //loc = imgCovid.y
        //animateIcon()
    }

    private fun animateIcon() {
        Handler().postDelayed({
            imgCovid.y = 0F
            imgCovid.animate()
                .translationY(imgCovid.height.toFloat())
                .setDuration(1000)
                .setListener(object: AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        animateIcon()
                    }
                })
        }, 100)
    }
}
