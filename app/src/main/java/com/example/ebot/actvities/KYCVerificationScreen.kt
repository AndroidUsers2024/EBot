package com.example.ebot.actvities

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
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
import com.example.ebot.models.MainResponse
import com.example.ebot.models.SaveBankDetails
import com.example.ebot.services.ServiceManager
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

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
    private var aadharNumber: String? = ""
    private var PANNumber: String? = ""
    private var aadhaarFront: String? = ""
    private var aadhaarBack: String? = ""
    private var PANFront: String? = ""
    private var bankDetails: SaveBankDetails?=null
    private var tempFileName: String? = ""
    private var faceIMGPath: String? = ""
    private var ImageFile: File? = null
    private var isCameraOpen: Boolean? = false
    private val PERMISSION_REQUEST_CODE: Int = 201
    private lateinit var openDialog: ProgressDialog


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
            img_PersonPhoto.visibility=View.GONE
            btn_submit.isEnabled=false
            setScreens(screenNo!!)

            try {
                aadharNumber=intent.getStringExtra("aadharNumber")
                PANNumber=intent.getStringExtra("PANNumber")
                aadhaarFront=intent.getStringExtra("aadhaarFront")
                aadhaarBack=intent.getStringExtra("aadhaarBack")
                PANFront=intent.getStringExtra("PANFront")
                bankDetails=intent.getParcelableExtra("bankDetails")

                isCameraOpen=intent.getBooleanExtra("isCameraOpen",false)
                if (isCameraOpen==true){
                    val imagePath =intent.getStringExtra("ImagePath")
                    ImageFile=File(imagePath)
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
                    img_PersonPhoto.setImageBitmap(bitmap)
                    img_PersonPhoto.visibility=View.VISIBLE
                    ll_verificationStatus.visibility=View.VISIBLE
                    faceIMGPath=path
                    if (path.isNotEmpty()){
                        btn_submit.isEnabled=true

                    }else{
                        btn_submit.isEnabled=false

                    }

                    if (ImageFile !=null && ImageFile!!.exists()) {
                        ImageFile!!.delete()
                    }


                }

            }catch (e:Exception){
                Log.e("KYCVerification",e.message.toString())
            }
            skip.setOnClickListener(View.OnClickListener {
                addKYCData(true)
            })


            btn_takePhoto.setOnClickListener(View.OnClickListener {
               openCamera()
            })
            btn_submit.setOnClickListener(
                View.OnClickListener {
                    if (btn_submit.text.toString()=="Home"&&screenNo==2) {
                        val intent= Intent()
                        intent.setClass(this,MainActivity::class.java)
                        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()

                    }else{
                        if (Utils.isNetworkAvailable(this)){
                            addKYCData(false)
                        }else{
                            Utils.showAlertDialog(this,"Alert","Please check network connection")
                        }

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
    private fun openCamera() {
        if (checkStoragePermissions()) {
            intent.setClass(this, CameraActivity::class.java)
            intent.putExtra("aadharNumber", aadharNumber)
            intent.putExtra("PANNumber", PANNumber)
            intent.putExtra("aadhaarFront", aadhaarFront)
            intent.putExtra("aadhaarBack", aadhaarBack)
            intent.putExtra("PANFront", PANFront)
            intent.putExtra("bankDetails", bankDetails)
            startActivity(intent)
            finish()

        }
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
                    img_PersonPhoto.setImageBitmap(bitmap)
                    faceIMGPath=path

                    if (ImageFile !=null && ImageFile!!.exists()) {
                        ImageFile!!.delete()
                    }
                }


            }

        }

    }
    fun checkStoragePermissions(): Boolean {
        val writePermission =
            ContextCompat.checkSelfPermission(
                this@KYCVerificationScreen,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        val readPermission =
            ContextCompat.checkSelfPermission(
                this@KYCVerificationScreen,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        val cameraPermission =
            ContextCompat.checkSelfPermission(
                this@KYCVerificationScreen,
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
                this@KYCVerificationScreen,
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
                Toast.makeText(this@KYCVerificationScreen, "Permissions Granted", Toast.LENGTH_SHORT).show()
                // selectImageSource()
                openCamera()
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
    private fun addKYCData(isSkip:Boolean) {
        try {
            val userID = Utils.getData(this@KYCVerificationScreen, "user_id", "") as String

            val aadharFront_File = File(aadhaarFront.toString())
            var aadharFrontPart : MultipartBody.Part?=null
            if (aadharFront_File.exists()){
                val aadharFront = aadharFront_File.asRequestBody("image/png".toMediaType())
                 aadharFrontPart = MultipartBody.Part.createFormData(
                    "aadhar_front",
                    aadharFront_File.name,
                    aadharFront
                )
            }


            val aadharBack_File = File(aadhaarBack.toString())
            var aadharBackPart :MultipartBody.Part?=null
            if (aadharBack_File.exists()){
                val aadharBack = aadharBack_File.asRequestBody("image/png".toMediaType())
                 aadharBackPart = MultipartBody.Part.createFormData(
                    "aadhar_back",
                    aadharBack_File.name,
                    aadharBack
                )
            }

            val panCard_File = File(PANFront.toString())
            var panImgPart : MultipartBody.Part?=null
            if (panCard_File.exists()){
                val panImg = panCard_File.asRequestBody("image/png".toMediaType())
                panImgPart = MultipartBody.Part.createFormData(
                    "pan_image",
                    panCard_File.name,
                    panImg
                )
            }

            val face_File = File(faceIMGPath.toString())
            var face_verificaton : MultipartBody.Part?=null
            if (face_File.exists()){
                val faceImg = panCard_File.asRequestBody("image/png".toMediaType())
                 face_verificaton = MultipartBody.Part.createFormData(
                    "face_verificaton",
                    face_File.name,
                    faceImg
                )
            }

            val user_id = RequestBody.create("text/plain".toMediaType(), userID)
            val aadhar_number = RequestBody.create("text/plain".toMediaType(), aadharNumber!!)
            val pan_number = RequestBody.create("text/plain".toMediaType(), PANNumber!!)
            val account_number = RequestBody.create("text/plain".toMediaType(), bankDetails!!.account_number!!)
            val bank_name = RequestBody.create("text/plain".toMediaType(), bankDetails!!.bank_name!!)
            val ifsc_code = RequestBody.create("text/plain".toMediaType(), bankDetails!!.ifsc_code!!)
            val account_type = RequestBody.create("text/plain".toMediaType(), bankDetails!!.account_type!!)
            addKYCApi(user_id,aadhar_number,pan_number,aadharFrontPart!!,aadharBackPart!!,panImgPart!!,account_number,bank_name,ifsc_code,account_type,face_verificaton,isSkip)
        } catch (e: Exception) {
            Log.e("addKYCData", e.message.toString())
        }

    }

    private fun addKYCApi(
        user_id: RequestBody, aadhar_number: RequestBody, pan_number: RequestBody?,
        aadhar_front: MultipartBody.Part?,
        aadhar_back: MultipartBody.Part?,
        pan_image: MultipartBody.Part?,
        account_number: RequestBody?,
        bank_name: RequestBody?,
        ifsc_code: RequestBody?,
        account_type: RequestBody?,
        face_verificaton: MultipartBody.Part?,
        isSkip:Boolean
    ) {
        try {
            openDialog = Utils.openDialog(this@KYCVerificationScreen)
            val dataManager = ServiceManager.getDataManager()
            val callbackAddKYC = object : Callback<MainResponse> {
                override fun onResponse(
                    call: Call<MainResponse>,
                    response: Response<MainResponse>
                ) {
                    Utils.closeDialog(openDialog)
                    if (isSkip)
                    {
                        val intent= Intent()
                        intent.setClass(this@KYCVerificationScreen,MainActivity::class.java)
                        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
                    else{
                        if (response.isSuccessful) {
                            if (response.body()!!.status == "true") {
                                screenNo = screenNo!! + 1
                                setScreens(screenNo!!)
                            }
                            Utils.showToast(
                                this@KYCVerificationScreen,
                                response.body()!!.message.toString()
                            )

                        } else {
                            Utils.showToast(
                                this@KYCVerificationScreen,
                                response.message().toString()
                            )

                        }
                    }
                    println("add KYC Details response: ${response}")


                }

                override fun onFailure(call: Call<MainResponse>, t: Throwable) {
                    Utils.closeDialog(openDialog)
                    println("Failed to add KYC Details. ${t.message}")
                    if (isSkip)
                    {
                        val intent= Intent()
                        intent.setClass(this@KYCVerificationScreen,MainActivity::class.java)
                        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }else{
                        Utils.showToast(this@KYCVerificationScreen, "${t.message} Please try again")
                    }
                }

            }
            dataManager.addKYC(
                callbackAddKYC,
                user_id,
                aadhar_number,
                pan_number,
                aadhar_front,
                aadhar_back,
                pan_image,account_number,bank_name,ifsc_code,account_type,face_verificaton
            )

        } catch (e: Exception) {
            if (openDialog.isShowing) {
                Utils.closeDialog(openDialog)
            }
            if (isSkip)
            {
                val intent= Intent()
                intent.setClass(this@KYCVerificationScreen,MainActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            Log.e("addKYCApi", e.message.toString())
        }
    }

}