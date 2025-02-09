package com.example.ebot.actvities

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ebot.R

class TermsAndConditions : AppCompatActivity() {
    private lateinit var wv_back: View
    private lateinit var progressbar: ProgressBar
    private lateinit var tv_content: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.terms_and_conditions)
        updateXML()

    }
    private fun updateXML(){
        try {
            wv_back = findViewById(R.id.wv_back)
            progressbar = findViewById(R.id.progressbar)
            tv_content = findViewById(R.id.tv_content)
            wv_back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })

        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}