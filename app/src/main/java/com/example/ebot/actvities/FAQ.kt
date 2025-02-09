package com.example.ebot.actvities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebot.R

class FAQ : AppCompatActivity() {
    private lateinit var wv_back: View
    private lateinit var et_search: EditText
    private lateinit var rc_faq_list: RecyclerView
    private lateinit var progressbar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        updateXML()
    }

    private fun updateXML() {
        try {
            wv_back = findViewById(R.id.wv_back)
            et_search = findViewById(R.id.et_search)
            rc_faq_list = findViewById(R.id.rc_faq_list)
            progressbar = findViewById(R.id.progressbar)

            wv_back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}