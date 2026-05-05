package com.example.veda

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HabitsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habits_tracker)

        val btnBack = findViewById<ImageView>(R.id.btnBackHabits)
        val tvSleepLabel = findViewById<TextView>(R.id.tvSleepLabel)
        val sleepSeekBar = findViewById<SeekBar>(R.id.sleepSeekBar)
        val tvStudyLabel = findViewById<TextView>(R.id.tvStudyLabel)
        val studySeekBar = findViewById<SeekBar>(R.id.studySeekBar)
        val tvWaterLabel = findViewById<TextView>(R.id.tvWaterLabel)
        val waterSeekBar = findViewById<SeekBar>(R.id.waterSeekBar)
        val swExercise = findViewById<Switch>(R.id.swExercise)
        val btnUpdate = findViewById<Button>(R.id.btnUpdateHabits)

        btnBack.setOnClickListener { finish() }

        sleepSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(s: SeekBar?, p: Int, f: Boolean) { tvSleepLabel.text = "${p}h" }
            override fun onStartTrackingTouch(s: SeekBar?) {}
            override fun onStopTrackingTouch(s: SeekBar?) {}
        })

        waterSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(s: SeekBar?, p: Int, f: Boolean) {
                val liters = p / 10.0f
                tvWaterLabel.text = String.format("%.1fL", liters)
            }
            override fun onStartTrackingTouch(s: SeekBar?) {}
            override fun onStopTrackingTouch(s: SeekBar?) {}
        })

        btnUpdate.setOnClickListener {
            val sharedPrefs = getSharedPreferences("VedaPrefs", Context.MODE_PRIVATE)
            with(sharedPrefs.edit()) {
                putFloat("daily_sleep", sleepSeekBar.progress.toFloat())
                putInt("daily_study", studySeekBar.progress)
                putFloat("daily_water", waterSeekBar.progress / 10.0f)
                putBoolean("daily_exercise", swExercise.isChecked)
                apply()
            }
            Toast.makeText(this, "Habits Updated! +20 XP 🔥", Toast.LENGTH_SHORT).show()
            finish()
        }

        setupNavigation()
    }

    private fun setupNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_habits
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> { startActivity(Intent(this, DashboardActivity::class.java)); true }
                R.id.nav_mood -> { startActivity(Intent(this, MoodLoggingActivity::class.java)); true }
                R.id.nav_habits -> true
                R.id.nav_insights -> { startActivity(Intent(this, InsightsActivity::class.java)); true }
                R.id.nav_settings -> { startActivity(Intent(this, SettingsActivity::class.java)); true }
                else -> false
            }
        }
    }
}