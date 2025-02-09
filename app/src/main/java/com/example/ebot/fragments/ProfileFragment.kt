package com.example.ebot.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import com.example.ebot.R
import com.example.ebot.actvities.AboutUS
import com.example.ebot.actvities.ContactUS
import com.example.ebot.actvities.EnquiryForm
import com.example.ebot.actvities.FAQ
import com.example.ebot.actvities.LoginActivity
import com.example.ebot.actvities.MainActivity
import com.example.ebot.actvities.PersonalInformation
import com.example.ebot.actvities.PrivacyPolicy
import com.example.ebot.actvities.TermsAndConditions
import com.example.ebot.actvities.ViewBankDetails
import com.example.ebot.common.Utils


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
    private lateinit var ll_KYCDetail: LinearLayout
    private var isNotificationNo = false
    private lateinit var openDialog: ProgressDialog

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

            R.id.btn_personal -> {
                val intent = Intent(requireContext(), PersonalInformation::class.java)
                startActivity(intent)

            }

            R.id.btn_kyc -> {
                /*val intent = Intent(requireContext(), KYCDetailsView::class.java)
                startActivity(intent)*/
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
               /* val intent = Intent(requireContext(), KYCDetailsView::class.java)
                startActivity(intent)*/

            }

            R.id.btn_logout -> {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                Utils.saveData(requireContext(), "mobile", "")
                Utils.saveData(requireContext(), "user_id", "")
                Utils.saveData(requireContext(), "user_name", "")
                startActivity(intent)

            }

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
}