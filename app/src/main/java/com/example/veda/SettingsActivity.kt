package com.example.veda

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switchNotifications = findViewById<SwitchCompat>(R.id.switchNotifications)
        val tvExportData = findViewById<TextView>(R.id.tvExportData)
        val tvLogout = findViewById<TextView>(R.id.tvLogout)

        val sharedPrefs = getSharedPreferences("VedaPrefs", Context.MODE_PRIVATE)
        switchNotifications.isChecked = sharedPrefs.getBoolean("notifications_enabled", true)

        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefs.edit().putBoolean("notifications_enabled", isChecked).apply()
            val status = if (isChecked) "enabled" else "disabled"
            Toast.makeText(this, "Notifications $status", Toast.LENGTH_SHORT).show()
        }

        tvExportData.setOnClickListener {
            Toast.makeText(this, "Exporting data...", Toast.LENGTH_SHORT).show()
        }

        tvLogout.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        setupNavigation()
    }

    private fun setupNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        // Set listener FIRST to capture any state changes
        bottomNav.setOnItemSelectedListener { item ->
            // Prevent reloading the same activity
            if (item.itemId == bottomNav.selectedItemId && item.itemId == R.id.nav_settings) {
                return@setOnItemSelectedListener true
            }

            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    true
                }
                R.id.nav_mood -> {
                    startActivity(Intent(this, MoodLoggingActivity::class.java))
                    true
                }
                R.id.nav_habits -> {
                    startActivity(Intent(this, HabitsActivity::class.java))
                    true
                }
                R.id.nav_insights -> {
                    startActivity(Intent(this, InsightsActivity::class.java))
                    true
                }
                R.id.nav_settings -> true
                else -> false
            }
        }

        // Set the active state to Settings SECOND
        bottomNav.selectedItemId = R.id.nav_settings
    }
}