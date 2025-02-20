package com.example.ebot.actvities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ebot.R
import com.example.ebot.common.Utils
import com.example.ebot.models.LoginResponse
import com.example.ebot.models.MainResponse
import com.example.ebot.models.UserCommonJson
import com.example.ebot.services.ServiceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyOTP : AppCompatActivity() {
    private lateinit var et_enter_OTP_bx1: EditText
    private lateinit var et_enter_OTP_bx2: EditText
    private lateinit var et_enter_OTP_bx3: EditText
    private lateinit var et_enter_OTP_bx4: EditText
    private lateinit var btn_OTPVerify: Button
    private lateinit var tv_phoneNumber: TextView
    private lateinit var tv_resendOTP: TextView
    private var screen:String?=""
    private var email:String?=""
    private var otp:String?=""
    private lateinit var openDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_verificationscreen)
        updateXML()
    }
    private fun updateXML(){
        try{
            et_enter_OTP_bx1=findViewById(R.id.et_enter_OTP_bx1)
            et_enter_OTP_bx2=findViewById(R.id.et_enter_OTP_bx2)
            et_enter_OTP_bx3=findViewById(R.id.et_enter_OTP_bx3)
            et_enter_OTP_bx4=findViewById(R.id.et_enter_OTP_bx4)
            btn_OTPVerify=findViewById(R.id.btn_OTPVerify)
            tv_phoneNumber=findViewById(R.id.tv_phoneNumber)
            tv_resendOTP=findViewById(R.id.tv_resendOTP)
            screen =intent.getStringExtra("screen")
            email =intent.getStringExtra("email")
            tv_phoneNumber.setText(email)
            

            btn_OTPVerify.setOnClickListener(View.OnClickListener {
                if (screen.equals("login")){
                    if (Utils.isNetworkAvailable(this)){
                        isEmpty()
                    }else{
                        Utils.showAlertDialog(this@VerifyOTP,"alert","Please check network verification")
                    }
                }else{
                    val intent= Intent(this,PersonalDetails::class.java)
                    intent.putExtra("email",email)
                    intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }



            })
            tv_resendOTP.setOnClickListener(View.OnClickListener {


            })

        }catch (e:Exception){
            Log.e("VerifyOTP.updateXML: ",e.message.toString())
        }


        
        
    }
    private fun isEmpty(){
        try {
            otp =et_enter_OTP_bx1.text.toString().trim()+
                    et_enter_OTP_bx2.text.toString().trim()+
                    et_enter_OTP_bx3.text.toString().trim()+
                    et_enter_OTP_bx4.text.toString().trim()
            if (otp.isNullOrEmpty()){
                Utils.showAlertDialog(this@VerifyOTP,"Alert","Please enter OTP")
            }else if (otp!!.isNotEmpty()&&otp!!.length<4){
                Utils.showAlertDialog(this@VerifyOTP,"Alert","Please enter 4 digits OTP")

            }else{
                verifyOTP(email!!,otp!!)
            }

        }catch (e:Exception){
            Log.e("VerifyOTP.isEmpty() : ",e.message.toString())
        }
    }
    private fun verifyOTP(email:String,otp:String){
        try {
            openDialog= Utils.  openDialog(this)
            val dataManager = ServiceManager.getDataManager()
            val otpCallback = object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (openDialog.isShowing){
                        openDialog.dismiss()
                    }
                    if (response.isSuccessful) {
                        val body=response.body()
                        if (body!!.status == "success" ){
                            if (screen.equals("login")){
                                Utils.saveData(this@VerifyOTP,"user_id",body.user.id)
                                Utils.saveData(this@VerifyOTP,"email",body.user.email)
                                val intent= Intent(this@VerifyOTP,MainActivity::class.java)
                                intent.putExtra("email",email)
                                startActivity(intent)
                                finish()
                            }else{
                                val intent= Intent(this@VerifyOTP,PersonalDetails::class.java)
                                intent.putExtra("email",email)
                                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                                startActivity(intent)
                                finish()
                            }
                            Log.e("Response", "response" + response.body().toString())

                        }else{
                            println("Failed to verify OTP. ${response.message()}")
                            Utils.showToast(this@VerifyOTP, body.message!!)
                        }


                    } else {
                        println("Failed to verify OTP. ${response.message()}")
                        Utils.showToast(this@VerifyOTP,"Failed to send OTP. ${response.message()}")
                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    // Handle failure
                    println("Failed to verify OTP. ${t.message}")
                    if (openDialog.isShowing){
                        openDialog.dismiss()
                    }
                    Utils.showToast(this@VerifyOTP, "Please try again")

                }
            }

            // Call the sendOtp function in DataManager
            val req= UserCommonJson(email=email, otp =otp )
            dataManager.verifyOTP(otpCallback,req )


        }catch (e:Exception){
            if (openDialog.isShowing){
                openDialog.dismiss()
            }
            Log.e("LoginAPI ",e.message.toString())
        }
    }

}