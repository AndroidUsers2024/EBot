package com.example.ebot.actvities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ebot.R

class PersonalDetails : AppCompatActivity() {
    private lateinit var et_firstName: EditText
    private lateinit var et_lastName: EditText
    private lateinit var btn_Next: Button
    private lateinit var sp_gender: Spinner
    private lateinit var tv_DOB: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.personal_detailscreen)
        updateXML()
    }

    private fun updateXML(){
        try{
            et_firstName=findViewById(R.id.et_firstName)
            et_lastName=findViewById(R.id.et_lastName)
            btn_Next=findViewById(R.id.btn_Next)
            sp_gender=findViewById(R.id.sp_gender)
            tv_DOB=findViewById(R.id.tv_DOB)

            btn_Next.setOnClickListener(View.OnClickListener {
                val intent= Intent(this,ContactDetails::class.java)
                startActivity(intent)
            })


        }catch (e:Exception){
            Log.e("PersonalDetails.updateXML: ",e.message.toString())
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent= Intent(this,LoginActivity::class.java)
        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
}