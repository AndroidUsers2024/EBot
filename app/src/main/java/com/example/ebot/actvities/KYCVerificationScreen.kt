package com.example.ebot.actvities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ebot.R

class KYCVerificationScreen : AppCompatActivity() {
    private lateinit var rl_Approved: RelativeLayout
    private lateinit var back: View
    private lateinit var vw_status: View
    private lateinit var vw_progress: View
    private lateinit var vw_wait: View
    private lateinit var skip: TextView
    private lateinit var tv_progress: TextView
    private lateinit var tv_wait: TextView
    private lateinit var tv_status: TextView
    private lateinit var img_PersonPhoto: ImageView
    private lateinit var btn_takePhoto: Button
    private lateinit var btn_submit: Button
    private lateinit var ll_verificationStatus: LinearLayout
    private lateinit var ll_faceVerification: LinearLayout
    private lateinit var ll_submission: LinearLayout
    private var screenNo:Int?=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kyc_verification_screen)
        updateXML()
    }
    private fun updateXML(){
        try {
            rl_Approved= findViewById(R.id.rl_Approved)
            ll_faceVerification= findViewById(R.id.ll_faceVerification)
            back= findViewById(R.id.back)
            btn_submit= findViewById(R.id.btn_submit)
            vw_status= findViewById(R.id.vw_status)
            skip= findViewById(R.id.skip)
            vw_wait= findViewById(R.id.vw_wait)
            vw_progress= findViewById(R.id.vw_progress)
            tv_status= findViewById(R.id.tv_status)
            img_PersonPhoto= findViewById(R.id.img_PersonPhoto)
            btn_takePhoto= findViewById(R.id.btn_takePhoto)
            ll_verificationStatus= findViewById(R.id.ll_verificationStatus)
            ll_submission= findViewById(R.id.ll_submission)
            tv_progress= findViewById(R.id.tv_progress)
            tv_wait= findViewById(R.id.tv_wait)

            ll_faceVerification.visibility=View.GONE
            ll_submission.visibility=View.GONE
            rl_Approved.visibility=View.GONE
            ll_verificationStatus.visibility=View.GONE
            btn_submit.isEnabled=false
            setScreens(screenNo!!)

            btn_takePhoto.setOnClickListener(View.OnClickListener {
                btn_submit.isEnabled=true
            })
            btn_submit.setOnClickListener(
                View.OnClickListener {
                    if (btn_submit.text.toString()=="Home"&&screenNo==2) {
                        val intent= Intent()
                        intent.setClass(this,MainActivity::class.java)
                        startActivity(intent)

                    }else{
                        screenNo= screenNo!! +1
                        setScreens(screenNo!!)

                    }
                }
            )


        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    private fun setScreens(screen:Int){
        if (screen==0){
            btn_submit.text = "Submit"
            btn_submit.visibility=View.VISIBLE
            ll_faceVerification.visibility=View.VISIBLE
            ll_submission.visibility=View.GONE
            rl_Approved.visibility=View.GONE
        }else if (screen==1){
            btn_submit.visibility=View.GONE
            ll_faceVerification.visibility=View.GONE
            ll_submission.visibility=View.VISIBLE
            rl_Approved.visibility=View.GONE

            Handler().postDelayed({
                screenNo= screenNo!! +1
                setScreens(screenNo!!)
            }, 5000)

        } else if (screen==2){
            btn_submit.text = "Home"
            btn_submit.visibility=View.VISIBLE
            ll_faceVerification.visibility=View.GONE
            ll_submission.visibility=View.GONE
            rl_Approved.visibility=View.VISIBLE

        }
    }

    override fun onBackPressed() {

        if (screenNo!!>0){
            screenNo= screenNo!! -1
            setScreens(screenNo!!)
        }else {
            super.onBackPressed()
            finish()
        }

    }
}