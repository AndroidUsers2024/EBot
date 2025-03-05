package com.example.ebot.actvities

import android.app.ProgressDialog
import android.content.Intent
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
import com.example.ebot.common.Utils
import com.example.ebot.models.MainResponse
import com.example.ebot.models.SaveBankDetails
import com.example.ebot.models.SaveBankDetailsResponse
import com.example.ebot.services.ServiceManager
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

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
    private var aadharNumber: String? = ""
    private var PANNumber: String? = ""
    private var aadhaarFront: String? = ""
    private var aadhaarBack: String? = ""
    private var PANFront: String? = ""
    private lateinit var screen: String
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
            screen = intent.getStringExtra("screen").toString()
            ll_kYCBankDetails.visibility = View.GONE
            ll_Add_bankDetails.visibility = View.GONE
            if (screen == "KYC") {
                ll_kYCBankDetails.visibility = View.VISIBLE
                ll_Add_bankDetails.visibility = View.GONE
                btn_submit.text = "Submit"

                aadharNumber=intent.getStringExtra("aadharNumber")
                PANNumber=intent.getStringExtra("PANNumber")
                aadhaarFront=intent.getStringExtra("aadhaarFront")
                aadhaarBack=intent.getStringExtra("aadhaarBack")
                PANFront=intent.getStringExtra("PANFront")
            } else {
                ll_kYCBankDetails.visibility = View.GONE
                ll_Add_bankDetails.visibility = View.VISIBLE
                btn_submit.text = "Save"
            }
            skip.setOnClickListener(View.OnClickListener {
                addKYCData()

            })

            kyc_back.setOnClickListener(View.OnClickListener {
                //onBackPressed()

            })
            back.setOnClickListener(View.OnClickListener {
               // onBackPressed()
            })

            btn_submit.setOnClickListener(View.OnClickListener {
                val alerts = isEmpty()
                val intent = Intent()

                if (alerts.isEmpty()) {
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
                }


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

    override fun onBackPressed() {
     if (screen.equals("KYC")){

     }else{
         super.onBackPressed()
     }


    }
    private fun updateBankDetails(bankDetails: SaveBankDetails, screen: String) {
        try {
            dialog = Utils.openDialog(this)
            val dataManager = ServiceManager.getDataManager()
            val callback = object : Callback<SaveBankDetailsResponse> {
                override fun onResponse(call: Call<SaveBankDetailsResponse>, response: Response<SaveBankDetailsResponse>) {

                    Utils.closeDialog(dialog)

                    if (response.isSuccessful) {
                        if (response.body()!!.status == "success") {
                            //move to next screen
                            if (screen.equals("KYC")) {
                                val intent=Intent()
                                intent.setClass(this@BankAccountDetails, KYCVerificationScreen::class.java)
                                intent.putExtra("aadharNumber", aadharNumber)
                                intent.putExtra("PANNumber", PANNumber)
                                intent.putExtra("aadhaarFront", aadhaarFront)
                                intent.putExtra("aadhaarBack", aadhaarBack)
                                intent.putExtra("PANFront", PANFront)
                                intent.putExtra("bankDetails", bankDetails)
                                intent.putExtra("screen", "KYC")
                                startActivity(intent)
                                finish()
                            } else {
                                onBackPressed()
                            }
                        }

                        Utils.showToast(this@BankAccountDetails, response.body()!!.message!!)
                        Log.e("Response", "response" + response.body()!!.message!!)

                    } else {
                        println("Failed to get packages. ${response.message()}")
                        Utils.showToast(
                            this@BankAccountDetails,
                            "Failed to update BankAccountDetails. ${response.message()}"
                        )
                    }

                }

                override fun onFailure(call: Call<SaveBankDetailsResponse>, t: Throwable) {
                    Utils.closeDialog(dialog)
                    println("Failed to get packages. ${t.message}")
                    Utils.showToast(
                        this@BankAccountDetails,
                        "Failed to update BankAccountDetails. ${t.message}"
                    )
                }


            }

            if (screen.equals("KYC")){
                dataManager.saveBankDetails(callback, bankDetails)

            }


        } catch (e: Exception) {
            Log.e("BankAccountDetails.updateBankDetails: ", e.message.toString())
        }
    }


    private fun addKYCData() {
        try {
            val userID = Utils.getData(this@BankAccountDetails, "user_id", "") as String

            var aadharFrontPart: MultipartBody.Part? =null

            val aadharFront_File = File(aadhaarFront.toString())
            if (aadharFront_File.exists()){
                val aadharFront = aadharFront_File.asRequestBody("image/png".toMediaType())

                aadharFrontPart = MultipartBody.Part.createFormData(
                    "aadhar_front",
                    aadharFront_File.name,
                    aadharFront
                )
            }else{
                aadharFrontPart=null
            }


            val aadharBack_File = File(aadhaarBack.toString())
            var aadharBackPart:MultipartBody.Part?=null
            if (aadharBack_File.exists()){
                val aadharBack = aadharBack_File.asRequestBody("image/png".toMediaType())
                aadharBackPart = MultipartBody.Part.createFormData(
                    "aadhar_back",
                    aadharBack_File.name,
                    aadharBack
                )
            }

            val panCard_File = File(PANFront.toString())
            var panImgPart:MultipartBody.Part?=null
            if (panCard_File.exists()){
                val panImg = panCard_File.asRequestBody("image/png".toMediaType())
                 panImgPart = MultipartBody.Part.createFormData(
                    "pan_image",
                    panCard_File.name,
                    panImg
                )
            }else{
                panImgPart=null
            }

            val face_verificaton: MultipartBody.Part? = null
            val user_id = RequestBody.create("text/plain".toMediaType(), userID)
            val aadhar_number = RequestBody.create("text/plain".toMediaType(), aadharNumber!!)
            val pan_number = RequestBody.create("text/plain".toMediaType(), PANNumber!!)
            val account_number = RequestBody.create("text/plain".toMediaType(), "")
            val bank_name = RequestBody.create("text/plain".toMediaType(), "")
            val ifsc_code = RequestBody.create("text/plain".toMediaType(),"")
            val account_type = RequestBody.create("text/plain".toMediaType(), "")
            addKYCApi(user_id,aadhar_number,pan_number,aadharFrontPart!!,aadharBackPart!!,panImgPart!!,account_number,bank_name,ifsc_code,account_type,face_verificaton)
        } catch (e: Exception) {
            Log.e("addKYCData", e.message.toString())
        }

    }

    private fun addKYCApi(
        user_id: RequestBody, aadhar_number: RequestBody, pan_number: RequestBody,
        aadhar_front: MultipartBody.Part,
        aadhar_back: MultipartBody.Part,
        pan_image: MultipartBody.Part,
        account_number: RequestBody,
        bank_name: RequestBody,
        ifsc_code: RequestBody,
        account_type: RequestBody,
        face_verificaton: MultipartBody.Part?
    ) {
        try {
            dialog = Utils.openDialog(this@BankAccountDetails)
            val dataManager = ServiceManager.getDataManager()
            val callbackAddKYC = object : Callback<MainResponse> {
                override fun onResponse(
                    call: Call<MainResponse>,
                    response: Response<MainResponse>
                ) {
                    Utils.closeDialog(dialog)
                    if (response.isSuccessful) {

                      //  Utils.showToast(this@BankAccountDetails, response.body()!!.message.toString())

                    } else {
                       // Utils.showToast(this@BankAccountDetails, response.message().toString())

                    }
                    println("add KYC Details response: ${response}")
                    val intent= Intent()
                    intent.setClass(this@BankAccountDetails,MainActivity::class.java)
                    intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()

                }

                override fun onFailure(call: Call<MainResponse>, t: Throwable) {
                    Utils.closeDialog(dialog)
                    println("Failed to add KYC Details. ${t.message}")
                    val intent= Intent()
                    intent.setClass(this@BankAccountDetails,MainActivity::class.java)
                    intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                 //   Utils.showToast(this@BankAccountDetails, "${t.message} Please try again")
                }

            }
            dataManager.addKYC(
                callbackAddKYC,
                user_id,
                aadhar_number,
                pan_number,
                aadhar_front,
                aadhar_back,
                pan_image,account_number,bank_name,ifsc_code,account_type,face_verificaton
            )

        } catch (e: Exception) {
            if (dialog.isShowing) {
                Utils.closeDialog(dialog)
            }
            val intent= Intent()
            intent.setClass(this,MainActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            Log.e("addKYCApi", e.message.toString())
        }
    }

}