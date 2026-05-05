package com.example.veda

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MoodLoggingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_logging)

        // Initializing UI Components
        val moodSlider = findViewById<SeekBar>(R.id.moodSeekBar)
        val moodDisplay = findViewById<TextView>(R.id.tvMoodValue)
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val btnLogMood = findViewById<Button>(R.id.btnLogMood)

        // Principle 3: User Control & Freedom (The Emergency Exit)
        // Ensuring the user can always return to the dashboard
        btnBack.setOnClickListener {
            finish()
        }

        // Principle 4: Mapping & Direct Manipulation
        // The slider acts as a metaphor for the user's state
        moodSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Principle 1: Visibility of System Status
                // Providing instant, synchronous feedback
                moodDisplay.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Principle 2: Match Between System & Real World
        // Using human language for confirmation
        btnLogMood.setOnClickListener {
            val currentMood = moodSlider.progress
            Toast.makeText(this, "Logged mood: $currentMood", Toast.LENGTH_SHORT).show()
        }
    }
}