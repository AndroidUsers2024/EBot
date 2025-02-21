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
import com.example.ebot.adapters.BankDetailsAdapter
import com.example.ebot.common.Utils
import com.example.ebot.models.BankDataResponse
import com.example.ebot.models.BankDetails
import com.example.ebot.models.MainResponse
import com.example.ebot.services.ServiceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewBankDetails : AppCompatActivity() {
    private lateinit var wv_back: View
    private lateinit var btn_AddBankAccount: Button
    private lateinit var rc_addedBank: RecyclerView
    private lateinit var bankDetailsList:ArrayList<BankDetails>
    private lateinit var dialog: ProgressDialog
    private lateinit var bankDetailsAdapter: BankDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_bank_details)
        updateXML()
        rc_addedBank.layoutManager = LinearLayoutManager(this)
        bankDetailsAdapter= BankDetailsAdapter(bankDetailsList,this@ViewBankDetails,null)
        rc_addedBank.adapter = bankDetailsAdapter


    }
    private fun updateXML(){
        try{
            wv_back = findViewById(R.id.wv_back)
            rc_addedBank = findViewById(R.id.rc_addedBank)
            btn_AddBankAccount = findViewById(R.id.btn_AddBankAccount)
            bankDetailsList= arrayListOf()
            wv_back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })
            btn_AddBankAccount.setOnClickListener(View.OnClickListener {
                val intent= Intent(this,BankAccountDetails::class.java)
                intent.putExtra("screen","addNew")
                startActivity(intent)

            })
            val userId=if(Utils.userId.isNullOrEmpty()){
                Utils.getData(this@ViewBankDetails,"user_id","")as String
            }else{
                Utils.userId
            }
            fetchBankDetails(userId.toString())
        }catch (e:Exception){
            e.printStackTrace()

        }
    }

    override fun onRestart() {
        super.onRestart()
        updateXML()
        if (dialog.isShowing){
            Utils.closeDialog(dialog)
        }
        val userId=if(Utils.userId.isNullOrEmpty()){
            Utils.getData(this@ViewBankDetails,"user_id","")as String
        }else{
            Utils.userId
        }

        fetchBankDetails(userId.toString())
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

                        Utils.showToast(this@ViewBankDetails,response.body()!!.message!!)
                        Log.e("Response","response"+response.body().toString())

                    }else{
                        println("Failed to get packages. ${response.message()}")
                        Utils.showToast(this@ViewBankDetails,"Failed to fetch BankAccountDetails. ${response.message()}")
                    }

                }

                override fun onFailure(call: Call<BankDataResponse>, t: Throwable) {
                    if (dialog.isShowing){
                        Utils.closeDialog(dialog)
                    }

                    println("Failed to get packages. ${t.message}")
                    Utils.showToast(this@ViewBankDetails,"Failed to fetch BankAccountDetails. ${t.message}")
                }


            }

            dataManager.fetchBankDetails(callback,userID)



        }catch (e:Exception){
            Log.e("ViewBankDetails.FetchBankDetails: ",e.message.toString())
        }
    }
}