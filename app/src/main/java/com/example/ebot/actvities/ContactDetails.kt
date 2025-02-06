package com.example.ebot.actvities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ebot.R

class ContactDetails : AppCompatActivity() {
    private lateinit var et_address: EditText
    private lateinit var et_emailId: EditText
    private lateinit var et_pinCode: EditText
    private lateinit var et_city: EditText
    private lateinit var et_state: EditText
    private lateinit var et_country: EditText
    private lateinit var et_mobileNumber: EditText
    private lateinit var back: View
    private lateinit var btn_Next: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contactdetails_screen)
    }
    private fun updateXML(){
        try{
            et_address=findViewById(R.id.et_address)
            et_emailId=findViewById(R.id.et_emailId)
            et_pinCode=findViewById(R.id.et_pinCode)
            et_city=findViewById(R.id.et_city)
            et_state=findViewById(R.id.et_state)
            et_country=findViewById(R.id.et_country)
            et_mobileNumber=findViewById(R.id.et_mobileNumber)
            btn_Next=findViewById(R.id.btn_Next)
            back=findViewById(R.id.back)
            back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })
            btn_Next.setOnClickListener(View.OnClickListener {
                val intent= Intent(this,UploadKYC::class.java)
                startActivity(intent)
            })


        }catch (e:Exception){
            Log.e("ContactDetails.updateXML() : ",e.message.toString())
        }
    }
}