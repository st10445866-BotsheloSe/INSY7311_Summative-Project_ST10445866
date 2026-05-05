package com.example.veda

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {

    private lateinit var tvWaterStat: TextView
    private lateinit var tvSleepStat: TextView
    private lateinit var tvStreak: TextView
    private lateinit var tvLatestMoodReflection: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        tvStreak = findViewById(R.id.tvStreak)
        tvWaterStat = findViewById(R.id.tvWaterStat)
        tvSleepStat = findViewById(R.id.tvSleepStat)
        tvLatestMoodReflection = findViewById(R.id.tvLatestMoodReflection)

        setupNavigation()
    }

    override fun onResume() {
        super.onResume()
        updateDashboardStats()
    }

    private fun updateDashboardStats() {
        val sharedPrefs = getSharedPreferences("VedaPrefs", Context.MODE_PRIVATE)
        val currentStreak = sharedPrefs.getInt("streak_count", 0)
        tvStreak.text = "Streak: $currentStreak Days 🔥"

        val waterAmount = sharedPrefs.getFloat("daily_water", 0.0f)
        val sleepAmount = sharedPrefs.getFloat("daily_sleep", 0.0f)
        tvWaterStat.text = "💧 ${waterAmount}L Water"
        tvSleepStat.text = "😴 ${sleepAmount}h Sleep"

        val lastMoodScore = sharedPrefs.getInt("last_mood_score", -1)
        val lastNote = sharedPrefs.getString("last_mood_note", "No thoughts logged yet.")

        if (lastMoodScore != -1) {
            val moodEmoji = when (lastMoodScore) {
                in 0..2 -> "😫"
                in 3..4 -> "😔"
                in 5..6 -> "😐"
                in 7..8 -> "😊"
                in 9..10 -> "🤩"
                else -> "😐"
            }
            tvLatestMoodReflection.text = "Last Mood: $moodEmoji\n\"$lastNote\""
        }
    }

    private fun setupNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_home

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
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
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                else -> false
            }
        }

        findViewById<CardView>(R.id.btnLogMoodTile).setOnClickListener {
            startActivity(Intent(this, MoodLoggingActivity::class.java))
        }

        findViewById<CardView>(R.id.btnCheckHabitsTile).setOnClickListener {
            startActivity(Intent(this, HabitsActivity::class.java))
        }
    }
}