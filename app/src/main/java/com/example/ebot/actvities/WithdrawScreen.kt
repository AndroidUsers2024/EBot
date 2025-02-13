package com.example.ebot.actvities

import android.app.ProgressDialog
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebot.R
import com.example.ebot.common.Utils

class WithdrawScreen : AppCompatActivity() {
    private lateinit var back: View
    private lateinit var et_withdrawAmount: EditText
    private lateinit var et_note: EditText
    private lateinit var rc_BankSelectList: RecyclerView
    private lateinit var btn_AddBankAccount: Button
    private lateinit var btn_withdraw: Button

    private var amount: String? = ""
    private var note: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.withdraw_screen)
        updateXML()

        val userId = if (Utils.userId.isNullOrEmpty()) {
            Utils.getData(this@WithdrawScreen, "user_id", "") as String
        } else {
            Utils.userId
        }

    }

    private fun updateXML() {
        try {
            back = findViewById(R.id.back)
            et_withdrawAmount = findViewById(R.id.et_withdrawAmount)
            et_note = findViewById(R.id.et_note)
            rc_BankSelectList = findViewById(R.id.rc_BankSelectList)
            btn_AddBankAccount = findViewById(R.id.btn_AddBankAccount)
            btn_withdraw = findViewById(R.id.btn_withdraw)
            et_withdrawAmount.setText("")
            et_note.setText("")
            back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })
            btn_AddBankAccount.setOnClickListener(View.OnClickListener {
                val intent = Intent(this, BankAccountDetails::class.java)
                intent.putExtra("screen", "addNew")
                startActivity(intent)
            })
            btn_withdraw.setOnClickListener(View.OnClickListener {


            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun isEmpty(): Boolean {
        try {
            amount = et_withdrawAmount.text.toString().trim()
            val amount_d: Double = Utils.convertToDouble(amount.toString(), 0.0)
            note = et_note.text.toString().trim()
            if (amount.isNullOrEmpty()) {
                Utils.showToast(this@WithdrawScreen, "enter withdraw amount")
                return false

            } else if (amount_d <= 0) {
                Utils.showToast(this@WithdrawScreen, "enter  valid withdraw amount")
                return false

            }



        } catch (e: Exception) {
            Log.e("withdrawScreen.isEmpty", e.message.toString())
        }
        return true
    }


}