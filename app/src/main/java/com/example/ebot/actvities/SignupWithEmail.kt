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

class SignupWithEmail : AppCompatActivity() {
    private lateinit var et_emailId: EditText
    private lateinit var btn_sendOTP: Button
    private lateinit var tv_Login: TextView
    private lateinit var back: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_with_email)
        updateXML()

    }
    private fun updateXML(){
        try{
            et_emailId=findViewById(R.id.et_emailId)
            btn_sendOTP=findViewById(R.id.btn_sendOTP)
            tv_Login=findViewById(R.id.tv_Login)
            back=findViewById(R.id.back)

            btn_sendOTP.setOnClickListener(View.OnClickListener {
                val intent= Intent(this,VerifyOTP::class.java)
                intent.putExtra("screen","signup")
                startActivity(intent)
            })
            tv_Login.setOnClickListener(View.OnClickListener {
                val intent= Intent(this,LoginActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            })
            back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })

        }catch (e:Exception){
            Log.e("SignupWithEmail.updateXML: ",e.message.toString())
        }
    }
}