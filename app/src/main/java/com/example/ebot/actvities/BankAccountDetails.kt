package com.example.ebot.actvities

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ebot.R

class BankAccountDetails : AppCompatActivity() {
    private lateinit var ll_kYCBankDetails: LinearLayout
    private lateinit var ll_Add_bankDetails: LinearLayout
    private lateinit var kyc_back: View
    private lateinit var back: View
    private lateinit var skip: TextView
    private lateinit var et_bankAccountNo: EditText
    private lateinit var et_bankConfirmAccountNo: EditText
    private lateinit var et_bankName: EditText
    private lateinit var et_IFSCCode: EditText
    private lateinit var et_AccountType: EditText
    private lateinit var btn_submit: Button
    private var bankAccountNo: String? = ""
    private var bankConfirmAccountNo: String? = ""
    private var bankName: String? = ""
    private var IFSCCode: String? = ""
    private var accountType: String? = ""
    private lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bank_account_details)
        updateXML()
    }

    private fun updateXML() {
        try {
            ll_kYCBankDetails = findViewById(R.id.ll_kYCBankDetails)
            ll_Add_bankDetails = findViewById(R.id.ll_Add_bankDetails)
            kyc_back = findViewById(R.id.kyc_back)
            back = findViewById(R.id.back)
            skip = findViewById(R.id.skip)
            et_bankAccountNo = findViewById(R.id.et_bankAccountNo)
            et_bankConfirmAccountNo = findViewById(R.id.et_bankConfirmAccountNo)
            et_bankName = findViewById(R.id.et_bankName)
            et_IFSCCode = findViewById(R.id.et_IFSCCode)
            et_AccountType = findViewById(R.id.et_AccountType)
            btn_submit = findViewById(R.id.btn_submit)
            val screen = intent.getStringExtra("screen")
            ll_kYCBankDetails.visibility = View.GONE
            ll_Add_bankDetails.visibility = View.GONE
            if (screen == "KYC") {
                ll_kYCBankDetails.visibility = View.VISIBLE
                ll_Add_bankDetails.visibility = View.GONE
                btn_submit.text = "Submit"
            } else {
                ll_kYCBankDetails.visibility = View.GONE
                ll_Add_bankDetails.visibility = View.VISIBLE
                btn_submit.text = "Save"
            }
            skip.setOnClickListener(View.OnClickListener {

            })

            kyc_back.setOnClickListener(View.OnClickListener {
                onBackPressed()

            })
            back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })

            btn_submit.setOnClickListener(View.OnClickListener {
                val alerts = isEmpty()
               /* if (alerts.isEmpty()) {
                    //set userId here

                    val userID = Utils.getData(this@BankAccountDetails,"user_id","") as String
                    val bankDetails = SaveBankDetails(
                        user_id = userID,
                        account_number = bankAccountNo,
                        bank_name = bankName,
                        ifsc_code = IFSCCode,
                        account_type = accountType
                    )
                    if (btn_submit.text.toString() == "Submit") {
                        updateBankDetails(bankDetails, "KYC")
                    } else {
                        updateBankDetails(bankDetails, "Other")
                    }
                } else {
                    Utils.showToast(this@BankAccountDetails, alerts)
                }*/


            })


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isEmpty(): String {
        var alert = ""
        try {
            bankAccountNo = et_bankAccountNo.text.toString()
            bankConfirmAccountNo = et_bankConfirmAccountNo.text.toString()
            bankName = et_bankName.text.toString()
            IFSCCode = et_IFSCCode.text.toString()
            accountType = et_AccountType.text.toString()
            if (bankAccountNo.isNullOrEmpty()) {
                alert += "Please enter your bank account number\n"
            }
            if (bankConfirmAccountNo.isNullOrEmpty()) {
                alert += "Please enter  confirm bank account number\n"
            }
            if (bankAccountNo!!.isNotEmpty() && bankConfirmAccountNo!!.isNotEmpty() && !bankConfirmAccountNo.equals(
                    bankAccountNo
                )
            ) {
                alert += "bank account number and   confirm bank account number should not be match \n"
            }
            if (bankName.isNullOrEmpty()) {
                alert += "Please enter your bank name\n"
            }
            if (IFSCCode.isNullOrEmpty()) {
                alert += "Please enter your bank IFSC Code \n"
            }
            if (accountType.isNullOrEmpty()) {
                alert += "Please enter your bank account type \n"
            }


        } catch (e: Exception) {
            Log.e("BankAccountDetails.isEmpty: ", e.message.toString())
        }
        return alert
    }


}