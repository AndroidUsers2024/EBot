package com.example.ebot.actvities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ebot.R
import com.example.ebot.common.Utils
import com.example.ebot.models.RegisterData
import com.example.ebot.models.RegisterResponse
import com.example.ebot.services.ServiceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupWithEmail : AppCompatActivity() {
    private lateinit var et_emailId: EditText
    private lateinit var btn_sendOTP: Button
    private lateinit var tv_Login: TextView
    private lateinit var back: View

    private lateinit var openDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_with_email)
        updateXML()

    }
    private fun updateXML(){
        try{
            et_emailId=findViewById(R.id.et_emailId)
            btn_sendOTP=findViewById(R.id.btn_sendOTP)
            tv_Login=findViewById(R.id.tv_Login)
            back=findViewById(R.id.back)

            btn_sendOTP.setOnClickListener(View.OnClickListener {
                val email =et_emailId.text.toString().trim()
                val isValid= Utils.isValidEmail(email)
                if (email.isEmpty()){
                    Utils.showToast(this,"Please enter your email")
                }else if(isValid.isNotEmpty()){
                    Utils.showToast(this,isValid)
                }else{
                    val registerData=RegisterData("","","","","",et_emailId.text.toString(),"","","","","")

                    registrationAPI(registerData)

                    /*val intent= Intent(this,VerifyOTP::class.java)
                    intent.putExtra("screen","signup")
                    intent.putExtra("email",email)
                    startActivity(intent)*/
                }

            })
            tv_Login.setOnClickListener(View.OnClickListener {
                val intent= Intent(this,LoginActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            })
            back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })

        }catch (e:Exception){
            Log.e("SignupWithEmail.updateXML: ",e.message.toString())
        }
    }

    private fun registrationAPI(registerData: RegisterData){
        try{
            openDialog = Utils.openDialog(this)
            val service = ServiceManager.getDataManager()
            val callback = object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (openDialog.isShowing){
                        openDialog.dismiss()

                    }
                    if (response.isSuccessful) {
                        if (response.body()?.status!!.equals("success")) {
                        val body = response.body()
                        Utils.showToast(this@SignupWithEmail, body!!.message.toString())
                        Utils.saveData(this@SignupWithEmail,"user_id",body.user_id)
                        /*val intent= Intent(this@SignupWithEmail,VerifyOTP::class.java)
                        intent.putExtra("screen","signup")
                        intent.putExtra("email",et_emailId.text.toString())
                        startActivity(intent)*/

                        val intent= Intent(this@SignupWithEmail,PersonalDetails::class.java)
                        intent.putExtra("email",et_emailId.text.toString())
                        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)

                        }else{
                            Utils.showToast(this@SignupWithEmail, response.body()!!.message!!)
                        }

                        Log.e("Response", "response" + response.message().toString())

                    } else {

                        println("Failed to send OTP. ${response.message()}")
                        Utils.showToast(this@SignupWithEmail, response.body()!!.message!!)

                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    println("Failed to send OTP. ${t.message}")
                    if (openDialog.isShowing){
                        openDialog.dismiss()

                    }
                    Utils.showToast(this@SignupWithEmail, "Please try again")
                }

            }
            service.registerUser(callback,registerData)
        }catch (e:Exception){
            if (openDialog.isShowing){
                openDialog.dismiss()

            }
            Log.e("ContactDetails.RegisterAPI ",e.message.toString())
        }
    }
}