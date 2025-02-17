package com.example.ebot.actvities

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ebot.R

class TransactionsHistory : AppCompatActivity() {
    private lateinit var back: View
    private lateinit var btn_Added: Button
    private lateinit var btn_withdraw: Button
    private lateinit var rc_history: RecyclerView
    private var isAddClicked: Boolean = true
    private var isWithClicked: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transactions_history)
        updateXML()

    }

    private fun updateXML() {
        try {
            back = findViewById(R.id.back)
            btn_Added = findViewById(R.id.btn_Added)
            btn_withdraw = findViewById(R.id.btn_withdraw)
            rc_history = findViewById(R.id.rc_history)
            val tintColor_u = ContextCompat.getColor(this, R.color.icons)
            val tintColor_s = ContextCompat.getColor(this, R.color.primary)


            btn_Added.setOnClickListener(View.OnClickListener {

                btn_withdraw.setBackgroundResource(R.drawable.ic_border)
                btn_withdraw.backgroundTintList = ColorStateList.valueOf(tintColor_u)
                btn_Added.backgroundTintList = ColorStateList.valueOf(tintColor_s)
                btn_withdraw.setTextColor(getResources().getColor(R.color.primary_text))
                btn_Added.setBackgroundResource(R.drawable.button_bg)
                btn_Added.setTextColor(getResources().getColor(R.color.black))


            })
            btn_withdraw.setOnClickListener(View.OnClickListener {

                btn_Added.setBackgroundResource(R.drawable.ic_border)
                btn_Added.setTextColor(getResources().getColor(R.color.black))
                btn_Added.backgroundTintList = ColorStateList.valueOf(tintColor_u)
                btn_withdraw.backgroundTintList = ColorStateList.valueOf(tintColor_s)

                btn_withdraw.setBackgroundResource(R.drawable.button_bg)
                btn_withdraw.setTextColor(getResources().getColor(R.color.primary_text))


            })
            back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })
        } catch (e: Exception) {
            e.printStackTrace()

        }
    }


}