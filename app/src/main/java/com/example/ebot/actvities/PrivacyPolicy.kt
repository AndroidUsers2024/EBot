package com.example.ebot.actvities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.ebot.R
import com.example.ebot.common.Utils
import com.example.ebot.models.CMS
import com.example.ebot.services.ServiceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrivacyPolicy : AppCompatActivity() {
    private lateinit var wv_back: View
    private lateinit var iv_privacy: ImageView
    private lateinit var progressbar: ProgressBar
    private lateinit var tv_content: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        updateXML()


    }
    private fun updateXML(){
        try {
            wv_back = findViewById(R.id.wv_back)
            iv_privacy = findViewById(R.id.iv_privacy)
            progressbar = findViewById(R.id.progressbar)
            tv_content = findViewById(R.id.tv_content)
            wv_back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })
            getData()

        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun getData() {
        // Obtain the DataManager instance
        val dataManager = ServiceManager.getDataManager()

        progressbar.visibility= View.VISIBLE
        // Create a callback for handling the API response
        val otpCallback = object : Callback<CMS> {
            override fun onResponse(call: Call<CMS>, response: Response<CMS>) {
                progressbar.visibility= View.GONE
                if (response.isSuccessful) {
                    if(response.body()!!.description!="")
                    {
                        Glide.with(this@PrivacyPolicy).load(response.body()!!.image)
                            .into(iv_privacy)
                        val content=  android.text.Html.fromHtml(response.body()!!.description.toString())
                        tv_content.text=content
                        return

                    }

                    Log.e("Response","response"+response.body().toString())

                } else {
                    // Handle error
                    println("Failed to send fetched data. ${response.message()}")
                    //showToast(requireContext(),response.body()!!.message!!)
                }
            }

            override fun onFailure(call: Call<CMS>, t: Throwable) {
                // Handle failure
                println("Failed to  fetchedData. ${t.message}")
                progressbar.visibility= View.GONE
                Utils.showToast(this@PrivacyPolicy,"Please try again")
            }
        }

        // Call the sendOtp function in DataManager
        dataManager.getPrivacy(otpCallback)
    }
}