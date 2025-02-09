package com.example.ebot.actvities

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ebot.R

class AboutUS : AppCompatActivity() {
    private lateinit var wv_back: View
    private lateinit var webview: WebView
    private lateinit var progressbar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        updateXML()
    }
    private fun updateXML(){
        try {
            wv_back = findViewById(R.id.wv_back)
            webview = findViewById(R.id.webview)
            progressbar = findViewById(R.id.progressbar)
            progressbar.visibility= View.GONE

            wv_back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })

        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}