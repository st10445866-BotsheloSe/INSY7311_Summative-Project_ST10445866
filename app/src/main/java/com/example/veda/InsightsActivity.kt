package com.example.veda

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.bottomnavigation.BottomNavigationView

class InsightsActivity : AppCompatActivity() {

    private lateinit var moodLineChart: LineChart
    private val days = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insights)

        // 1. Initialize UI Elements
        val tvAvgSleep = findViewById<TextView>(R.id.tvAvgSleep)
        val tvBestDay = findViewById<TextView>(R.id.tvBestDay)
        moodLineChart = findViewById(R.id.moodLineChart)

        // 2. Load Stats from SharedPrefs
        val sharedPrefs = getSharedPreferences("VedaPrefs", Context.MODE_PRIVATE)
        val currentSleep = sharedPrefs.getFloat("daily_sleep", 0.0f)
        val currentMood = sharedPrefs.getInt("last_mood_score", 0)

        tvAvgSleep.text = "${currentSleep}h"

        // Dynamic logic for the "Best Day" card
        tvBestDay.text = if (currentSleep >= 7.0f && currentMood >= 7) {
            "Today! ✨"
        } else {
            "Keep going! 💪"
        }

        // 3. Setup Chart & Toggles
        setupChartStyle()
        updateChartData("Weekly") // Default starting view
        setupToggles()

        // 4. Setup Navigation
        setupNavigation()
    }

    private fun setupChartStyle() {
        moodLineChart.apply {
            description.isEnabled = false
            setTouchEnabled(true)
            setPinchZoom(false)
            setDrawGridBackground(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                textColor = Color.WHITE
                setDrawGridLines(false)
                valueFormatter = IndexAxisValueFormatter(days)
                granularity = 1f
                isGranularityEnabled = true
            }

            axisLeft.apply {
                textColor = Color.WHITE
                axisMinimum = 0f
                axisMaximum = 10f
                setDrawGridLines(true)
                gridColor = Color.parseColor("#44FFFFFF")
            }

            axisRight.isEnabled = false
            legend.textColor = Color.WHITE
            animateX(1000)
        }
    }

    private fun updateChartData(timeframe: String) {
        val entries = ArrayList<Entry>()

        when (timeframe) {
            "Daily" -> {
                moodLineChart.xAxis.axisMinimum = -0.5f
                moodLineChart.xAxis.axisMaximum = 0.5f
                entries.add(Entry(0f, 5f))
            }
            "Weekly" -> {
                moodLineChart.xAxis.axisMinimum = 0f
                moodLineChart.xAxis.axisMaximum = 6f
                entries.add(Entry(1f, 4f)) // Mon
                entries.add(Entry(2f, 7f)) // Tue
                entries.add(Entry(3f, 5f)) // Wed
                entries.add(Entry(4f, 8f)) // Thu
                entries.add(Entry(5f, 6f)) // Fri
            }
            "Monthly" -> {
                moodLineChart.xAxis.axisMinimum = 1f
                moodLineChart.xAxis.axisMaximum = 30f
                for (i in 1..30) entries.add(Entry(i.toFloat(), (4..9).random().toFloat()))
            }
        }

        val dataSet = LineDataSet(entries, "Mood Level").apply {
            color = Color.parseColor("#A3B18A")
            setCircleColor(Color.WHITE)
            lineWidth = 3f
            circleRadius = 5f
            setDrawValues(false)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setDrawFilled(true)
            fillColor = Color.parseColor("#A3B18A")
            fillAlpha = 50
        }

        moodLineChart.data = LineData(dataSet)
        moodLineChart.invalidate()
    }

    private fun setupToggles() {
        val btnDaily = findViewById<Button>(R.id.btnDaily)
        val btnWeekly = findViewById<Button>(R.id.btnWeekly)
        val btnMonthly = findViewById<Button>(R.id.btnMonthly)
        val buttons = listOf(btnDaily, btnWeekly, btnMonthly)

        buttons.forEach { button ->
            button.setOnClickListener { selected ->
                buttons.forEach {
                    it.setBackgroundResource(android.R.color.transparent)
                    it.setTextColor(getColor(R.color.off_white))
                }
                selected.setBackgroundResource(R.drawable.active_toggle_shape)
                (selected as Button).setTextColor(getColor(R.color.sombre_green))

                when (selected.id) {
                    R.id.btnDaily -> updateChartData("Daily")
                    R.id.btnWeekly -> updateChartData("Weekly")
                    R.id.btnMonthly -> updateChartData("Monthly")
                }
            }
        }
    }

    private fun setupNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_insights

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> { startActivity(Intent(this, DashboardActivity::class.java)); true }
                R.id.nav_mood -> { startActivity(Intent(this, MoodLoggingActivity::class.java)); true }
                R.id.nav_habits -> { startActivity(Intent(this, HabitsActivity::class.java)); true }
                R.id.nav_insights -> true
                R.id.nav_settings -> { startActivity(Intent(this, SettingsActivity::class.java)); true }
                else -> false
            }
        }
    }
}