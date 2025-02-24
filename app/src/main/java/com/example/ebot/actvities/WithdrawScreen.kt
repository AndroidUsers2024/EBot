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
import com.example.ebot.adapters.BankDetailsAdapter
import com.example.ebot.common.Utils
import com.example.ebot.models.BankDataResponse
import com.example.ebot.models.BankDetails
import com.example.ebot.models.MainResponse
import com.example.ebot.models.Withdraw
import com.example.ebot.services.ServiceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WithdrawScreen : AppCompatActivity() {
    private lateinit var back: View
    private lateinit var et_withdrawAmount: EditText
    private lateinit var et_note: EditText
    private lateinit var rc_BankSelectList: RecyclerView
    private lateinit var btn_AddBankAccount: Button
    private lateinit var btn_withdraw: Button
    private lateinit var bankDetailsList: ArrayList<BankDetails>
    private var selectedBankData: BankDetails? = null
    private lateinit var dialog: ProgressDialog
    private lateinit var bankDetailsAdapter: BankDetailsAdapter
    private var amount: String? = ""
    private var note: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.withdraw_screen)
        updateXML()

        rc_BankSelectList.layoutManager = LinearLayoutManager(this)
        bankDetailsAdapter =
            BankDetailsAdapter(bankDetailsList, this@WithdrawScreen) { selectedBank ->
                selectedBankData = selectedBank
            }
        rc_BankSelectList.adapter = bankDetailsAdapter
        val userId = if (Utils.userId.isNullOrEmpty()) {
            Utils.getData(this@WithdrawScreen, "user_id", "") as String
        } else {
            Utils.userId
        }
        fetchBankDetails(userId.toString())


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
            bankDetailsList= arrayListOf()
            back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })
            btn_AddBankAccount.setOnClickListener(View.OnClickListener {
                val intent = Intent(this, BankAccountDetails::class.java)
                intent.putExtra("screen", "addNew")
                startActivity(intent)
            })
            btn_withdraw.setOnClickListener(View.OnClickListener {
                if (isEmpty()) {
                    val user_id=Utils.getData(this@WithdrawScreen,"user_id","") as String
                    val withdraw= Withdraw(user_id,amount,selectedBankData!!.id,note)
                    withdrawApi(withdraw)
                }

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
            if (note.isNullOrEmpty()){
                Utils.showToast(this@WithdrawScreen, "The Notes field is required")
                return false
            }
            if (selectedBankData == null) {
                Utils.showToast(this@WithdrawScreen, "please select bank account")
                return false
            }



        } catch (e: Exception) {
            Log.e("withdrawScreen.isEmpty", e.message.toString())
        }
        return true
    }
    private fun  fetchBankDetails(userID:String){
        try {

            dialog = Utils.openDialog(this)
            val dataManager= ServiceManager.getDataManager()
            val callback= object : Callback<BankDataResponse> {
                override fun onResponse(
                    call: Call<BankDataResponse>,
                    response: Response<BankDataResponse>
                ) {
                    if (dialog.isShowing){
                        Utils.closeDialog(dialog)
                    }
                    if (response.isSuccessful){
                        if (response.body()!!.status=="success"){
                            //move to next screen
                            bankDetailsList.clear()
                            bankDetailsList.addAll(response.body()!!.data)
                            bankDetailsAdapter.updateBankList(bankDetailsList)

                        }

                        Utils.showToast(this@WithdrawScreen,response.body()!!.message!!)
                        Log.e("Response","response"+response.body().toString())

                    }else{
                        println("Failed to get packages. ${response.message()}")
                        Utils.showToast(this@WithdrawScreen,"Failed to fetch BankAccountDetails. ${response.message()}")
                    }

                }

                override fun onFailure(call: Call<BankDataResponse>, t: Throwable) {
                    if (dialog.isShowing){
                        Utils.closeDialog(dialog)
                    }

                    println("Failed to get packages. ${t.message}")
                    Utils.showToast(this@WithdrawScreen,"Failed to fetch BankAccountDetails. ${t.message}")
                }



            }

            dataManager.fetchBankDetails(callback,userID)



        }catch (e:Exception){
            if (dialog.isShowing){
                Utils.closeDialog(dialog)
            }
            Log.e("ViewBankDetails.FetchBankDetails: ",e.message.toString())
        }
    }

    private fun withdrawApi(withdraw: Withdraw) {
        try {
            dialog = Utils.openDialog(this@WithdrawScreen)
            val dataManager = ServiceManager.getDataManager()
            val callbackWithdraw = object : Callback<MainResponse> {
                override fun onResponse(
                    call: Call<MainResponse>,
                    response: Response<MainResponse>
                ) {
                    Utils.closeDialog(dialog)
                    if (response.isSuccessful) {
                        if (response.body()!!.status == "success") {
                            val intent = Intent(this@WithdrawScreen, TransactionDetails::class.java)
                            intent.putExtra("bankDetails", selectedBankData)
                            intent.putExtra("amount", amount)
                            intent.putExtra("note", note)
                            startActivity(intent)
                            finish()

                        }else{
                            Utils.showToast(this@WithdrawScreen, response.body()!!.message.toString())
                        }
                    } else {
                        println("Failed to get bankDetail. ${response.message()}")
                        Utils.showToast(
                            this@WithdrawScreen,
                            "Failed to fetch BankAccountDetails. ${response.message()}"
                        )
                    }
                }

                override fun onFailure(call: Call<MainResponse>, t: Throwable) {
                    Utils.closeDialog(dialog)
                    println("Failed to withdraw. ${t.message}")
                    Utils.showToast(this@WithdrawScreen, "Failed to withdraw. ${t.message}")
                }
            }
            dataManager.withdraw(callbackWithdraw,withdraw)

        } catch (e: Exception) {
            Log.e("withdrawAPI ", e.message.toString())
        }
    }

    override fun onRestart() {
        super.onRestart()
        updateXML()
        if (dialog.isShowing){
            Utils.closeDialog(dialog)
        }
        val userId = if (Utils.userId.isNullOrEmpty()) {
            Utils.getData(this@WithdrawScreen, "user_id", "") as String
        } else {
            Utils.userId
        }

        fetchBankDetails(userId.toString())
    }

}