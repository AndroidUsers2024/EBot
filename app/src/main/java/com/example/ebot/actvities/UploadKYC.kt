package com.example.ebot.actvities

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ebot.R
import com.example.ebot.common.Utils
import java.io.File

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

    private var ImageFile: File? = null
    private val PERMISSION_REQUEST_CODE: Int = 201

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kyc_screen)
        updateXML()
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
                if (btn_submit.text.toString() == "Submit" && screenNo == 2) { val intent = Intent()


                    val msg=isEmptyOrNot(screenNo!!)
                    if (msg.isEmpty()){
                        intent.setClass(this, BankAccountDetails::class.java)
                        intent.putExtra("screen", "KYC")
                        intent.putExtra("aadharNumber", aadharNumber)
                        intent.putExtra("PANNumber", PANNumber)
                        intent.putExtra("aadhaarFront", img_AadharFront_Path)
                        intent.putExtra("aadhaarBack", img_AadharBack_Path)
                        intent.putExtra("PANFront", img_PANFront_Path)
                        startActivity(intent)
                    }else{
                        Utils.showAlertDialog(this,"Alert",msg)
                    }


                } else {
                    /*screenNo = screenNo!! + 1
                    setScreens(screenNo!!)*/
                    if (screenNo==0){
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
                    }

                }

            })
            skip.setOnClickListener(View.OnClickListener {
                val intent = Intent()
               // intent.setClass(this, BankAccountDetails::class.java)
                intent.setClass(this, MainActivity::class.java)
                intent.putExtra("screen", "KYC")
                startActivity(intent)
                finish()
            })
            skip_kyc.setOnClickListener(View.OnClickListener {

            })
            back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })
            back_kyc.setOnClickListener(View.OnClickListener {
                onBackPressed()

            })
            btn_takeAadharFront.setOnClickListener(View.OnClickListener {
               openCamera("IMG_Aadhaar_Front")
            })
            btn_takeAadharBack.setOnClickListener(View.OnClickListener {
               openCamera("IMG_Aadhaar_Back")
            })
            btn_takePANFront.setOnClickListener(View.OnClickListener {
                openCamera("IMG_PAN_Card")
            })
            btn_chooseAadharFront.setOnClickListener(View.OnClickListener {
                openGallery("IMG_Aadhaar_Front")
            })
            btn_chooseAadharBack.setOnClickListener(View.OnClickListener {
                openGallery("IMG_Aadhaar_Back")
            })
            btn_choosePANFront.setOnClickListener(View.OnClickListener {
               openGallery("IMG_PAN_Card")
            })

            btn_UploadPANFront.setOnClickListener(View.OnClickListener {
                upLoadImageCancel("PAN_Card")
            })

            btn_UploadAadharFront.setOnClickListener(View.OnClickListener {
                upLoadImageCancel("Aadhaar_Front")
            })

            btn_UploadAadharBack.setOnClickListener(View.OnClickListener {
                upLoadImageCancel("Aadhaar_Back")
            })


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun isEmptyOrNot(screenNo: Int): String {
        var msg = ""
        if (screenNo == 1) {
            aadharNumber = et_AadharNumber.text.toString().trim()
            if (aadharNumber.isNullOrEmpty()) {
                msg += "Please enter your aadhaar Number\n"

            } else if (aadharNumber.toString().length != 12) {
                msg += "Please enter your 12 digits aadhaar Number\n"
            }
            if (img_AadharFront_Path.isNullOrEmpty()) {
                msg += "Please capture  Aadhaar Front Image\n"

            }
            if (img_AadharBack_Path.isNullOrEmpty()) {
                msg += "Please capture  Aadhaar Back Image\n"

            }
            if (!ck_agree_Aadhar.isChecked()) {
                msg += "Please confirm  agree KYC purpose\n"

            }

        } else if (screenNo == 2) {
            PANNumber = et_PANNumber.text.toString()
            if (PANNumber.isNullOrEmpty()) {
                msg += "Please enter your PAN Card Number\n"

            } else if (PANNumber.toString().isNotEmpty()) {
                msg += Utils.validatePAN(PANNumber.toString())
            }
            if (img_PANFront_Path.isNullOrEmpty()) {
                msg += "Please capture  PAN Front Image\n"

            }

        }
        return msg
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
    private fun openCamera(name: String) {
        tempFileName = name

        if (checkStoragePermissions()) {
            ImageFile = Utils.createImageFile(name, this)
            val photoUri =
                FileProvider.getUriForFile(this, "${packageName}.fileprovider", ImageFile!!)
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivityForResult(cameraIntent, Utils.REQUEST_CODE_CAMERA)
        }
    }

    private fun openGallery(name: String) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        tempFileName = name
        startActivityForResult(intent, Utils.REQUEST_CODE_GALLERY)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Utils.REQUEST_CODE_CAMERA -> {
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(
                            contentResolver,
                            Uri.fromFile(ImageFile)
                        ) as Bitmap
                    val path = Utils.saveImage(
                        tempFileName.toString(),
                        bitmap,
                        false,
                        this
                    ).toString()
                    setupImage(path, bitmap)
                    if (ImageFile !=null && ImageFile!!.exists()) {
                        ImageFile!!.delete()
                    }
                }

                Utils.REQUEST_CODE_GALLERY -> {
                    val selectedImageUri: Uri? = data?.data
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
                    val path = Utils.saveImage(
                        tempFileName.toString(),
                        bitmap,
                        false,
                        this
                    ).toString()
                    setupImage(path, bitmap)
                    if (ImageFile!=null && ImageFile!!.exists()) {
                        ImageFile!!.delete()
                    }

                }
            }

        }

    }
    private fun upLoadImageCancel(name: String) {
        try {
            var file: File?=null

            if (name.contains("Aadhaar_Front")) {
                img_AadharFront.setImageBitmap(null)
                file = img_AadharFront_Path?.let { File(it) }
                if (file!=null &&file.exists()){
                    file.delete()
                }
                img_AadharFront_Path=""
                btn_UploadAadharFront.visibility=View.GONE
                btn_takeAadharFront.visibility=View.VISIBLE
                btn_chooseAadharFront.visibility=View.VISIBLE
            } else if (name.contains("Aadhaar_Back")) {
                img_AadharBack.setImageBitmap(null)
                file = img_AadharBack_Path?.let { File(it) }
                if (file!=null && file.exists()){
                    file.delete()
                }
                img_AadharBack_Path=""
                btn_UploadAadharBack.visibility=View.GONE
                btn_takeAadharBack.visibility=View.VISIBLE
                btn_chooseAadharBack.visibility=View.VISIBLE
            } else if (name.contains("PAN_Card")) {
                img_PANFront.setImageBitmap(null)
                file = img_PANFront_Path?.let { File(it) }
                if (file!=null && file.exists()){
                    file.delete()
                }
                img_PANFront_Path=""
                btn_UploadPANFront.visibility=View.GONE
                btn_takePANFront.visibility=View.VISIBLE
                btn_choosePANFront.visibility=View.VISIBLE
            }
            tempFileName = ""
        } catch (e: Exception) {
            Log.e("UploadKYC.setupImage() ", e.message.toString())
        }
    }

    private fun setupImage(path: String, bitmap: Bitmap) {
        try {

            if (path.contains("IMG_Aadhaar_Front")) {
                img_AadharFront.setImageBitmap(bitmap)
                img_AadharFront_Path = path
                btn_UploadAadharFront.visibility=View.VISIBLE
                btn_takeAadharFront.visibility=View.GONE
                btn_chooseAadharFront.visibility=View.GONE
            } else if (path.contains("IMG_Aadhaar_Back")) {
                img_AadharBack.setImageBitmap(bitmap)
                img_AadharBack_Path = path
                btn_UploadAadharBack.visibility=View.VISIBLE
                btn_takeAadharBack.visibility=View.GONE
                btn_chooseAadharBack.visibility=View.GONE
            } else if (path.contains("IMG_PAN_Card")) {
                img_PANFront.setImageBitmap(bitmap)
                img_PANFront_Path = path
                btn_UploadPANFront.visibility=View.VISIBLE
                btn_takePANFront.visibility=View.GONE
                btn_choosePANFront.visibility=View.GONE

            }
            tempFileName = ""
        } catch (e: Exception) {
            Log.e("UploadKYC.setupImage() ", e.message.toString())
        }
    }

    fun checkStoragePermissions(): Boolean {
        val writePermission =
            ContextCompat.checkSelfPermission(
                this@UploadKYC,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        val readPermission =
            ContextCompat.checkSelfPermission(
                this@UploadKYC,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        val cameraPermission =
            ContextCompat.checkSelfPermission(
                this@UploadKYC,
                android.Manifest.permission.CAMERA
            )

        val listPermissionsNeeded = mutableListOf<String>()

        /* if (writePermission != PackageManager.PERMISSION_GRANTED) {
             listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
         }

         if (readPermission != PackageManager.PERMISSION_GRANTED) {
             listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
         }
         if (readPermission != PackageManager.PERMISSION_GRANTED) {
             listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
         }*/
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA)
        }

        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this@UploadKYC,
                listPermissionsNeeded.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
            return false
        }

        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            var allPermissionsGranted = true

            // Check if all permissions were granted
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false
                    break
                }
            }

            if (allPermissionsGranted) {
                Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show()
                // selectImageSource()
                openCamera(tempFileName.toString())
            } else {
                Toast.makeText(this, "Permissions Denied", Toast.LENGTH_SHORT).show()

                if (/*ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) ||*/
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.CAMERA
                    )
                ) {
                    Toast.makeText(this, "Please grant permissions to proceed", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        "Permissions denied. Go to Settings to enable them.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
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