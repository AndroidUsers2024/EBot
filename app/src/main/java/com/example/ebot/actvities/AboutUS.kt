package com.example.ebot.actvities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ebot.R
import com.example.ebot.common.Utils
import com.example.ebot.models.CMS
import com.example.ebot.services.ServiceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AboutUS : AppCompatActivity() {
    private lateinit var wv_back: View
    private lateinit var iv_about: ImageView
    private lateinit var webview: WebView
    private lateinit var progressbar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        updateXML()
    }
    private fun updateXML(){
        try {
            wv_back = findViewById(R.id.wv_back)
            iv_about = findViewById(R.id.iv_about)
            webview = findViewById(R.id.webview)
            progressbar = findViewById(R.id.progressbar)
            progressbar.visibility= View.GONE
            getData()
            wv_back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun getData() {
        // Obtain the DataManager instance
        val dataManager = ServiceManager.getDataManager()

        progressbar.visibility= View.VISIBLE
        // Create a callback for handling the API response
        val callback = object : Callback<CMS> {
            override fun onResponse(call: Call<CMS>, response: Response<CMS>) {
                progressbar.visibility= View.GONE
                if (response.isSuccessful) {
                    if(response.body()!!.description!="")
                    {
                        Glide.with(this@AboutUS).load(response.body()!!.image)
                        .into(iv_about)
                        webview.loadData(response.body()!!.description.toString(),"text/html","utf-8")
                        return

                    }
//                    Utils.showToast(this@AboutUS,response.body()!!.message!!)

                    Log.e("Response","response"+response.body().toString())

                } else {
                    // Handle error
                    println("Failed to getData  ${response.message()}")
                    //showToast(requireContext(),response.body()!!.message!!)
                }
            }

            override fun onFailure(call: Call<CMS>, t: Throwable) {
                // Handle failure
                println("Failed to getData. ${t.message}")
                progressbar.visibility= View.GONE
                Utils.showToast(this@AboutUS,"Please try again")
            }
        }

        // Call the sendOtp function in DataManager
        dataManager.getAbout(callback)
    }
}