package com.example.ebot.actvities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ebot.R

class UploadKYC : AppCompatActivity() {
    private lateinit var ll_kycIntro: LinearLayout
    private lateinit var ll_UploadAadhar: LinearLayout
    private lateinit var ll_kycUpload: LinearLayout
    private lateinit var ll_UploadPan: LinearLayout
    private lateinit var skip: TextView
    private lateinit var skip_kyc: TextView
    private lateinit var et_PANNumber: EditText
    private lateinit var et_AadharNumber: EditText
    private lateinit var back: View
    private lateinit var back_kyc: View
    private lateinit var btn_submit: Button
    private lateinit var btn_takeAadharFront: Button
    private lateinit var btn_chooseAadharFront: Button
    private lateinit var btn_takeAadharBack: Button
    private lateinit var btn_chooseAadharBack: Button
    private lateinit var btn_choosePANFront: Button
    private lateinit var btn_takePANFront: Button
    private lateinit var btn_UploadPANFront: Button
    private lateinit var btn_UploadAadharFront: Button
    private lateinit var btn_UploadAadharBack: Button
    private lateinit var ck_agree_Aadhar: CheckBox
    private lateinit var img_AadharFront: ImageView
    private lateinit var img_AadharBack: ImageView
    private lateinit var img_PANFront: ImageView
    private var screenNo: Int? = 0
    private var maskAadharNumber: String? = ""
    private var aadharNumber: String? = ""
    private var PANNumber: String? = ""
    private var img_AadharFront_Path: String? = ""
    private var img_AadharBack_Path: String? = ""
    private var img_PANFront_Path: String? = ""
    private var tempFileName: String? = ""
    private lateinit var openDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kyc_screen)
    }
    private fun updateXML() {
        try {

            ll_kycIntro = findViewById(R.id.ll_kycIntro)
            ll_kycUpload = findViewById(R.id.ll_kycUpload)
            ll_UploadAadhar = findViewById(R.id.ll_UploadAadhar)
            ll_UploadPan = findViewById(R.id.ll_UploadPan)
            skip = findViewById(R.id.skip)
            skip_kyc = findViewById(R.id.skip_kyc)
            et_PANNumber = findViewById(R.id.et_PANNumber)
            et_AadharNumber = findViewById(R.id.et_AadharNumber)
            back = findViewById(R.id.back)
            back_kyc = findViewById(R.id.back_kyc)
            btn_submit = findViewById(R.id.btn_submit)
            btn_takeAadharFront = findViewById(R.id.btn_takeAadharFront)
            btn_chooseAadharFront = findViewById(R.id.btn_chooseAadharFront)
            btn_takeAadharBack = findViewById(R.id.btn_takeAadharBack)
            btn_chooseAadharBack = findViewById(R.id.btn_chooseAadharBack)
            btn_choosePANFront = findViewById(R.id.btn_choosePANFront)
            btn_takePANFront = findViewById(R.id.btn_takePANFront)
            ck_agree_Aadhar = findViewById(R.id.ck_agree_Aadhar)
            img_AadharFront = findViewById(R.id.img_AadharFront)
            img_AadharBack = findViewById(R.id.img_AadharBack)
            img_PANFront = findViewById(R.id.img_PANFront)
            btn_UploadPANFront = findViewById(R.id.btn_UploadPANFront)
            btn_UploadAadharFront = findViewById(R.id.btn_UploadAadharFront)
            btn_UploadAadharBack = findViewById(R.id.btn_UploadAadharBack)
            btn_UploadPANFront.visibility=View.GONE
            btn_UploadAadharFront.visibility=View.GONE
            btn_UploadAadharBack.visibility=View.GONE
            // setupAadhaarMasking(et_AadharNumber)


            btn_submit.text = "Accept and Proceed"

            setScreens(screenNo!!)

            btn_submit.setOnClickListener(View.OnClickListener {
                if (btn_submit.text.toString() == "Submit" && screenNo == 2) {
                   /* val msg=isEmptyOrNot(screenNo!!)
                    if (msg.isEmpty()){
                        addKYCData()
                    }else{
                        Utils.showAlertDialog(this,"Alert",msg)
                    }*/


                } else {
                    screenNo = screenNo!! + 1
                    setScreens(screenNo!!)
                    /*if (screenNo==0){
                        screenNo = screenNo!! + 1
                        setScreens(screenNo!!)
                    } else {
                        val msg=isEmptyOrNot(1)
                        if (msg.isEmpty()){
                            screenNo = screenNo!! + 1
                            setScreens(screenNo!!)

                        }else{
                            Utils.showAlertDialog(this,"Alert",msg)
                        }
                    }*/

                }

            })
            skip.setOnClickListener(View.OnClickListener {
                val intent = Intent()
                //intent.setClass(this@MemberKYC_Details, BankAccountDetails::class.java)
                intent.setClass(this, MainActivity::class.java)
                intent.putExtra("screen", "KYC")
                startActivity(intent)
                finish()
            })
            skip_kyc.setOnClickListener(View.OnClickListener {
                val intent = Intent()
                //intent.setClass(this@MemberKYC_Details, BankAccountDetails::class.java)
                intent.setClass(this, MainActivity::class.java)
                intent.putExtra("screen", "KYC")
                startActivity(intent)
                finish()
            })
            back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })
            back_kyc.setOnClickListener(View.OnClickListener {
                onBackPressed()

            })
            btn_takeAadharFront.setOnClickListener(View.OnClickListener {
               // openCamera("IMG_Aadhaar_Front")
            })
            btn_takeAadharBack.setOnClickListener(View.OnClickListener {
             //   openCamera("IMG_Aadhaar_Back")
            })
            btn_takePANFront.setOnClickListener(View.OnClickListener {
                //openCamera("IMG_PAN_Card")
            })
            btn_chooseAadharFront.setOnClickListener(View.OnClickListener {
               // openGallery("IMG_Aadhaar_Front")
            })
            btn_chooseAadharBack.setOnClickListener(View.OnClickListener {
             //   openGallery("IMG_Aadhaar_Back")
            })
            btn_choosePANFront.setOnClickListener(View.OnClickListener {
             //   openGallery("IMG_PAN_Card")
            })

            btn_UploadPANFront.setOnClickListener(View.OnClickListener {
               // upLoadImageCancel("PAN_Card")
            })

            btn_UploadAadharFront.setOnClickListener(View.OnClickListener {
                //upLoadImageCancel("Aadhaar_Front")
            })

            btn_UploadAadharBack.setOnClickListener(View.OnClickListener {
                //upLoadImageCancel("Aadhaar_Back")
            })


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setScreens(screenNo: Int) {
        if (screenNo == 0) {
            btn_submit.text = "Accept and Proceed"
            ll_kycIntro.visibility = View.VISIBLE
            ll_kycUpload.visibility = View.GONE
            ll_UploadAadhar.visibility = View.GONE
            ll_UploadPan.visibility = View.GONE
        } else if (screenNo == 1) {
            btn_submit.setText("Submit")
            ll_kycIntro.visibility = View.GONE
            ll_kycUpload.visibility = View.VISIBLE
            ll_UploadAadhar.visibility = View.VISIBLE
            ll_UploadPan.visibility = View.GONE
            back_kyc.visibility=View.GONE

        } else if (screenNo == 2) {
            btn_submit.text = "Submit"
            ll_kycUpload.visibility = View.VISIBLE
            ll_kycIntro.visibility = View.GONE
            ll_UploadAadhar.visibility = View.GONE
            ll_UploadPan.visibility = View.VISIBLE
            back_kyc.visibility = View.VISIBLE

        }
    }

    override fun onBackPressed() {

        if (screenNo!! > 0) {
            screenNo = screenNo!! - 1
            setScreens(screenNo!!)
        } else {
            super.onBackPressed()
            finish()
        }

    }
}