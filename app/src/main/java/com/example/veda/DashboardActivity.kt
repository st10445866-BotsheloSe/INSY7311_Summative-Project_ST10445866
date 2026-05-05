package com.example.veda

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        // Setting "Home" as selected by default
        bottomNav.selectedItemId = R.id.nav_home

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_mood -> {
                    // Navigate to your existing Mood Logger
                    startActivity(Intent(this, MoodLoggingActivity::class.java))
                    true
                }
                R.id.nav_home -> true
                else -> false
            }
        }

        // Also link the tile button for "Log Mood"
        findViewById<androidx.cardview.widget.CardView>(R.id.btnLogMoodTile).setOnClickListener {
            startActivity(Intent(this, MoodLoggingActivity::class.java))
        }
    }
}