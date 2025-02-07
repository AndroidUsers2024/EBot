package com.example.ebot.actvities

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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


        } catch (e: Exception) {
            e.printStackTrace()

        }
    }


}