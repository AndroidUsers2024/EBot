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
import com.example.ebot.models.UserCommonJson
import com.example.ebot.services.ServiceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var et_emailId:EditText
    private lateinit var btn_sendOTP:Button
    private lateinit var tv_signUp:TextView
    private lateinit var openDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        updateXML()

    }
    private fun updateXML(){
        try{
            et_emailId=findViewById(R.id.et_emailId)
            btn_sendOTP=findViewById(R.id.btn_sendOTP)
            tv_signUp=findViewById(R.id.tv_signUp)

            btn_sendOTP.setOnClickListener(View.OnClickListener {
                val email =et_emailId.text.toString().trim()
                val isValid=Utils.isValidEmail(email)
                if (email.isEmpty()){
                    Utils.showToast(this,"Please enter your email")
                }else if(isValid.isNotEmpty()){
                    Utils.showToast(this,isValid)
                }else{
                    if (Utils.isNetworkAvailable(this)){
                        loginUser(email)
                    }else{
                        Utils.showToast(this,"Please check network connection")
                    }
                }

            })
            tv_signUp.setOnClickListener(View.OnClickListener {
                val intent=Intent(this,SignupWithEmail::class.java)
                startActivity(intent)
            })

        }catch (e:Exception){
            Log.e("LoginActivity.updateXML: ",e.message.toString())
        }
    }

    private fun loginUser(email:String){
        try {
            openDialog=Utils.  openDialog(this)
            val dataManager = ServiceManager.getDataManager()
            val otpCallback = object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (openDialog.isShowing){
                        openDialog.dismiss()
                    }
                    if (response.isSuccessful) {
                        val body=response.body()
                        if (body!!.status == "success" ){
                            val intent=Intent(this@LoginActivity,VerifyOTP::class.java)
                            intent.putExtra("screen","login")
                            intent.putExtra("email",email)
                            startActivity(intent)
                            Log.e("Response", "response" + response.body().toString())

                        }else{
                            println("Failed to send OTP. ${response.message()}")
                            Utils.showToast(this@LoginActivity, body.message!!)
                        }


                    } else {
                        println("Failed to send OTP. ${response.message()}")
                        Utils.showToast(this@LoginActivity,"Failed to send OTP. ${response.message()}")
                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    // Handle failure
                    println("Failed to send OTP. ${t.message}")
                    if (openDialog.isShowing){
                        openDialog.dismiss()
                    }
                    Utils.showToast(this@LoginActivity, "Please try again")

                }
            }

            // Call the sendOtp function in DataManager
            val req=UserCommonJson(email=email)
            dataManager.loginUser(otpCallback,req )


        }catch (e:Exception){
            if (openDialog.isShowing){
                openDialog.dismiss()
            }
            Log.e("LoginAPI ",e.message.toString())
        }
    }

}