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
import com.example.ebot.R
import com.example.ebot.common.Utils
import com.example.ebot.models.MainResponse
import com.example.ebot.models.RegisterData
import com.example.ebot.models.RegisterResponse
import com.example.ebot.services.ServiceManager
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactDetails : AppCompatActivity() {
    private lateinit var et_address: EditText
    private lateinit var et_emailId: EditText
    private lateinit var et_pinCode: EditText
    private lateinit var et_city: EditText
    private lateinit var et_state: EditText
    private lateinit var et_country: EditText
    private lateinit var et_mobileNumber: EditText
    private lateinit var back: View
    private lateinit var btn_Next: Button
    private var firstName: String? = ""
    private var lastName: String? = ""
    private var gender: String? = ""
    private var dob: String? = ""
    private var address: String? = ""
    private var emailId: String? = ""
    private var pinCode: String? = ""
    private var city: String? = ""
    private var state: String? = ""
    private var country: String? = ""
    private var mobileNumber: String? = ""
    private lateinit var openDialog:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contactdetails_screen)
        updateXML()
    }

    private fun updateXML() {
        try {
            et_address = findViewById(R.id.et_address)
            et_emailId = findViewById(R.id.et_emailId)
            et_pinCode = findViewById(R.id.et_pinCode)
            et_city = findViewById(R.id.et_city)
            et_state = findViewById(R.id.et_state)
            et_country = findViewById(R.id.et_country)
            et_mobileNumber = findViewById(R.id.et_mobileNumber)
            btn_Next = findViewById(R.id.btn_Next)
            back = findViewById(R.id.back)
            firstName = intent.getStringExtra("firstName")
            lastName = intent.getStringExtra("lastName")
            gender = intent.getStringExtra("gender")
            dob = intent.getStringExtra("dob")
            emailId = intent.getStringExtra("email")

            if (!emailId.isNullOrEmpty()) {
                et_emailId.setText(emailId)
                et_emailId.isEnabled = false
            } else {
                et_emailId.setText("")
                et_emailId.isEnabled = true
            }

            back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })
            btn_Next.setOnClickListener(View.OnClickListener {
                val msg = isEmpty()
                if (msg.isEmpty()) {
                    // save
                    if (Utils.isNetworkAvailable(this)){
                        val registerData=RegisterData(firstName,lastName,gender,dob,mobileNumber,emailId,address,pinCode,city,state,country)
                        /*val intent = Intent(this, UploadKYC::class.java)
                        startActivity(intent)*/
                        registrationAPI(registerData)
                    }else{
                        Utils.showToast(this,"Please check network connection")
                    }

                } else {
                    Utils.showAlertDialog(this, "Alert", msg)

                }
            })


        } catch (e: Exception) {
            Log.e("ContactDetails.updateXML() : ", e.message.toString())
        }
    }

    private fun isEmpty(): String {
        var msg = ""
        mobileNumber = et_mobileNumber.text.toString().trim()
        emailId = et_emailId.text.toString().trim()
        address = et_address.text.toString().trim()
        pinCode = et_pinCode.text.toString().trim()
        city = et_city.text.toString().trim()
        state = et_state.text.toString().trim()
        country = et_state.text.toString().trim()

        if (mobileNumber.isNullOrEmpty()) {
            msg += "Please enter your mobile number\n"
        } else if (mobileNumber!!.isNotEmpty() && !Utils.isValidMobileNumber(mobileNumber!!)) {
            msg += "Please enter Valid mobile number\n"
        }
        if (emailId.isNullOrEmpty()) {
            msg += "Please enter your email Id\n"
        } else {
            msg += Utils.isValidEmail(emailId!!)
        }
        if (address.isNullOrEmpty()) {
            msg += "Please enter your address\n"
        }
        if (pinCode.isNullOrEmpty()) {
            msg += "Please enter your pinCode\n"
        }
        if (city.isNullOrEmpty()) {
            msg += "Please enter your city\n"
        }
        if (state.isNullOrEmpty()) {
            msg += "Please enter your state\n"
        }
        if (country.isNullOrEmpty()) {
            msg += "Please enter your country\n"
        }

        return msg;

    }
    private fun registrationAPI(registerData: RegisterData){
        try{
            openDialog = Utils.openDialog(this)
            val service = ServiceManager.getDataManager()
            val callback = object : Callback<MainResponse> {
                override fun onResponse(
                    call: Call<MainResponse>,
                    response: Response<MainResponse>
                ) {
                    if (openDialog.isShowing){
                        openDialog.dismiss()

                    }
                    if (response.isSuccessful) {
//                        if (response.body()?.status!!.equals("success")) {
                            val body = response.body()
                            Utils.showToast(this@ContactDetails, body!!.message.toString())
                            val intent = Intent()
                            intent.setClass(this@ContactDetails, UploadKYC::class.java)
                            intent.putExtra("screen", 0)
                            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                            finish()

                        /*}else{
                            Utils.showToast(this@ContactDetails, response.body()!!.message!!)
                        }*/


                        Log.e("Response", "response" + response.message().toString())


                    } else {

                        println("Failed to send OTP. ${response.message()}")
                        Utils.showToast(this@ContactDetails, response.body()!!.message!!)

                    }
                }

                override fun onFailure(call: Call<MainResponse>, t: Throwable) {
                    println("Failed to send OTP. ${t.message}")
                    if (openDialog.isShowing){
                        openDialog.dismiss()

                    }
                    Utils.showToast(this@ContactDetails, "Please try again")
                }

            }
            val userID = Utils.getData(this@ContactDetails, "user_id", "") as String
            val user_id = RequestBody.create("text/plain".toMediaType(), userID)
            val name = RequestBody.create("text/plain".toMediaType(), registerData.name!!)
            val last_name = RequestBody.create("text/plain".toMediaType(), registerData.last_name!!)
            val gender = RequestBody.create("text/plain".toMediaType(), registerData.gender!!)
            val dob = RequestBody.create("text/plain".toMediaType(), registerData.dob!!)
            val phone = RequestBody.create("text/plain".toMediaType(), registerData.phone!!)
            val email = RequestBody.create("text/plain".toMediaType(), registerData.email!!)
            val address = RequestBody.create("text/plain".toMediaType(), registerData.address!!)
            val pin_code = RequestBody.create("text/plain".toMediaType(), registerData.pin_code!!)
            val city = RequestBody.create("text/plain".toMediaType(), registerData.city!!)
            val state = RequestBody.create("text/plain".toMediaType(), registerData.state!!)
            val country = RequestBody.create("text/plain".toMediaType(), registerData.country!!)
            service.updateProfile(
                callback,
                user_id, name, last_name, gender, dob, phone, email, address, pin_code, city, state, country, null
            )
//            service.registerUser(callback,registerData)
        }catch (e:Exception){
            if (openDialog.isShowing){
                openDialog.dismiss()

            }
         Log.e("ContactDetails.RegisterAPI ",e.message.toString())
        }
    }
}