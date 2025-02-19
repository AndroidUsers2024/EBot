package com.example.ebot.actvities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebot.R
import com.example.ebot.adapters.FAQAdapter
import com.example.ebot.common.Utils
import com.example.ebot.models.FAQsMainResponse
import com.example.ebot.models.Faqs
import com.example.ebot.services.ServiceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FAQ : AppCompatActivity() {
    private lateinit var wv_back: View
    private lateinit var et_search: EditText
    private lateinit var rc_faq_list: RecyclerView
    private lateinit var progressbar: ProgressBar
    lateinit var faqAdapter: FAQAdapter
    var list: ArrayList<Faqs> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        updateXML()
    }

    private fun updateXML() {
        try {
            wv_back = findViewById(R.id.wv_back)
            et_search = findViewById(R.id.et_search)
            rc_faq_list = findViewById(R.id.rc_faq_list)
            progressbar = findViewById(R.id.progressbar)

            wv_back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })
            rc_faq_list.layoutManager= LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            faqAdapter= FAQAdapter(list,this)
            rc_faq_list.adapter=faqAdapter
//            val userId= Utils.getData(this@FAQ,"user_id","").toString()
            faqs()
            et_search.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    filterFaqs(s.toString())
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun faqs() {
        // Obtain the DataManager instance
        val dataManager = ServiceManager.getDataManager()
        progressbar.visibility= View.VISIBLE
        // Create a callback for handling the API response
        val otpCallback = object : Callback<List<Faqs>> {
            override fun onResponse(call: Call<List<Faqs>>, response: Response<List<Faqs>>) {
                progressbar.visibility= View.GONE
                if (response.isSuccessful) {

                    list.clear()
                    list.addAll(response.body()!!)
                    faqAdapter.updateData(list)

                    Log.e("Response","response"+response.body().toString())

                } else {
                    // Handle error
                    println("Failed to send OTP. ${response.message()}")
                    //showToast(requireContext(),response.body()!!.message!!)
                }
            }

            override fun onFailure(call: Call<List<Faqs>>, t: Throwable) {
                // Handle failure
                println("Failed to send OTP. ${t.message}")
                progressbar.visibility= View.GONE
//                showToast(this@FAQ,"Please try again")
            }
        }

        // Call the sendOtp function in DataManager
        dataManager.faqs(otpCallback)
    }

    private fun filterFaqs(query: String?) {
        val filteredFaqs = if (!query.isNullOrEmpty()) {
            list.filter {
                it.description!!.contains(query, ignoreCase = true) ||
                        it.title!!.contains(query, ignoreCase = true)
            }
        } else {
            list
        }
        faqAdapter.updateData(filteredFaqs)
    }

}