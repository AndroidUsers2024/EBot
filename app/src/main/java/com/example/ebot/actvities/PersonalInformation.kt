package com.example.ebot.actvities

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ebot.R
import com.google.android.material.imageview.ShapeableImageView

class PersonalInformation : AppCompatActivity() {
    private lateinit var back: View
    private lateinit var img_profile: ShapeableImageView
    private lateinit var wv_camera: ImageButton
    private lateinit var btn_editProfile: ImageButton
    private lateinit var btn_editAddress: ImageButton
    private lateinit var et_FullName: EditText
    private lateinit var et_emailId: EditText
    private lateinit var et_mobile: EditText
    private lateinit var et_DOB: EditText
    private lateinit var et_FullAddress: EditText
    private lateinit var et_address: EditText
    private lateinit var et_pinCode: EditText
    private lateinit var et_city: EditText
    private lateinit var et_state: EditText
    private lateinit var et_country: EditText
    private lateinit var btn_Save_Acc: Button
    private lateinit var ll_edit_address: LinearLayout
    private lateinit var openDialog: ProgressDialog
    private var first_name: String? = ""
    private var last_name: String? = ""
    private var dob: String? = ""
    private var address: String? = ""
    private var pincode: String? = ""
    private var city: String? = ""
    private var country: String? = ""
    private var state: String? = ""
    private var user_id: String? = ""
    private var email: String? = ""
    private var isEditAddress = false
    private var isEditProfile = false
    private var profilePicPath: String? = ""
    private  var tempPhotoPath: String?=""
    private val PERMISSION_REQUEST_CODE: Int = 201


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

            back.setOnClickListener(View.OnClickListener {
                onBackPressed()
            })



        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}