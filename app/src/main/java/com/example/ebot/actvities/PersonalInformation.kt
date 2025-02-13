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
import com.example.ebot.R
import com.example.ebot.common.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.imageview.ShapeableImageView
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
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
    private var tempPhotoPath: String? = ""
    private lateinit var cameraBottomSheet: BottomSheetDialog
    private lateinit var editDetailBottomSheet: BottomSheetDialog


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
            val tv_email: TextView = view.findViewById(R.id.tv_email)
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

            if (isProfileEdit) {
                ll_editAddress.visibility = View.GONE
                tv_email.visibility = View.VISIBLE
                et_emailId.visibility = View.VISIBLE
            } else {
                ll_editAddress.visibility = View.VISIBLE
                tv_email.visibility = View.GONE
                et_emailId.visibility = View.GONE
                setButtonsBG(0)

            }


            close.setOnClickListener(View.OnClickListener {
                editDetailBottomSheet.dismiss()
            })
            btn_cancel.setOnClickListener(View.OnClickListener {
                editDetailBottomSheet.dismiss()
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
}