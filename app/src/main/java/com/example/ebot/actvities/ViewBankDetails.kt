package com.example.ebot.actvities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebot.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewBankDetails : AppCompatActivity() {
    private lateinit var wv_back: View
    private lateinit var btn_AddBankAccount: Button
    private lateinit var rc_addedBank: RecyclerView
    private lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_bank_details)
        updateXML()



    }
    private fun updateXML(){
        try{
            wv_back = findViewById(R.id.wv_back)
            rc_addedBank = findViewById(R.id.rc_addedBank)
            btn_AddBankAccount = findViewById(R.id.btn_AddBankAccount)

            wv_back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })
            btn_AddBankAccount.setOnClickListener(View.OnClickListener {
                val intent= Intent(this,BankAccountDetails::class.java)
                intent.putExtra("screen","addNew")
                startActivity(intent)

            })


        }catch (e:Exception){
            e.printStackTrace()

        }
    }


}