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
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
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
import com.example.ebot.models.MainResponse
import com.example.ebot.models.ProfileData
import com.example.ebot.models.ProfileResponse
import com.example.ebot.services.ServiceManager
import com.google.android.material.bottomsheet.BottomSheetDialog
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

class PersonalInformation : AppCompatActivity() {
    private lateinit var back: View
    private lateinit var img_profile: ShapeableImageView
    private lateinit var wv_camera: ImageButton
    private lateinit var btn_editProfile: View
    private lateinit var btn_editAddress: View
    private lateinit var tv_FullName: TextView
    private lateinit var tv_email: TextView
    private lateinit var tv_mobileNo: TextView
    private lateinit var tv_fullAddress: TextView
    private lateinit var btn_home: Button
    private lateinit var btn_shop: Button
    private lateinit var btn_other: Button
    private val PERMISSION_REQUEST_CODE: Int = 201
    private var profilePicPath: String? = ""
    private  var tempPhotoPath: String?=""
    private lateinit var userId: String
    private lateinit var cameraBottomSheet: BottomSheetDialog
    private lateinit var editDetailBottomSheet: BottomSheetDialog
    private lateinit var  openDialog:ProgressDialog
    private var profileData: ProfileData?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.personal_information)
        try {
            updateXML()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun updateXML() {
        try {
            back = findViewById(R.id.back)
            img_profile = findViewById(R.id.img_profile)
            wv_camera = findViewById(R.id.wv_camera)
            btn_editProfile = findViewById(R.id.btn_editProfile)
            btn_editAddress = findViewById(R.id.btn_editAddress)
            tv_FullName = findViewById(R.id.tv_FullName)
            tv_email = findViewById(R.id.tv_email)
            tv_mobileNo = findViewById(R.id.tv_mobileNo)
            tv_fullAddress = findViewById(R.id.tv_fullAddress)
            userId=Utils.getData(this@PersonalInformation,"user_id","") as String

            getProfileData(userId)
            back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })
            wv_camera.setOnClickListener(View.OnClickListener {
                showImageSource()
            })
            btn_editProfile.setOnClickListener(View.OnClickListener {
                editDetailsSource(true)
            })
            btn_editAddress.setOnClickListener(View.OnClickListener {
                editDetailsSource(false)
            })


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showImageSource() {
        try {
            cameraBottomSheet = BottomSheetDialog(this, R.style.BottomSheetTheme)
            val view: View =
                LayoutInflater.from(this).inflate(R.layout.choose_profile_popup, null)
            val close: ShapeableImageView = view.findViewById(R.id.close)
            val btn_openCamara: Button = view.findViewById(R.id.btn_openCamara)
            val btn_chooseLibrary: Button = view.findViewById(R.id.btn_chooseLibrary)
            close.setOnClickListener(View.OnClickListener {
                cameraBottomSheet.dismiss()
            })
            btn_chooseLibrary.setOnClickListener(View.OnClickListener {
                openGallery()
            })
            btn_openCamara.setOnClickListener(View.OnClickListener {
                openCamera()
            })
            cameraBottomSheet.setContentView(view)
            cameraBottomSheet.show()
            cameraBottomSheet.setCancelable(false)
        } catch (e: Exception) {
            Log.e("profile.showImageSource()", e.message.toString())
        }
    }

    private fun editDetailsSource(isProfileEdit: Boolean) {
        try {
            editDetailBottomSheet = BottomSheetDialog(this, R.style.BottomSheetTheme)
            val view: View =
                LayoutInflater.from(this).inflate(R.layout.edit_profile_popup, null)
            val close: ShapeableImageView = view.findViewById(R.id.close)
            val img_flag: ShapeableImageView = view.findViewById(R.id.img_flag)
            val tv_countryCode: TextView = view.findViewById(R.id.tv_countryCode)
            val tv_emailID: TextView = view.findViewById(R.id.tv_email)
            val et_fullName: EditText = view.findViewById(R.id.et_fullName)
            val et_mobileNumber: EditText = view.findViewById(R.id.et_mobileNumber)
            val et_emailId: EditText = view.findViewById(R.id.et_emailId)
            val ll_editAddress: LinearLayout = view.findViewById(R.id.ll_editAddress)
            val et_flat_home: EditText = view.findViewById(R.id.et_flat_home)
            val et_floor: EditText = view.findViewById(R.id.et_floor)
            val et_Area: EditText = view.findViewById(R.id.et_Area)
            val et_landmark: EditText = view.findViewById(R.id.et_landmark)
            val et_City: EditText = view.findViewById(R.id.et_City)
            val et_Country: EditText = view.findViewById(R.id.et_Country)
            val et_state: EditText = view.findViewById(R.id.et_state)
            val et_ZipCode: EditText = view.findViewById(R.id.et_ZipCode)
            btn_home = view.findViewById(R.id.btn_home)
            btn_shop = view.findViewById(R.id.btn_shop)
            btn_other = view.findViewById(R.id.btn_other)
            val btn_cancel: Button = view.findViewById(R.id.btn_cancel)
            val btn_save: Button = view.findViewById(R.id.btn_save)
            var updateData=ProfileData()
            img_flag.setImageDrawable(getDrawable(R.drawable.ind_flag))
            et_fullName.setText(tv_FullName.text.toString())
            et_mobileNumber.setText(tv_mobileNo.text.toString())
            et_emailId.setText(tv_email.text.toString())
            et_Area.setText(profileData!!.address)
            et_ZipCode.setText(profileData!!.pincode)
            et_City.setText(profileData!!.city)
            et_Country.setText(profileData!!.country)
            et_state.setText(profileData!!.state)


            if (isProfileEdit) {
                ll_editAddress.visibility = View.GONE
                tv_emailID.visibility = View.VISIBLE
                et_emailId.visibility = View.VISIBLE
            } else {
                ll_editAddress.visibility = View.VISIBLE
                tv_emailID.visibility = View.GONE
                et_emailId.visibility = View.GONE
                setButtonsBG(0)
            }




            close.setOnClickListener(View.OnClickListener {
                editDetailBottomSheet.dismiss()
            })
            btn_cancel.setOnClickListener(View.OnClickListener {
                editDetailBottomSheet.dismiss()
            })
            btn_save.setOnClickListener(View.OnClickListener {
                val name=et_fullName.text.toString()
                val email =et_emailId.text.toString()
                val phone=et_mobileNumber.text.toString()
                val names = name.split(" ")
                var f_name=""
                var l_name=""
                if (names.size == 2) {
                    f_name = names[0]
                    l_name = names[1]
                } else {
                    f_name = name
                }
                var address=""
                val fl_no=et_flat_home.text.toString().trim()
                val floor=et_floor.text.toString().trim()
                val area=et_Area.text.toString().trim()
                val landmark=et_landmark.text.toString().trim()
                val city=et_City.text.toString().trim()
                val country=et_Country.text.toString().trim()
                val state=et_state.text.toString().trim()
                val pin_code=et_ZipCode.text.toString().trim()
                val country_code=tv_countryCode.text.toString().trim()
                address= "$fl_no,$floor,$area,$landmark".trim()

                if (isProfileEdit){
                    if (name.isEmpty()){
                        Utils.showToast(this@PersonalInformation,"Please enter name")
                    } else if (phone.isEmpty()){
                        Utils.showToast(this@PersonalInformation,"Please enter phone number")
                    }else if (email.isEmpty()){
                        Utils.showToast(this@PersonalInformation,"Please enter email")
                    }else if (!Utils.isNetworkAvailable(this)){
                        Utils.showToast(this@PersonalInformation,"Please check network")
                    }else{
                        updateData=ProfileData(userId, phone,profileData!!.otp,profileData!!.created_at,email,f_name,l_name,profileData!!.gender,profileData!!.dob,
                            address,pin_code,city,country,state,  country_code, profile_image = profilePicPath
                        )
                        updateProfileData(updateData)

                    }

                }else{
                    if (name.isEmpty()){
                        Utils.showToast(this@PersonalInformation,"Please enter name")
                    } else if (phone.isEmpty()){
                        Utils.showToast(this@PersonalInformation,"Please enter phone number")
                    }else if (fl_no.isEmpty()){
                        Utils.showToast(this@PersonalInformation,"Please enter flat/house/building no")
                    }else if (area.isEmpty()){
                        Utils.showToast(this@PersonalInformation,"Please enter area/sector/Locality")
                    }else if (city.isEmpty()){
                        Utils.showToast(this@PersonalInformation,"Please enter city")
                    }else if (country.isEmpty()){
                        Utils.showToast(this@PersonalInformation,"Please enter country")
                    }else if (state.isEmpty()){
                        Utils.showToast(this@PersonalInformation,"Please enter state")
                    }else if (pin_code.isEmpty()){
                        Utils.showToast(this@PersonalInformation,"Please enter Zip Code")
                    }else if (!Utils.isNetworkAvailable(this)){
                        Utils.showToast(this@PersonalInformation,"Please check network")
                    }else{
                        updateData=ProfileData(userId,email,profileData!!.otp,profileData!!.created_at,email,f_name,l_name,profileData!!.gender,profileData!!.dob,
                            address,pin_code,city,country,state,  country_code, profile_image = profilePicPath
                        )
                        updateProfileData(updateData)
                    }
                }
            })
            btn_home.setOnClickListener(View.OnClickListener {
                setButtonsBG(0)
            })
            btn_shop.setOnClickListener(View.OnClickListener {
                setButtonsBG(1)
            })
            btn_other.setOnClickListener(View.OnClickListener {
                setButtonsBG(2)
            })
            editDetailBottomSheet.setContentView(view)
            editDetailBottomSheet.show()
            editDetailBottomSheet.setCancelable(false)

        } catch (e: Exception) {
            Log.e("profile.showImageSource()", e.message.toString())
        }
    }

    private fun openCamera() {
        if (checkStoragePermissions()) {
            val photoFile = Utils.createImageFile("Profile", this@PersonalInformation)
            val photoUri =
                FileProvider.getUriForFile(this, "${packageName}.fileprovider", photoFile)
            tempPhotoPath = photoFile.absolutePath
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            startActivityForResult(cameraIntent, Utils.REQUEST_CODE_CAMERA)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, Utils.REQUEST_CODE_GALLERY)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (cameraBottomSheet.isShowing) {
            cameraBottomSheet.dismiss()

        }
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Utils.REQUEST_CODE_CAMERA -> {
                    val file = File(tempPhotoPath)
                    val bitmap =
                        Utils.getBitmapFromUri(contentResolver, Uri.fromFile(file))
                    profilePicPath = Utils.saveImage(
                        "IMG_Profile",
                        bitmap!!,
                        false,
                        this@PersonalInformation
                    )
                    val temp = tempPhotoPath?.let { File(it) }
                    if (temp != null && temp.exists()) {
                        temp.delete()
                    }
                    img_profile.setImageBitmap(bitmap)
                }

                Utils.REQUEST_CODE_GALLERY -> {
                    val selectedImageUri: Uri? = data?.data
                    val bitmap =
                        Utils.getBitmapFromUri(contentResolver, selectedImageUri!!) as Bitmap
                    profilePicPath = Utils.saveImage(
                        "IMG_Profile",
                        bitmap!!,
                        false,
                        this@PersonalInformation
                    ) // Save in external storage
                    val temp = tempPhotoPath?.let { File(it) }
                    if (temp != null && temp.exists()) {
                        temp.delete()
                    }
                    img_profile.setImageBitmap(bitmap)

                }
            }


        }


    }

    fun checkStoragePermissions(): Boolean {
        /* val writePermission =
             ContextCompat.checkSelfPermission(
                 this@PersonalInformation,
                 android.Manifest.permission.WRITE_EXTERNAL_STORAGE
             )
         val readPermission =
             ContextCompat.checkSelfPermission(
                 this@PersonalInformation,
                 android.Manifest.permission.READ_EXTERNAL_STORAGE
             )*/
        val cameraPermission =
            ContextCompat.checkSelfPermission(
                this@PersonalInformation,
                android.Manifest.permission.CAMERA
            )

        val listPermissionsNeeded = mutableListOf<String>()

        /*if (writePermission != PackageManager.PERMISSION_GRANTED) {
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
                this@PersonalInformation,
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
                //  selectImageSource()
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

    private fun setButtonsBG(potion: Int) {
        try {
            val tintColorSelected =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.secondary))
            val tintColorUnSelcted = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary_low))

            when (potion) {
                0 -> {
                    btn_home.setBackgroundResource(R.drawable.button_gray_bg)
                    btn_home.setTextColor(getColor(R.color.white))
                    btn_home.backgroundTintList = tintColorSelected
                    btn_shop.backgroundTintList = tintColorUnSelcted
                    btn_other.backgroundTintList = tintColorUnSelcted
                    val shopDrawable = btn_shop.compoundDrawablesRelative
                    val homeDrawable = btn_home.compoundDrawablesRelative
                    val otherDrawable = btn_other.compoundDrawablesRelative

                    val homeDrawableS = homeDrawable[0]
                    homeDrawableS?.setTint(ContextCompat.getColor(this, R.color.white))
                    btn_home.setCompoundDrawablesRelative(
                        homeDrawableS,
                        homeDrawable[1],
                        homeDrawable[2],
                        homeDrawable[3]
                    )

                    val shopDrawableUS = shopDrawable[0] // Left drawable
                    shopDrawableUS?.setTint(ContextCompat.getColor(this, R.color.icons))
                    btn_shop.setCompoundDrawablesRelative(
                        shopDrawableUS,
                        shopDrawable[1],
                        shopDrawable[2],
                        shopDrawable[3]
                    )

                    val otherDrawableUS = otherDrawable[0]
                    otherDrawableUS?.setTint(ContextCompat.getColor(this, R.color.icons))
                    btn_other.setCompoundDrawablesRelative(
                        otherDrawableUS,
                        otherDrawable[1],
                        otherDrawable[2],
                        otherDrawable[3]
                    )

                    btn_shop.setBackgroundResource(R.drawable.button_gray_bg)
                    btn_other.setBackgroundResource(R.drawable.button_gray_bg)
                    btn_shop.setTextColor(getColor(R.color.black))
                    btn_other.setTextColor(getColor(R.color.black))

                }

                1 -> {
                    val shopDrawable = btn_shop.compoundDrawablesRelative
                    val homeDrawable = btn_home.compoundDrawablesRelative
                    val otherDrawable = btn_other.compoundDrawablesRelative
                    btn_shop.setBackgroundResource(R.drawable.button_gray_bg)
                    btn_shop.setTextColor(getColor(R.color.white))
                    btn_shop.backgroundTintList = tintColorSelected
                    btn_home.backgroundTintList = tintColorUnSelcted
                    btn_other.backgroundTintList = tintColorUnSelcted

                    val homeDrawableUS = homeDrawable[0]
                    homeDrawableUS?.setTint(ContextCompat.getColor(this, R.color.icons))
                    btn_home.setCompoundDrawablesRelative(
                        homeDrawableUS,
                        homeDrawable[1],
                        homeDrawable[2],
                        homeDrawable[3]
                    )

                    val shopDrawableS = shopDrawable[0] // Left drawable
                    shopDrawableS?.setTint(ContextCompat.getColor(this, R.color.white))
                    btn_shop.setCompoundDrawablesRelative(
                        shopDrawableS,
                        shopDrawable[1],
                        shopDrawable[2],
                        shopDrawable[3]
                    )

                    val otherDrawableUS = otherDrawable[0]
                    otherDrawableUS?.setTint(ContextCompat.getColor(this, R.color.icons))
                    btn_other.setCompoundDrawablesRelative(
                        otherDrawableUS,
                        otherDrawable[1],
                        otherDrawable[2],
                        otherDrawable[3]
                    )

                    btn_home.setBackgroundResource(R.drawable.button_gray_bg)
                    btn_other.setBackgroundResource(R.drawable.button_gray_bg)
                    btn_home.setTextColor(getColor(R.color.black))
                    btn_other.setTextColor(getColor(R.color.black))
                }

                2 -> {
                    val shopDrawable = btn_shop.compoundDrawablesRelative
                    val homeDrawable = btn_home.compoundDrawablesRelative
                    val otherDrawable = btn_other.compoundDrawablesRelative
                    btn_other.setBackgroundResource(R.drawable.button_gray_bg)
                    btn_other.setTextColor(getColor(R.color.white))
                    btn_other.backgroundTintList = tintColorSelected
                    btn_home.backgroundTintList = tintColorUnSelcted
                    btn_shop.backgroundTintList = tintColorUnSelcted

                    val homeDrawableUS = homeDrawable[0]
                    homeDrawableUS?.setTint(ContextCompat.getColor(this, R.color.icons))
                    btn_home.setCompoundDrawablesRelative(
                        homeDrawableUS,
                        homeDrawable[1],
                        homeDrawable[2],
                        homeDrawable[3]
                    )

                    val shopDrawableUS = shopDrawable[0] // Left drawable
                    shopDrawableUS?.setTint(ContextCompat.getColor(this, R.color.icons))
                    btn_shop.setCompoundDrawablesRelative(
                        shopDrawableUS,
                        shopDrawable[1],
                        shopDrawable[2],
                        shopDrawable[3]
                    )

                    val otherDrawableS = otherDrawable[0]
                    otherDrawableS?.setTint(ContextCompat.getColor(this, R.color.white))
                    btn_other.setCompoundDrawablesRelative(
                        otherDrawableS,
                        otherDrawable[1],
                        otherDrawable[2],
                        otherDrawable[3]
                    )

                    btn_home.setBackgroundResource(R.drawable.button_gray_bg)
                    btn_shop.setBackgroundResource(R.drawable.button_gray_bg)
                    btn_home.setTextColor(getColor(R.color.black))
                    btn_shop.setTextColor(getColor(R.color.black))
                }


            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun getProfileData(user:String) {
        val dataManager = ServiceManager.getDataManager()
        openDialog=Utils.openDialog(this@PersonalInformation)

        val callback = object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
              if (openDialog.isShowing){
                  openDialog.dismiss()
              }
                if (response.isSuccessful) {
                    if(response.body()!!.status=="success")
                    {
                        profileData=ProfileData()
                        profileData=response.body()!!.data
                        showProfileData(profileData!!)



                    }

                    Log.i("Response","response"+response.body().toString())

                } else {
                    // Handle error
                    println("Failed to getData  ${response.message()}")
                    //showToast(requireContext(),response.body()!!.message!!)
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                // Handle failure
                println("Failed to getData. ${t.message}")
                if (openDialog.isShowing){
                    openDialog.dismiss()
                }
                Utils.showToast(this@PersonalInformation,"Please try again")
            }
        }

        // Call the sendOtp function in DataManager
        dataManager.fetchProfile(callback, userId = user)
    }

    private fun shownProfile(profileImageUrl:String){
        try{
            if (profileImageUrl.isNotEmpty()) {
                profileImageUrl.let { url ->
                    Glide.with(this).load(url)
                        .into(img_profile)
                    CoroutineScope(Dispatchers.Main).launch {
                        profilePicPath=Utils.convertURLToLocalPath(this@PersonalInformation,url,"IMG_Profile")

                    }
                }
            }
        }catch (e:Exception){
            Log.e("shownProfileImage",e.message.toString())
        }
    }

    private fun showProfileData(data: ProfileData) {
        try {
            tv_FullName.text = data.first_name + " " + data.last_name
            Utils.saveData(
                this@PersonalInformation,
                "user_name",
                tv_FullName.text.toString().trim()
            )
            tv_mobileNo.text = data.mobile
            val dob = Utils.changeDateFormat(data.dob.toString(), "dd-MM-yyyy")
            tv_email.text = data.email
            val fullAddress =
                """${data.address}, ${data.city}, ${data.state}, ${data.country} - ${data.pincode}"""
            tv_fullAddress.text = fullAddress

            shownProfile(data.profile_image.toString())

        } catch (e: Exception) {
            Log.e("PersonalInfo", e.message.toString())
        }
    }

    private fun updateProfileApi(
        user_id: RequestBody,
        name: RequestBody,
        last_name: RequestBody,
        gender: RequestBody,
        dob: RequestBody,
        phone: RequestBody,
        email: RequestBody,
        address: RequestBody,
        pin_code: RequestBody,
        city: RequestBody,
        state: RequestBody,
        country: RequestBody,
        image: MultipartBody.Part?,
    ) {
        try {
            openDialog = Utils.openDialog(this@PersonalInformation)
            val dataManager = ServiceManager.getDataManager()
            val callbackAddKYC = object : Callback<MainResponse> {
                override fun onResponse(
                    call: Call<MainResponse>,
                    response: Response<MainResponse>
                ) {
                    Utils.closeDialog(openDialog)
                    if (response.isSuccessful) {
                        if (response.body()!!.status == "success") {

                            if (editDetailBottomSheet.isShowing){
                                editDetailBottomSheet.dismiss()
                                updateXML()
                            }
                        }
                        Utils.showToast(
                            this@PersonalInformation,
                            response.body()!!.message.toString()
                        )

                    } else {
                        Utils.showToast(this@PersonalInformation, response.message().toString())

                    }
                    println("add KYC Details response: ${response}")


                }

                override fun onFailure(call: Call<MainResponse>, t: Throwable) {
                    Utils.closeDialog(openDialog)
                    println("Failed to add KYC Details. ${t.message}")
                    Utils.showToast(this@PersonalInformation, "${t.message} Please try again")
                }

            }
            dataManager.updateProfile(
                callbackAddKYC,
                user_id, name, last_name, gender, dob, phone, email, address, pin_code, city, state, country, image
            )

        } catch (e: Exception) {
            if (openDialog.isShowing) {
                Utils.closeDialog(openDialog)
            }
            Log.e("addKYCApi", e.message.toString())
        }
    }
    private fun updateProfileData(data: ProfileData) {
        try {
            val userID = Utils.getData(this@PersonalInformation, "user_id", "") as String

             val image:MultipartBody.Part?
            val profilePic = File(profilePicPath.toString())
            if (profilePic.exists()){
                val profileImg = profilePic.asRequestBody("image/png".toMediaType())
                image = MultipartBody.Part.createFormData(
                    "image",
                    profilePic.name,
                    profileImg
                )
            }else{
                image=null
            }

            val user_id = RequestBody.create("text/plain".toMediaType(), userID)
            val name = RequestBody.create("text/plain".toMediaType(), data.first_name!!)
            val last_name = RequestBody.create("text/plain".toMediaType(), data.last_name!!)
            val gender = RequestBody.create("text/plain".toMediaType(), data.gender!!)
            val dob = RequestBody.create("text/plain".toMediaType(), data.dob!!)
            val phone = RequestBody.create("text/plain".toMediaType(), data.mobile!!)
            val email = RequestBody.create("text/plain".toMediaType(), data.email!!)
            val address = RequestBody.create("text/plain".toMediaType(), data.address!!)
            val pin_code = RequestBody.create("text/plain".toMediaType(), data.pincode!!)
            val city = RequestBody.create("text/plain".toMediaType(), data.city!!)
            val state = RequestBody.create("text/plain".toMediaType(), data.state!!)
            val country = RequestBody.create("text/plain".toMediaType(), data.country!!)
            updateProfileApi(user_id, name, last_name, gender, dob, phone, email, address, pin_code, city, state, country, image)
        } catch (e: Exception) {
            Log.e("addKYCData", e.message.toString())
        }

    }

}