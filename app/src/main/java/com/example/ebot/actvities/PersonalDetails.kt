package com.example.ebot.actvities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import com.example.ebot.R
import com.example.ebot.common.Utils
import java.util.Calendar
import java.util.Date

class PersonalDetails : AppCompatActivity() {
    private lateinit var et_firstName: EditText
    private lateinit var et_lastName: EditText
    private lateinit var btn_Next: Button
    private lateinit var sp_gender: Spinner
    private lateinit var tv_DOB: TextView
    private var firstName:String?=""
    private var lastName:String?=""
    private var gender:String?=""
    private var dob:String?=""
    private var email:String?=""
    private val genderOptions = arrayOf("Select Gender", "Male", "Female")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.personal_detailscreen)
        updateXML()
    }

    private fun updateXML(){
        try{
            et_firstName=findViewById(R.id.et_firstName)
            et_lastName=findViewById(R.id.et_lastName)
            btn_Next=findViewById(R.id.btn_Next)
            sp_gender=findViewById(R.id.sp_gender)
            tv_DOB=findViewById(R.id.tv_DOB)
            email =intent.getStringExtra("email")
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, genderOptions
            )

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sp_gender.adapter = adapter
            tv_DOB.setOnTouchListener { _, event ->
                if (event.action == android.view.MotionEvent.ACTION_UP) {
                    val drawableEnd = 2
                    if (event.rawX >= tv_DOB.right - tv_DOB.compoundDrawables[drawableEnd].bounds.width()) {
                        datePickerDialog(tv_DOB.text.toString(),tv_DOB)
                        return@setOnTouchListener true
                    }
                }
                false
            }

            btn_Next.setOnClickListener(View.OnClickListener {
                val msg=isEmpty()
                if (msg.isEmpty()){
                    val intent= Intent(this,ContactDetails::class.java)
                    intent.putExtra("firstName",firstName)
                    intent.putExtra("lastName",lastName)
                    intent.putExtra("gender",gender)
                    intent.putExtra("dob",dob)
                    intent.putExtra("email",email)

                    startActivity(intent)
                }else{
                    Utils.showAlertDialog(this,"Alert",msg)
                }

            })


        }catch (e:Exception){
            Log.e("PersonalDetails.updateXML: ",e.message.toString())
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent= Intent(this,LoginActivity::class.java)
        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

  private fun isEmpty():String{
      var msg=""
      firstName =et_firstName.text.toString().trim()
      lastName =et_lastName.text.toString().trim()
      dob=tv_DOB.text.toString().trim()
      val genderPotion=sp_gender.selectedItemPosition
      if (genderPotion>0){
          gender=sp_gender.selectedItem.toString()
      }else{
          gender=""
      }
      if (firstName.isNullOrEmpty()){
          msg+="Please enter your first name\n"
      }
      if (lastName.isNullOrEmpty()){
          msg+="Please enter your last name\n"
      }
      if (gender.isNullOrEmpty()){
          msg+="Please select your gender\n"
      }
      if (dob.isNullOrEmpty()){
          msg+="Please select your DOB\n"
      }

      return msg;

  }
    private fun datePickerDialog(tvDate: String, textView: TextView) {
        try {
            val builder = AlertDialog.Builder(this)
            val inflater: LayoutInflater = layoutInflater
            val view:View = inflater.inflate(R.layout.datepicker, null)
            val vw_close:View = view.findViewById(R.id.vw_close)
            val date: DatePicker = view.findViewById(R.id.date_picker)
            val btn_Apply:Button = view.findViewById(R.id.btn_Apply)
            builder.setView(view)
            if (tvDate != "") {
                val selectedDate: Date? = Utils.stringToDateTimeDefault(tvDate, "dd/MM/yyyy")
                val CDate = Calendar.getInstance()
                if (selectedDate != null) {
                    CDate.time = selectedDate
                }
                date.init(
                    CDate[Calendar.YEAR],
                    CDate[Calendar.MONTH], CDate[Calendar.DAY_OF_MONTH], null
                )
            } else {
                val defaultDate: Date? =
                    Utils.stringToDateTimeDefault(Utils.getDate(), "dd/MM/yyyy")
                val CDate = Calendar.getInstance()
                if (defaultDate != null) {
                    CDate.time = defaultDate
                }
                date.init(
                    CDate[Calendar.YEAR],
                    CDate[Calendar.MONTH], CDate[Calendar.DAY_OF_MONTH], null
                )
            }
            builder.setCancelable(false)
            val dialog: AlertDialog = builder.create()
            dialog.show()
            vw_close.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
            })
            btn_Apply.setOnClickListener(View.OnClickListener {
                val dob: String = //"${date.year}-${String.format("%02d", date.month + 1)}-${String.format("%02d", date.dayOfMonth)}"
                   (date.dayOfMonth.toString() + "/" + (Utils.getDayNumber(date.month + 1))).toString() + "/" + date.year
                textView.setText(dob)
                dialog.dismiss()
            })
        } catch (e:Exception) {
          Log.e("PersonalDetails.selectDOB",e.message.toString())
        }


    }


}