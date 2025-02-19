package com.example.ebot.actvities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.ebot.R
import com.example.ebot.common.Utils
import com.example.ebot.models.Contact
import com.example.ebot.services.ServiceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactUS : AppCompatActivity() {
    private lateinit var back: View
    private lateinit var progressbar: ProgressBar
    private lateinit var tv_Address: TextView
    private lateinit var tv_landlineNo: TextView
    private lateinit var tv_mobileNo1: TextView
    private lateinit var tv_mobileNo2: TextView
    private lateinit var tv_email: TextView
    private lateinit var ll_ContactDetails: LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
        updateXML()

    }

    private fun updateXML() {
        try {
            back = findViewById(R.id.back)
            tv_Address = findViewById(R.id.tv_Address)
            tv_landlineNo = findViewById(R.id.tv_landlineNo)
            tv_mobileNo1 = findViewById(R.id.tv_mobileNo1)
            tv_mobileNo2 = findViewById(R.id.tv_mobileNo2)
            tv_email = findViewById(R.id.tv_email)
            progressbar = findViewById(R.id.progressbar)
            ll_ContactDetails = findViewById(R.id.ll_ContactDetails)
            ll_ContactDetails.visibility = View.GONE

            back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })
            getContactData()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getContactData(){
        try {
            val dataManager=ServiceManager.getDataManager()

            progressbar.visibility=View.VISIBLE
            val callback = object : Callback<Contact> {
                override fun onResponse(
                    call: Call<Contact>,
                    response: Response<Contact>
                ) {
                    progressbar.visibility=View.GONE
                    if (response.isSuccessful)
                    {
                        ll_ContactDetails.visibility=View.VISIBLE
                            val data = response.body()!!
                            setData(data)

                        Log.e("Response","response"+response.body().toString())

                    }else{
                        println("Failed to contact Detail  ${response.message()}")
                        Utils.showToast(this@ContactUS,"Failed to contact Detail  ${response.message()}")


                    }
                }

                override fun onFailure(call: Call<Contact>, t: Throwable) {
                    println("Failed to contact Detail  ${t.message}")
                    progressbar.visibility=View.GONE
                    Utils.showToast(this@ContactUS,"Please try again")

                }

            }


            dataManager.getContact(callback)
        }catch (e:Exception){
            Log.e("com.propertizone_it.Activities.ContactUS",e.toString())
        }
    }

    private fun setData(data: Contact){
        try {
            tv_email.text=data.email
            tv_Address.text=data.address
            tv_landlineNo.text=data.landline_no
            tv_mobileNo1.text=data.phone
            tv_mobileNo2.text=data.phone_2
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}