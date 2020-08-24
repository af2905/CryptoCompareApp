package com.af2905.cryptotopandnews.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.af2905.cryptotopandnews.R
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    private val scheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val runnable: () -> Unit = {
            startActivity(MainActivity.newInstance(this))
            finish()
        }
        scheduledExecutorService.schedule(runnable, 3, TimeUnit.SECONDS)
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down)
    }
}
