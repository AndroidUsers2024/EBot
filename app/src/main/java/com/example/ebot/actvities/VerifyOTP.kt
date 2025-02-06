package com.example.ebot.actvities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ebot.R

class VerifyOTP : AppCompatActivity() {
    private lateinit var et_enter_OTP_bx1: EditText
    private lateinit var et_enter_OTP_bx2: EditText
    private lateinit var et_enter_OTP_bx3: EditText
    private lateinit var et_enter_OTP_bx4: EditText
    private lateinit var btn_OTPVerify: Button
    private lateinit var tv_phoneNumber: TextView
    private lateinit var tv_resendOTP: TextView
    private var screen:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_verificationscreen)
        updateXML()
    }
    private fun updateXML(){
        try{
            et_enter_OTP_bx1=findViewById(R.id.et_enter_OTP_bx1)
            et_enter_OTP_bx2=findViewById(R.id.et_enter_OTP_bx2)
            et_enter_OTP_bx3=findViewById(R.id.et_enter_OTP_bx3)
            et_enter_OTP_bx4=findViewById(R.id.et_enter_OTP_bx4)
            btn_OTPVerify=findViewById(R.id.btn_OTPVerify)
            tv_phoneNumber=findViewById(R.id.tv_phoneNumber)
            tv_resendOTP=findViewById(R.id.tv_resendOTP)
            screen =intent.getStringExtra("screen")

            btn_OTPVerify.setOnClickListener(View.OnClickListener {
                if (screen.equals("login")){
                    val intent= Intent(this,VerifyOTP::class.java)
                    startActivity(intent)
                }else{
                    val intent= Intent(this,PersonalDetails::class.java)
                    startActivity(intent)
                    finish()
                }

            })
            tv_resendOTP.setOnClickListener(View.OnClickListener {

            })

        }catch (e:Exception){
            Log.e("VerifyOTP.updateXML: ",e.message.toString())
        }
    }
}