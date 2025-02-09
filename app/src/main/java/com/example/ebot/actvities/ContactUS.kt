package com.example.ebot.actvities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ebot.R

class ContactUS : AppCompatActivity() {
    private lateinit var back: View
    private lateinit var progressbar: ProgressBar
    private lateinit var tv_Address: TextView
    private lateinit var tv_landlineNo: TextView
    private lateinit var tv_mobileNo1: TextView
    private lateinit var tv_mobileNo2: TextView
    private lateinit var tv_email: TextView
    private lateinit var ll_ContactDetails: LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        updateXML()

    }

    private fun updateXML() {
        try {
            back = findViewById(R.id.back)
            tv_Address = findViewById(R.id.tv_Address)
            tv_landlineNo = findViewById(R.id.tv_landlineNo)
            tv_mobileNo1 = findViewById(R.id.tv_mobileNo1)
            tv_mobileNo2 = findViewById(R.id.tv_mobileNo2)
            tv_email = findViewById(R.id.tv_email)
            progressbar = findViewById(R.id.progressbar)
            ll_ContactDetails = findViewById(R.id.ll_ContactDetails)
            ll_ContactDetails.visibility = View.GONE

            back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}