package com.example.veda

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*

class MoodLoggingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_logging)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val tvMoodValue = findViewById<TextView>(R.id.tvMoodValue)
        val moodSeekBar = findViewById<SeekBar>(R.id.moodSeekBar)
        val etMoodNotes = findViewById<EditText>(R.id.etMoodNotes)
        val btnLogMood = findViewById<Button>(R.id.btnLogMood)
        val tvMoodLabel = findViewById<TextView>(R.id.tvTitle)

        btnBack.setOnClickListener { finish() }

        moodSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvMoodValue.text = progress.toString()
                val (emoji, label) = when (progress) {
                    in 0..2 -> "😫" to "Feeling Rough"
                    in 3..4 -> "😔" to "A Bit Down"
                    in 5..6 -> "😐" to "Doing Okay"
                    in 7..8 -> "😊" to "Feeling Good"
                    in 9..10 -> "🤩" to "Amazing!"
                    else -> "😐" to "Steady"
                }
                tvMoodLabel.text = "$emoji $label"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        btnLogMood.setOnClickListener {
            val moodScore = moodSeekBar.progress
            val moodNote = etMoodNotes.text.toString()
            val noteAdded = moodNote.isNotBlank()
            val xpEarned = if (noteAdded) 15 else 10

            val sharedPrefs = getSharedPreferences("VedaPrefs", Context.MODE_PRIVATE)
            with(sharedPrefs.edit()) {
                putInt("last_mood_score", moodScore)
                putString("last_mood_note", if (noteAdded) moodNote else "No notes added today.")
                putInt("total_xp", sharedPrefs.getInt("total_xp", 0) + xpEarned)
                apply()
            }

            updateStreak()
            Toast.makeText(this, "Mood Logged! +$xpEarned XP 🌟", Toast.LENGTH_SHORT).show()
            finish()
        }

        setupNavigation()
    }

    private fun updateStreak() {
        val sharedPrefs = getSharedPreferences("VedaPrefs", Context.MODE_PRIVATE)
        val lastDate = sharedPrefs.getString("last_log_date", "")
        var streak = sharedPrefs.getInt("streak_count", 0)
        val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())

        if (lastDate != today) {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, -1)
            val yesterday = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(calendar.time)
            streak = if (lastDate == yesterday) streak + 1 else 1

            with(sharedPrefs.edit()) {
                putString("last_log_date", today)
                putInt("streak_count", streak)
                apply()
            }
        }
    }

    private fun setupNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_mood
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> { startActivity(Intent(this, DashboardActivity::class.java)); true }
                R.id.nav_mood -> true
                R.id.nav_habits -> { startActivity(Intent(this, HabitsActivity::class.java)); true }
                R.id.nav_insights -> { startActivity(Intent(this, InsightsActivity::class.java)); true }
                R.id.nav_settings -> { startActivity(Intent(this, SettingsActivity::class.java)); true }
                else -> false
            }
        }
    }
}