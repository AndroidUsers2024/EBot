package com.example.ebot.fragments

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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.ebot.R
import com.example.ebot.actvities.AboutUS
import com.example.ebot.actvities.ContactUS
import com.example.ebot.actvities.EnquiryForm
import com.example.ebot.actvities.FAQ
import com.example.ebot.actvities.KYCDetailsView
import com.example.ebot.actvities.LoginActivity
import com.example.ebot.actvities.MainActivity
import com.example.ebot.actvities.PersonalInformation
import com.example.ebot.actvities.PrivacyPolicy
import com.example.ebot.actvities.TermsAndConditions
import com.example.ebot.actvities.ViewBankDetails
import com.example.ebot.common.Utils
import com.example.ebot.models.CMS
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


class ProfileFragment : Fragment(),View.OnClickListener {
    private lateinit var btn_personal: Button
    private lateinit var btn_kyc: Button
    private lateinit var btn_bank: Button
    private lateinit var btn_AboutUs: Button
    private lateinit var btn_PrivacyPolicy: Button
    private lateinit var btn_terms: Button
    private lateinit var btn_enquiry: Button
    private lateinit var btn_contact: Button
    private lateinit var btn_faq: Button
    private lateinit var btn_logout: Button
    private lateinit var ll_BankAcounts: LinearLayout
    private lateinit var ll_profile: LinearLayout
    private lateinit var ll_KYCDetail: LinearLayout
    private var isNotificationNo = false
    private lateinit var openDialog: ProgressDialog
    private lateinit var img_profile: ShapeableImageView
    private lateinit var wv_camera: ImageButton
    private lateinit var cameraBottomSheet: BottomSheetDialog
    private val PERMISSION_REQUEST_CODE: Int = 201
    private var profilePicPath: String? = ""
    private  var tempPhotoPath: String?=""
    private  var userId: String?=""
    private lateinit var progressbar: ProgressBar
    private var profileData: ProfileData?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        updateXML(view)
        return view
    }



    private fun updateXML(view: View) {
        try {
            btn_personal = view.findViewById(R.id.btn_personal)
            btn_kyc = view.findViewById(R.id.btn_kyc)
            btn_bank = view.findViewById(R.id.btn_bank)
            btn_AboutUs = view.findViewById(R.id.btn_AboutUs)
            btn_PrivacyPolicy = view.findViewById(R.id.btn_PrivacyPolicy)
            btn_terms = view.findViewById(R.id.btn_terms)
            btn_enquiry = view.findViewById(R.id.btn_enquiry)
            btn_contact = view.findViewById(R.id.btn_contact)
            btn_faq = view.findViewById(R.id.btn_faq)
            ll_BankAcounts = view.findViewById(R.id.ll_BankAcounts)
            ll_KYCDetail = view.findViewById(R.id.ll_KYCDetail)
            btn_logout = view.findViewById(R.id.btn_logout)
            img_profile =view. findViewById(R.id.img_profile)
            wv_camera = view.findViewById(R.id.wv_camera)
            ll_profile = view.findViewById(R.id.ll_profile)
            progressbar = view.findViewById(R.id.progressbar)
            profileData=ProfileData()
            progressbar.visibility=View.VISIBLE
            ll_profile.visibility=View.GONE
             userId=Utils.getData(requireContext(),"user_id","") as String
            getImageData(userId!!)

            wv_camera.setOnClickListener(this)
            btn_personal.setOnClickListener(this)
            btn_kyc.setOnClickListener(this)
            btn_bank.setOnClickListener(this)
            btn_AboutUs.setOnClickListener(this)
            btn_PrivacyPolicy.setOnClickListener(this)
            btn_terms.setOnClickListener(this)
            btn_enquiry.setOnClickListener(this)
            btn_contact.setOnClickListener(this)
            btn_faq.setOnClickListener(this)
            ll_BankAcounts.setOnClickListener(this)
            ll_KYCDetail.setOnClickListener(this)
            btn_logout.setOnClickListener(this)


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.wv_camera -> {
               showImageSource()

            }
            R.id.btn_personal -> {
                val intent = Intent(requireContext(), PersonalInformation::class.java)
                startActivity(intent)

            }

            R.id.btn_kyc -> {
                val intent = Intent(requireContext(), KYCDetailsView::class.java)
                startActivity(intent)
            }

            R.id.btn_bank -> {
                val intent = Intent(requireContext(), ViewBankDetails::class.java)
                startActivity(intent)
            }



            R.id.btn_AboutUs -> {
                val intent = Intent(requireContext(), AboutUS::class.java)
                startActivity(intent)

            }

            R.id.btn_PrivacyPolicy -> {
                val intent = Intent(requireContext(), PrivacyPolicy::class.java)
                startActivity(intent)

            }

            R.id.btn_terms -> {
                val intent = Intent(requireContext(), TermsAndConditions::class.java)
                startActivity(intent)

            }

            R.id.btn_enquiry -> {
                val intent = Intent(requireContext(), EnquiryForm::class.java)
                startActivity(intent)

            }

            R.id.btn_contact -> {
                val intent = Intent(requireContext(), ContactUS::class.java)
                startActivity(intent)

            }

            R.id.btn_faq -> {
                val intent = Intent(requireContext(), FAQ::class.java)
                startActivity(intent)

            }

            R.id.ll_BankAcounts -> {
                val intent = Intent(requireContext(), ViewBankDetails::class.java)
                startActivity(intent)

            }

            R.id.ll_KYCDetail -> {
                val intent = Intent(requireContext(), KYCDetailsView::class.java)
                startActivity(intent)

            }

            R.id.btn_logout -> {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                Utils.saveData(requireContext(), "mobile", "")
                Utils.saveData(requireContext(), "user_id", "")
                Utils.saveData(requireContext(), "user_name", "")
                Utils.saveData(requireContext(), "fullAddress", "")
                startActivity(intent)

            }

        }
    }
    private fun showImageSource(){
        try{
            cameraBottomSheet = BottomSheetDialog(requireContext(),R.style.BottomSheetTheme)
            val view:View= LayoutInflater.from(requireContext()).inflate(R.layout.choose_profile_popup,null,false )
            val close: ShapeableImageView =view.findViewById(R.id.close)
            val btn_openCamara: Button =view.findViewById(R.id.btn_openCamara)
            val btn_chooseLibrary:Button=view.findViewById(R.id.btn_chooseLibrary)
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
        }catch (e:Exception){
            Log.e("profile.showImageSource()",e.message.toString())
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val currentFragment =
                        requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainer)
                    if (currentFragment is HomeFragment) {
                        requireActivity().finish()  // This will exit the app, or do something else here
                    } else {
                        (requireActivity() as MainActivity).onBackPressed()

                    }
                }
            })
    }

    private fun openCamera() {
        if (checkStoragePermissions()){
            val photoFile = Utils.createImageFile("Profile", requireContext())
            val photoUri =
                FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.fileprovider", photoFile)
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
        if (cameraBottomSheet.isShowing){
            cameraBottomSheet.dismiss()

        }
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Utils.REQUEST_CODE_CAMERA -> {
                    val file = File(tempPhotoPath)
                    val bitmap =
                        Utils.getBitmapFromUri(requireContext().contentResolver, Uri.fromFile(file))
                    profilePicPath = Utils.saveImage(
                        "IMG_Profile",
                        bitmap!!,
                        false,
                        requireContext()
                    )
                    val temp= tempPhotoPath?.let { File(it) }
                    if(temp!=null&&temp.exists()){
                        temp.delete()
                    }
                    img_profile.setImageBitmap(bitmap)
                }

                Utils.REQUEST_CODE_GALLERY -> {
                    val selectedImageUri: Uri? = data?.data
                    val bitmap =Utils. getBitmapFromUri(requireContext().contentResolver, selectedImageUri!!) as Bitmap
                    profilePicPath = Utils.saveImage(
                        "IMG_Profile",
                        bitmap!!,
                        false,
                        requireContext()
                    ) // Save in external storage
                    val temp= tempPhotoPath?.let { File(it) }
                    if(temp!=null&&temp.exists()){
                        temp.delete()
                    }
                    img_profile.setImageBitmap(bitmap)

                }

            }
            if (Utils.isNull(profilePicPath).isNotEmpty()){
                if (cameraBottomSheet!=null && cameraBottomSheet.isShowing){
                    cameraBottomSheet.dismiss()
                }
                updateProfileData(profileData!!,true)
            }


        }


    }

    fun checkStoragePermissions(): Boolean {
        /* val writePermission =
             ContextCompat.checkSelfPermission(
                 requireContext(),
                 android.Manifest.permission.WRITE_EXTERNAL_STORAGE
             )
         val readPermission =
             ContextCompat.checkSelfPermission(
                 requireContext(),
                 android.Manifest.permission.READ_EXTERNAL_STORAGE
             )*/
        val cameraPermission =
            ContextCompat.checkSelfPermission(
                requireContext(),
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
                requireContext() as Activity,
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
                Toast.makeText(requireContext(), "Permissions Granted", Toast.LENGTH_SHORT).show()
                //  selectImageSource()
                openCamera()
            } else {
                Toast.makeText(requireContext(), "Permissions Denied", Toast.LENGTH_SHORT).show()

                if (/*ActivityCompat.shouldShowRequestPermissionRationale(
                        requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) ||*/
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        requireContext() as Activity,
                        Manifest.permission.CAMERA
                    )
                ) {
                    Toast.makeText(requireContext(), "Please grant permissions to proceed", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", requireContext().packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Permissions denied. Go to Settings to enable them.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun getImageData(user:String) {
        val dataManager = ServiceManager.getDataManager()

        progressbar.visibility= View.VISIBLE
        // Create a callback for handling the API response
        val callback = object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                progressbar.visibility= View.GONE
                ll_profile.visibility= View.VISIBLE
                if (response.isSuccessful) {
                    if(response.body()!!.status=="success")
                    {
                        profileData=ProfileData()
                        profileData=response.body()!!.data
                        shownProfile(response.body()!!.data!!.profile_image.toString())


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
                progressbar.visibility= View.GONE
                ll_profile.visibility= View.VISIBLE
                Utils.showToast(requireContext(),"Please try again")
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
                    Log.i("Response", "imageURL $url")


                }
            }
        }catch (e:Exception){
            Log.e("shownProfileImage",e.message.toString())
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
        isUpdateProfilePic: Boolean
    ) {
        try {
            openDialog = Utils.openDialog(requireContext())
            val dataManager = ServiceManager.getDataManager()
            val callbackAddKYC = object : Callback<MainResponse> {
                override fun onResponse(
                    call: Call<MainResponse>,
                    response: Response<MainResponse>
                ) {
                    Utils.closeDialog(openDialog)
                    if (response.isSuccessful) {
                        if (response.body()!!.status == "success") {

                           getImageData(userId!!)

                        }


                    }
                    println("add KYC Details response: ${response}")


                }

                override fun onFailure(call: Call<MainResponse>, t: Throwable) {
                    Utils.closeDialog(openDialog)
                    println("Failed to add KYC Details. ${t.message}")
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
    private fun updateProfileData(data: ProfileData,  isUpdateProfilePic: Boolean) {
        try {
            val userID = Utils.getData(requireContext(), "user_id", "") as String

            val image: MultipartBody.Part?
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
            updateProfileApi(user_id, name, last_name, gender, dob, phone, email, address, pin_code, city, state, country, image,isUpdateProfilePic)
        } catch (e: Exception) {
            Log.e("addKYCData", e.message.toString())
        }

    }


}