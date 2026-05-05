package com.example.veda

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        // Initialize views from activity_auth.xml
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val tvErrorMessage = findViewById<TextView>(R.id.tvErrorMessage)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnCreateAccount = findViewById<Button>(R.id.btnCreateAccount)

        // Logic for the Log In button
        btnLogin.setOnClickListener {
            val emailInput = etEmail.text.toString()
            val passwordInput = etPassword.text.toString()

            // Simulated Prototype Validation
            if (emailInput == "student@veda.com" && passwordInput == "12345") {
                // Navigate to the DashboardActivity as per Home Dashboard_4.png
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish() // Prevents the user from going back to login via the back button
            } else {
                // Show error message to user
                tvErrorMessage.visibility = View.VISIBLE
                tvErrorMessage.text = "Error: Invalid credentials. Try student@veda.com"
            }
        }

        // Logic for the Create Account button
        btnCreateAccount.setOnClickListener {
            // In this prototype, account creation automatically succeeds
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}