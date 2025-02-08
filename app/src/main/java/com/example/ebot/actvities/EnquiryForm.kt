package com.example.ebot.actvities

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ebot.R

class EnquiryForm : AppCompatActivity() {
    private lateinit var back: View
    private lateinit var et_Subject: EditText
    private lateinit var et_Message: EditText
    private lateinit var btn_cancel: Button
    private lateinit var btn_send: Button
    private lateinit var openDialog: ProgressDialog
    private var submit:String?=""
    private var message:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enquiry_form)
        back = findViewById(R.id.back)
        et_Subject = findViewById(R.id.et_Subject)
        et_Message = findViewById(R.id.et_Message)
        btn_cancel = findViewById(R.id.btn_cancel)
        btn_send = findViewById(R.id.btn_send)

        back.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
        btn_cancel.setOnClickListener(View.OnClickListener {
            et_Message.setText("")
            et_Subject.setText("")
        })
    }
}