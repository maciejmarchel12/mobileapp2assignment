package com.example.historicallandmarksplacemark.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.historicallandmarksplacemark.R
import com.example.historicallandmarksplacemark.views.landmarklist.LandmarkListView

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    private lateinit var ivCastle: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        ivCastle = findViewById(R.id.iv_castle)

        ivCastle.alpha = 0f
        ivCastle.animate().setDuration(1500).alpha(1f).withEndAction {
            val i = Intent(this, LandmarkListView::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
}