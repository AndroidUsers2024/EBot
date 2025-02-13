package com.example.ebot.actvities

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
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
import com.bumptech.glide.Glide
import com.example.ebot.R
import com.example.ebot.common.Utils
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class KYCDetailsView : AppCompatActivity() {
    private lateinit var wv_back: View
    private lateinit var vw_shownAadhar: View
    private lateinit var vw_showPAN: View
    private lateinit var tv_imgeinfo: TextView
    private lateinit var tv_kyc_status: TextView
    private lateinit var tv_kyc_Number: TextView
    private lateinit var et_AadharNumber: EditText
    private lateinit var et_PANNumber: EditText
    private lateinit var ll_AadharCard: LinearLayout
    private lateinit var ll_AadharFontButtons: LinearLayout
    private lateinit var ll_AadharBackButtons: LinearLayout
    private lateinit var ll_PAnCardDetail: LinearLayout
    private lateinit var ll_AadharCardDetail: LinearLayout
    private lateinit var ll_aadharNum: LinearLayout
    private lateinit var ll_pan_no: LinearLayout
    private lateinit var ll_pan_buttons: LinearLayout
    private lateinit var ll_PanCard: LinearLayout
    private lateinit var img_AadharFront: ShapeableImageView
    private lateinit var img_AadharBack: ShapeableImageView
    private lateinit var img_PANFront: ShapeableImageView
    private lateinit var btn_takeAadharFront: Button
    private lateinit var btn_chooseAadharFront: Button
    private lateinit var btn_UploadAadharFront: Button
    private lateinit var btn_takeAadharBack: Button
    private lateinit var btn_chooseAadharBack: Button
    private lateinit var btn_UploadAadharBack: Button
    private lateinit var tv_UpdateAadhar: TextView
    private lateinit var btn_UpdateAadhar: Button
    private lateinit var btn_UpdateKYC: Button
    private lateinit var btn_takePANFront: Button
    private lateinit var btn_choosePANFront: Button
    private lateinit var btn_UploadPANFront: Button
    private lateinit var tv_UpdatePAN: TextView
    private lateinit var btn_UpdatePAN: Button
    private var maskAadharNumber: String? = ""
    private var aadharNumber: String? = ""
    private var PANNumber: String? = ""
    private var img_AadharFront_Path: String? = ""
    private var img_AadharBack_Path: String? = ""
    private var img_PANFront_Path: String? = ""
    private var tempFileName: String? = ""
    private lateinit var openDialog: ProgressDialog

    private var ImageFile: File? = null
    private var isAadharOpen: Boolean? = false
    private var isShowAadhar: Boolean? = false
    private var isPANOpen: Boolean? = false
    private var isShowPAN: Boolean? = false
    private val PERMISSION_REQUEST_CODE: Int = 201

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kyc_details_view)
        updateXML()
    }

    private fun updateXML() {
        try {
            wv_back = findViewById(R.id.wv_back)
            vw_shownAadhar = findViewById(R.id.vw_shownAadhar)
            vw_showPAN = findViewById(R.id.vw_showPAN)
            tv_imgeinfo = findViewById(R.id.tv_imgeinfo)
            tv_kyc_status = findViewById(R.id.tv_kyc_status)
            tv_kyc_Number = findViewById(R.id.tv_kyc_Number)
            et_AadharNumber = findViewById(R.id.et_AadharNumber)
            et_PANNumber = findViewById(R.id.et_PANNumber)
            ll_AadharCard = findViewById(R.id.ll_AadharCard)
            ll_pan_buttons = findViewById(R.id.ll_pan_buttons)
            ll_AadharFontButtons = findViewById(R.id.ll_AadharFontButtons)
            ll_AadharBackButtons = findViewById(R.id.ll_AadharBackButtons)
            ll_PAnCardDetail = findViewById(R.id.ll_PAnCardDetail)
            ll_AadharCardDetail = findViewById(R.id.ll_AadharCardDetail)
            ll_PanCard = findViewById(R.id.ll_PanCard)
            ll_aadharNum = findViewById(R.id.ll_aadharNum)
            img_AadharFront = findViewById(R.id.img_AadharFront)
            img_AadharBack = findViewById(R.id.img_AadharBack)
            img_PANFront = findViewById(R.id.img_PANFront)
            ll_pan_no = findViewById(R.id.ll_pan_no)
            btn_takeAadharFront = findViewById(R.id.btn_takeAadharFront)
            btn_chooseAadharFront = findViewById(R.id.btn_chooseAadharFront)
            btn_UploadAadharFront = findViewById(R.id.btn_UploadAadharFront)
            btn_takeAadharBack = findViewById(R.id.btn_takeAadharBack)
            btn_chooseAadharBack = findViewById(R.id.btn_chooseAadharBack)
            btn_UploadAadharBack = findViewById(R.id.btn_UploadAadharBack)
            tv_UpdateAadhar = findViewById(R.id.tv_UpdateAadhar)
            btn_UpdateAadhar = findViewById(R.id.btn_UpdateAadhar)
            btn_UpdateKYC = findViewById(R.id.btn_UpdateKYC)
            btn_takePANFront = findViewById(R.id.btn_takePANFront)
            btn_choosePANFront = findViewById(R.id.btn_choosePANFront)
            btn_UploadPANFront = findViewById(R.id.btn_UploadPANFront)
            tv_UpdatePAN = findViewById(R.id.tv_UpdatePAN)
            btn_UpdatePAN = findViewById(R.id.btn_UpdatePAN)
            ll_PAnCardDetail.visibility = View.GONE
            ll_AadharCardDetail.visibility = View.GONE
            btn_UpdateKYC.visibility = View.GONE

            vw_shownAadhar.setBackgroundResource(R.drawable.down_arrow)
            isAadharOpen = false
            vw_showPAN.setBackgroundResource(R.drawable.down_arrow)
            isPANOpen = false
            isShowAadhar=showUpdatePANDetails(false)
            isShowPAN=showUpdateKYCDetails(false)


            ll_AadharCard.setOnClickListener(View.OnClickListener {
                if (isAadharOpen == false) {
                    vw_shownAadhar.setBackgroundResource(R.drawable.ic_arrow_up)
                    isAadharOpen = true
                    ll_AadharCardDetail.visibility = View.VISIBLE
                } else {
                    vw_shownAadhar.setBackgroundResource(R.drawable.down_arrow)
                    isAadharOpen = false
                    ll_AadharCardDetail.visibility = View.GONE

                }

            })
            ll_PanCard.setOnClickListener(View.OnClickListener {
                if (isPANOpen == false) {
                    vw_showPAN.setBackgroundResource(R.drawable.ic_arrow_up)
                    isPANOpen = true
                    ll_PAnCardDetail.visibility = View.VISIBLE
                } else {
                    vw_showPAN.setBackgroundResource(R.drawable.down_arrow)
                    isPANOpen = false
                    ll_PAnCardDetail.visibility = View.GONE

                }


            })
            wv_back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })
            tv_UpdateAadhar.setOnClickListener(View.OnClickListener {
                if (isShowAadhar==false){
                    isShowAadhar= showUpdateKYCDetails(true)
                }else{
                    isShowAadhar= showUpdateKYCDetails(false)

                }
            })
            tv_UpdatePAN.setOnClickListener(View.OnClickListener {
                if (isShowPAN ==false){
                    isShowPAN=  showUpdatePANDetails(true)
                }else{
                    isShowPAN=  showUpdatePANDetails(false)

                }
            })

            btn_takeAadharFront.setOnClickListener(View.OnClickListener {
                openCamera("IMG_Aadhaar_Front")
            })
            img_AadharFront.setOnClickListener(View.OnClickListener {
              //  Utils.popUpFullViewImage(img_AadharFront_Path,this)
            })
            img_AadharBack.setOnClickListener(View.OnClickListener {
              //  Utils.popUpFullViewImage(img_AadharBack_Path,this)
            })
            img_PANFront.setOnClickListener(View.OnClickListener {
              //  Utils.popUpFullViewImage(img_PANFront_Path,this)
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
            val userID = Utils.getData(this@KYCDetailsView, "user_id", "") as String

           // getKYCDetailsAPI(userID)
            btn_UpdateAadhar.setOnClickListener(View.OnClickListener {
                /*val msg=isEmptyOrNot(isShowAadhar!!,isShowPAN!!)
                if (msg.isEmpty()){
                    updateKYCData()

                }else{
                    Utils.showAlertDialog(this,"Alert",msg)
                }*/
            })
            btn_UpdateKYC.setOnClickListener(View.OnClickListener {
              /*  val msg=isEmptyOrNot(true,false)
                if (msg.isEmpty()){
                    updateKYCData()

                }else{
                    Utils.showAlertDialog(this,"Alert",msg)
                }*/
            })
            btn_UpdatePAN.setOnClickListener(View.OnClickListener {
               /* val msg=isEmptyOrNot(false,true)
                if (msg.isEmpty()){
                    updateKYCData()

                }else{
                    Utils.showAlertDialog(this,"Alert",msg)
                }*/
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isEmptyOrNot(isKYC: Boolean,isPAN: Boolean): String {
        aadharNumber = et_AadharNumber.text.toString().trim()
        PANNumber = et_PANNumber.text.toString()


        var msg = ""
        if (isKYC) {
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


        }
        if (isPAN) {
            if (PANNumber.isNullOrEmpty()) {
                msg += "Please enter your PAN Card Number\n"

            } else if (PANNumber.toString().isNotEmpty()) {
               // msg += Utils.validatePAN(PANNumber.toString())
            }
            if (img_PANFront_Path.isNullOrEmpty()) {
                msg += "Please capture  PAN Front Image\n"

            }

        }
        return msg
    }

    private fun selectImageSource(name: String) {
        val options = arrayOf("Camera", "Gallery")
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Select Image Source")
            .setIcon(R.drawable.ic_galary)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openCamera(name)
                    1 -> openGallery(name)
                }
            }
            .show()
    }

    private fun openCamera(name: String) {
        if (checkStoragePermissions()) {
            tempFileName = name
           // ImageFile = Utils.createImageFile(name, this@KYCDetailsView)
            val photoUri =
                FileProvider.getUriForFile(this, "${packageName}.fileprovider", ImageFile!!)
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
           // startActivityForResult(cameraIntent, Utils.REQUEST_CODE_CAMERA)
        }
    }

    private fun openGallery(name: String) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        tempFileName = name
       // startActivityForResult(intent, Utils.REQUEST_CODE_GALLERY)
    }

   /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
                        this@KYCDetailsView
                    ).toString()
                    setupImage(path, bitmap)
                    if (ImageFile!=null && ImageFile!!.exists()) {
                        ImageFile!!.delete()
                    }
                }

                Utils.REQUEST_CODE_GALLERY -> {
                    val selectedImageUri: Uri? = data?.data
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri) as Bitmap
                    val path = Utils.saveImage(
                        tempFileName.toString(),
                        bitmap,
                        false,
                        this@KYCDetailsView
                    ).toString()
                    setupImage(path, bitmap)
                    if (ImageFile !=null && ImageFile!!.exists()) {
                        ImageFile!!.delete()
                    }

                }
            }

        }

    }
