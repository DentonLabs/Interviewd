package io.github.alexdenton.interviewd

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.alexdenton.interviewd.dashboard.DashboardActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }
}
