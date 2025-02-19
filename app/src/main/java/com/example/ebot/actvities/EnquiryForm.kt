package com.example.ebot.actvities

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ebot.R
import com.example.ebot.common.Utils
import com.example.ebot.models.MainResponse
import com.example.ebot.models.SubmitEnquiry
import com.example.ebot.services.ServiceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnquiryForm : AppCompatActivity() {
    private lateinit var back: View
    private lateinit var et_Subject: EditText
    private lateinit var et_Message: EditText
    private lateinit var btn_cancel: Button
    private lateinit var btn_send: Button
    private lateinit var openDialog: ProgressDialog
    private var submit:String?=""
    private var message:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enquiry_form)
        back = findViewById(R.id.back)
        et_Subject = findViewById(R.id.et_Subject)
        et_Message = findViewById(R.id.et_Message)
        btn_cancel = findViewById(R.id.btn_cancel)
        btn_send = findViewById(R.id.btn_send)

        back.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
        btn_cancel.setOnClickListener(View.OnClickListener {
            et_Message.setText("")
            et_Subject.setText("")
        })

        btn_send.setOnClickListener(View.OnClickListener {
            val userId= Utils.getData(this@EnquiryForm,"user_id","1").toString()

            if (isEmpty()){
                val data = SubmitEnquiry(userId.toInt(),submit,message)
                submitForm(data)

            }
        })
    }

    private fun isEmpty():Boolean{
        submit = et_Subject.text.toString().trim()
        message=et_Message.text.toString().trim()
        if (message.isNullOrEmpty()) {
            Utils.showToast(this,"message should not be empty")
            return false
        }
        if (submit.isNullOrEmpty()) {
            Utils.showToast(this,"submit should not be empty")
            return false
        }
        return true
    }

    fun submitForm(data: SubmitEnquiry) {

        val dataManager = ServiceManager.getDataManager()

        openDialog()
        val otpCallback = object : Callback<MainResponse> {
            override fun onResponse(call: Call<MainResponse>, response: Response<MainResponse>) {
                openDialog.dismiss()
                if (response.isSuccessful) {
                    var body=response.body()
                    if(body!!.status?.toLowerCase()=="success")
                    {
                        et_Message.setText("")
                        et_Subject.setText("")
                        Utils.showToast(this@EnquiryForm, "your enquiry from submit successfully")


                    }


                    Log.e("Response", "response" + response.body().toString())

                } else {

                    // Handle error
                    println("Failed to send OTP. ${response.message()}")
                    //showToast(requireContext(),response.body()!!.message!!)
                }

            }

            override fun onFailure(call: Call<MainResponse>, t: Throwable) {
                // Handle failure
                println("Failed to send OTP. ${t.message}")
                openDialog.dismiss()
                Utils.showToast(this@EnquiryForm, "Please try again")

            }
        }

        // Call the sendOtp function in DataManager
        dataManager.submitEnquiryForm(otpCallback,data )
    }



    fun openDialog(){
        openDialog = ProgressDialog(this@EnquiryForm).apply {
            setMessage("please wait...")
            setCancelable(false)
            show()
        }

    }
}