*/
    private fun updateKYCData() {
        try {
            val userID = Utils.getData(this@KYCDetailsView, "user_id", "") as String

            val aadharFront_File = File(img_AadharFront_Path.toString())
            val aadharFrontPart: MultipartBody.Part?

            if (aadharFront_File.exists()){
                val aadharFront = aadharFront_File.asRequestBody("image/png".toMediaType())
                aadharFrontPart = MultipartBody.Part.createFormData(
                    "aadhar_front",
                    aadharFront_File.name,
                    aadharFront
                )
            }else{
                aadharFrontPart=null
            }

            val aadharBack_File = File(img_AadharBack_Path.toString())
            val aadharBackPart: MultipartBody.Part?
            if (aadharBack_File.exists()){
                val aadharBack = aadharBack_File.asRequestBody("image/png".toMediaType())
                aadharBackPart = MultipartBody.Part.createFormData(
                    "aadhar_back",
                    aadharBack_File.name,
                    aadharBack
                )
            }else{
                aadharBackPart=null
            }

            val panCard_File: File = File(img_PANFront_Path.toString())
            val panImgPart: MultipartBody.Part?
            if (panCard_File.exists()){
                val panImg = panCard_File.asRequestBody("image/png".toMediaType())
                panImgPart = MultipartBody.Part.createFormData(
                    "pan_image",
                    panCard_File.name,
                    panImg
                )
            }else{
                panImgPart=null
            }

            val user_id = RequestBody.create("text/plain".toMediaType(), userID)
            val aadhar_number = RequestBody.create("text/plain".toMediaType(), aadharNumber!!)
            val pan_number = RequestBody.create("text/plain".toMediaType(), PANNumber!!)
            /*updateKYCApi(
                user_id,
                aadhar_number,
                pan_number,
                aadharFrontPart,
                aadharBackPart,
                panImgPart
            )*/
        } catch (e: Exception) {
            Log.e("updateKYCData", e.message.toString())
        }

    }

   /* private fun updateKYCApi(
        user_id: RequestBody, aadhar_number: RequestBody, pan_number: RequestBody,
        aadhar_front: MultipartBody.Part?,
        aadhar_back: MultipartBody.Part?,
        pan_image: MultipartBody.Part?
    ) {
        try {
            openDialog = Utils.openDialog(this@KYCDetailsView)
            val dataManager = DataManager.getDataManager()
            val callbackAddKYC = object : Callback<MainResponse> {
                override fun onResponse(
                    call: Call<MainResponse>,
                    response: Response<MainResponse>
                ) {
                    Utils.closeDialog(openDialog)
                    if (response.isSuccessful) {

                        Utils.showToast(
                            this@KYCDetailsView,
                            response.body()!!.message.toString()
                        )
                        isAadharOpen = false
                        isShowAadhar = false
                        isPANOpen = false
                        isShowPAN = false
                        updateXML()
                    } else {
                        Utils.showToast(this@KYCDetailsView, response.message().toString())

                    }
                    println("add KYC Details response: ${response}")


                }

                override fun onFailure(call: Call<MainResponse>, t: Throwable) {
                    Utils.closeDialog(openDialog)
                    println("Failed to add KYC Details. ${t.message}")
                    Utils.showToast(this@KYCDetailsView, "${t.message} Please try again")
                }

            }
            dataManager.addKYC(
                callbackAddKYC,
                user_id,
                aadhar_number,
                pan_number,
                aadhar_front,
                aadhar_back,
                pan_image
            )

        } catch (e: Exception) {
            if (openDialog.isShowing) {
                Utils.closeDialog(openDialog)
            }
            Log.e("updateKYCApi", e.message.toString())
        }
    }*/

    private fun upLoadImageCancel(name: String) {
        try {
            var file: File? = null

            if (name.contains("Aadhaar_Front")) {
                img_AadharFront.setImageBitmap(null)
                file = File(img_AadharFront_Path)
                if (file.exists()) {
                    file.delete()
                }
                img_AadharFront_Path = ""
                btn_UploadAadharFront.visibility = View.GONE
                btn_takeAadharFront.visibility = View.VISIBLE
                btn_chooseAadharFront.visibility = View.VISIBLE
            } else if (name.contains("Aadhaar_Back")) {
                img_AadharBack.setImageBitmap(null)
                file = File(img_AadharBack_Path)
                if (file.exists()) {
                    file.delete()
                }
                img_AadharBack_Path = ""
                btn_UploadAadharBack.visibility = View.GONE
                btn_takeAadharBack.visibility = View.VISIBLE
                btn_chooseAadharBack.visibility = View.VISIBLE
            } else if (name.contains("PAN_Card")) {
                img_PANFront.setImageBitmap(null)
                file = File(img_PANFront_Path)
                if (file.exists()) {
                    file.delete()
                }
                img_PANFront_Path = ""
                btn_UploadPANFront.visibility = View.GONE
                btn_takePANFront.visibility = View.VISIBLE
                btn_choosePANFront.visibility = View.VISIBLE
            }
            tempFileName = ""
        } catch (e: Exception) {
            Log.e("MemberKYC_Details.setupImage() ", e.message.toString())
        }
    }

    private fun setupImage(path: String, bitmap: Bitmap) {
        try {

            if (path.contains("IMG_Aadhaar_Front")) {

                img_AadharFront.setImageBitmap(bitmap)
                img_AadharFront_Path = path
                btn_UploadAadharFront.visibility = View.VISIBLE
                btn_takeAadharFront.visibility = View.GONE
                btn_chooseAadharFront.visibility = View.GONE
            } else if (path.contains("IMG_Aadhaar_Back")) {
                img_AadharBack.setImageBitmap(bitmap)
                img_AadharBack_Path = path
                btn_UploadAadharBack.visibility = View.VISIBLE
                btn_takeAadharBack.visibility = View.GONE
                btn_chooseAadharBack.visibility = View.GONE
            } else if (path.contains("IMG_PAN_Card")) {
                img_PANFront.setImageBitmap(bitmap)
                img_PANFront_Path = path
                btn_UploadPANFront.visibility = View.VISIBLE
                btn_takePANFront.visibility = View.GONE
                btn_choosePANFront.visibility = View.GONE

            }
            tempFileName = ""
        } catch (e: Exception) {
            Log.e("MemberKYC_Details.setupImage() ", e.message.toString())
        }
    }

    fun checkStoragePermissions(): Boolean {
        val writePermission =
            ContextCompat.checkSelfPermission(
                this@KYCDetailsView,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        val readPermission =
            ContextCompat.checkSelfPermission(
                this@KYCDetailsView,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        val cameraPermission =
            ContextCompat.checkSelfPermission(
                this@KYCDetailsView,
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
                this@KYCDetailsView,
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

    /*private fun getKYCDetailsAPI(userId: String) {
        try {
            val dataManager = DataManager.getDataManager()

            val otpCallback = object : Callback<FetchedKYCResponse> {
                override fun onResponse(
                    call: Call<FetchedKYCResponse>,
                    response: Response<FetchedKYCResponse>
                ) {
                    if (response.body()!!.status == true) {
                        val responseData = response.body()!!.data.kyc_details
                        setKYCData(responseData)


                        Log.v("Response", "response" + response.body()!!.message.toString())
                    } else {

                        // Handle error
                        println("Failed to KYC. ${response.message()}")
                    }

                }


                override fun onFailure(call: Call<FetchedKYCResponse>, t: Throwable) {
                    println("Failed to KYC. ${t.message}")
                }
            }

            // Call the sendOtp function in DataManager
            dataManager.getKYCDetails(otpCallback, userId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun setKYCData(list: ArrayList<AddKYC>) {
        try {
            var aadhar_front: String? = ""
            var aadhar_back: String? = ""
            var pan_image: String? = ""
            var aadhar_number: String? = ""
            var pan_number: String? = ""
            for (data in list) {
                aadhar_front = data.aadhar_front.toString()
                aadhar_back = data.aadhar_back.toString()
                aadhar_number = data.aadhar_number.toString()
                pan_image = data.pan_image.toString()
                pan_number = data.pan_number.toString()

            }
            tv_kyc_Number.text = aadhar_number
            et_AadharNumber.setText(aadhar_number)
            et_PANNumber.setText(pan_number)


            if (aadhar_front!!.isNotEmpty()) {
                aadhar_front.let { url ->
                    Glide.with(this).load(url)
                        *//*.apply(
                            RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)  // Cache for fast loading
                                .override(1080, 1920)  // Set high resolution
                                .fitCenter()
                        )*//*
                        .into(img_AadharFront)

                    CoroutineScope(Dispatchers.Main).launch {
                        img_AadharFront_Path=Utils.convertURLToLocalPath(this@KYCDetailsView,url,"IMG_Aadhaar_Front")

                    }

                }
            }
            if (aadhar_back!!.isNotEmpty()) {
                aadhar_back.let { url ->
                    Glide.with(this).load(url)
                        *//*.apply(
                            RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)  // Cache for fast loading
                                .override(1080, 1920)  // Set high resolution
                                .fitCenter()
                        )*//*
                        .into(img_AadharBack)
                    CoroutineScope(Dispatchers.Main).launch {
                        img_AadharBack_Path=Utils.convertURLToLocalPath(this@KYCDetailsView,url,"IMG_Aadhaar_Back")

                    }


                }
            }
            if (pan_image!!.isNotEmpty()) {
                pan_image.let { url ->
                    Glide.with(this).load(url)
                        *//*.apply(
                            RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)  // Cache for fast loading
                                .override(1080, 1920)  // Set high resolution
                                .fitCenter()
                        )*//*
                        .into(img_PANFront)
                    CoroutineScope(Dispatchers.Main).launch {
                        img_PANFront_Path=Utils.convertURLToLocalPath(this@KYCDetailsView,url,"IMG_PAN_Card")

                    }


                }
            }
        } catch (e: Exception) {
            Log.e("shownProfileImage", e.message.toString())
        }
    }*/

    private fun showUpdateKYCDetails(isKYC: Boolean):Boolean {
        if (isKYC) {
            ll_aadharNum.visibility = View.VISIBLE
            ll_AadharFontButtons.visibility = View.VISIBLE
            ll_AadharBackButtons.visibility = View.VISIBLE
            btn_UpdateKYC.visibility = View.VISIBLE
            btn_UpdateAadhar.visibility = View.GONE
            tv_imgeinfo.visibility = View.VISIBLE
            btn_UploadAadharFront.visibility = View.GONE
            btn_UploadAadharBack.visibility = View.GONE
            tv_UpdateAadhar.text = "Close"
            val drawable = ContextCompat.getDrawable(this, R.drawable.ic_close)?.mutate()
            drawable?.setTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red)))
            drawable!!.setTint(ContextCompat.getColor(this, R.color.red))
            tv_UpdateAadhar.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
            tv_UpdateAadhar.setTextColor(ContextCompat.getColor(this, R.color.red))
            tv_UpdateAadhar.compoundDrawablePadding = 8

        } else {
            ll_aadharNum.visibility = View.GONE
            ll_AadharFontButtons.visibility = View.GONE
            ll_AadharBackButtons.visibility = View.GONE
            tv_imgeinfo.visibility = View.GONE
            btn_UpdateAadhar.visibility = View.GONE
            btn_UpdateKYC.visibility = View.GONE
            tv_UpdateAadhar.text = "Update Aadhar"
            val drawable = ContextCompat.getDrawable(this, R.drawable.ic_edit)?.mutate()
            drawable?.let {
                it.setTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary)))
                tv_UpdateAadhar.setCompoundDrawablesWithIntrinsicBounds(null, null, it, null)
            }
            tv_UpdateAadhar.setTextColor(ContextCompat.getColor(this, R.color.primary))
            tv_UpdateAadhar.compoundDrawablePadding = 8


        }
        return isKYC

    }

    private fun showUpdatePANDetails(isKYC: Boolean): Boolean {
        if (isKYC) {
            ll_pan_no.visibility = View.VISIBLE
            ll_pan_buttons.visibility = View.VISIBLE
            btn_UpdateKYC.visibility = View.VISIBLE
            btn_UploadPANFront.visibility = View.GONE
            btn_UpdatePAN.visibility = View.GONE
            tv_UpdatePAN.text = "Close"
            val drawable = ContextCompat.getDrawable(this, R.drawable.ic_close)?.mutate()
            drawable?.let {
                it.setTintList( ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red)))
                tv_UpdatePAN.setCompoundDrawablesWithIntrinsicBounds(null, null, it, null)
            }
            tv_UpdatePAN.setTextColor(ContextCompat.getColor(this, R.color.red))
            tv_UpdatePAN.compoundDrawablePadding = 8
        } else {
            ll_pan_no.visibility = View.GONE
            ll_pan_buttons.visibility = View.GONE
            btn_UpdatePAN.visibility = View.GONE
            btn_UpdateKYC.visibility = View.GONE
            tv_UpdatePAN.text = "Update PAN"
            val drawable = ContextCompat.getDrawable(this, R.drawable.ic_edit)?.mutate()
            drawable?.let {
                it.setTintList( ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary)))
                tv_UpdatePAN.setCompoundDrawablesWithIntrinsicBounds(null, null, it, null)
            }
            tv_UpdatePAN.setTextColor(ContextCompat.getColor(this, R.color.primary))
            tv_UpdatePAN.compoundDrawablePadding = 8

        }

        return isKYC
    }


}