package com.example.ebot.actvities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ebot.R
import com.google.android.material.imageview.ShapeableImageView

class TransactionDetails : AppCompatActivity() {
    private lateinit var wv_back: View
    private lateinit var vw_share: View
    private lateinit var vw_status: View
    private lateinit var tv_status: TextView
    private lateinit var tv_statusMsg: TextView
    private lateinit var tv_transactionType: TextView
    private lateinit var tv_BankName: TextView
    private lateinit var tv_AccountNumber: TextView
    private lateinit var tv_payAmount: TextView
    private lateinit var tv_Amount: TextView
    private lateinit var tv_date: TextView
    private lateinit var tv_time: TextView
    private lateinit var tv_Reference_Number: TextView
    private lateinit var btn_goBack: Button
    private lateinit var tv_fee: TextView
    private lateinit var img_accountLogo: ShapeableImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaction_details)
        updateXML()

    }
    private fun updateXML(){
        try{
            wv_back = findViewById(R.id.wv_back)
            btn_goBack = findViewById(R.id.btn_goBack)
            vw_share = findViewById(R.id.vw_share)
            vw_status = findViewById(R.id.vw_status)
            tv_status = findViewById(R.id.tv_status)
            tv_statusMsg = findViewById(R.id.tv_statusMsg)
            tv_transactionType = findViewById(R.id.tv_transactionType)
            tv_BankName = findViewById(R.id.tv_BankName)
            tv_AccountNumber = findViewById(R.id.tv_AccountNumber)
            tv_payAmount = findViewById(R.id.tv_payAmount)
            tv_date = findViewById(R.id.tv_date)
            tv_time = findViewById(R.id.tv_time)
            tv_Reference_Number = findViewById(R.id.tv_Reference_Number)
            tv_fee = findViewById(R.id.tv_fee)
            tv_Amount = findViewById(R.id.tv_Amount)
            img_accountLogo = findViewById(R.id.img_accountLogo)


            wv_back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })
            btn_goBack.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })

        }catch (e:Exception){
            e.printStackTrace()

        }
    }
}