package com.example.ebot.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.text.Html
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.text.toLowerCase
import androidx.core.content.ContextCompat
import com.example.ebot.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale


object Utils {
    var userId: String? = ""
    val IMG_ROOT_URL = "https://ritps.com/ebot/"
    var sponser_code: String? = ""
    var WALLET_AMOUNT: String = ""
    val REQUEST_CODE_CAMERA = 100
    val REQUEST_CODE_GALLERY = 101

    fun showToast(ctx: Context, msg: String) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
    }

    fun stringToDateTimeDefault(dateTime: String?, format: String?): Date? {
        var result: Date? = null
        try {
            if (dateTime != null) {
                val sdf = SimpleDateFormat(format, Locale.ENGLISH)
                result = sdf.parse(dateTime)
                // DateFormat dateformate ;
                // Date date;
                // dateformate = new SimpleDateFormat("ddMMMyyyy");
            }
        } catch (ex: Exception) {
            ex.printStackTrace()

        }
        return result
    }

    fun getDate(): String {
        val c = Calendar.getInstance()
        val date =
            c[Calendar.DAY_OF_MONTH].toString() + "/" + (c[Calendar.MONTH] + 1) + "/" + c[Calendar.YEAR]
        return date
    }

    fun getDayNumber(month: Int): String {
        var strmonth = ""

        strmonth = if (month == 1) {
            "01"
        } else if (month == 2) {
            "02"
        } else if (month == 3) {
            "03"
        } else if (month == 4) {
            "04"
        } else if (month == 5) {
            "05"
        } else if (month == 6) {
            "06"
        } else if (month == 7) {
            "07"
        } else if (month == 8) {
            "08"
        } else if (month == 9) {
            "09"
        } else {
            month.toString() + ""
        }
        return strmonth
    }

    fun getMonth(month: Int): String {
        var strmonth = ""

        if (month == 0) {
            strmonth = "Jan"
        }
        if (month == 1) {
            strmonth = "Feb"
        }
        if (month == 2) {
            strmonth = "Mar"
        }
        if (month == 3) {
            strmonth = "Apr"
        }
        if (month == 4) {
            strmonth = "May"
        }
        if (month == 5) {
            strmonth = "Jun"
        }
        if (month == 6) {
            strmonth = "Jul"
        }
        if (month == 7) {
            strmonth = "Aug"
        }
        if (month == 8) {
            strmonth = "Sep"
        }
        if (month == 9) {
            strmonth = "Oct"
        }
        if (month == 10) {
            strmonth = "Nov"
        }
        if (month == 11) {
            strmonth = "Dec"
        }
        return strmonth
    }

    fun isMobileValid(mobileNo: String, type: String): String? {
        var alert: String? = ""

        if (mobileNo.length != 10) {
            alert += "Please enter 10 digits $type mobile number \n"
        } else if (!mobileNo.startsWith("9") && !mobileNo.startsWith("8")
            && !mobileNo.startsWith("7") && !mobileNo.startsWith("6")
        ) {
            alert += "Please enter valid $type mobile number \n"
        }

        return alert
    }


    fun showAlertDialog(context: Context, title: String, msg: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss() // Close the dialog
            }


        // Create and show the AlertDialog
        val alertDialog = builder.create()
        alertDialog.show()
    }


    fun openDialog(context: Context): ProgressDialog {
        var dialog: ProgressDialog? = null
        dialog = ProgressDialog(context).apply {
            setMessage("Please wait...")
            setCancelable(false)
            show()
        }
        return dialog
    }

    fun closeDialog(dialog: ProgressDialog) {
        if (dialog.isShowing) {
            dialog.dismiss()
        }

    }

    fun maskAccountNumber(accountNumber: String): String {
        if (accountNumber.length <= 4) {
            return accountNumber // If the account number is 4 or fewer characters, don't mask it
        }
        val maskLength = accountNumber.length - 4
        val maskedPart = "*".repeat(maskLength)
        val visiblePart = accountNumber.takeLast(4)
        val maskAccountNo = maskedPart + visiblePart
        return maskAccountNo.chunked(4).joinToString("_")
    }

    fun maskEndDigits(mobileNumber: String): String {
        return if (mobileNumber.length > 4) {
            mobileNumber.dropLast(4) + "X".repeat(4)
        } else {
            "X".repeat(mobileNumber.length)
        }
    }

    fun maskMobileNumber(mobileNumber: String): String {
        return if (mobileNumber.length > 4) {
            "X".repeat(mobileNumber.length - 4) + mobileNumber.takeLast(4)
        } else {
            mobileNumber // If the number is 4 digits or less, no masking needed
        }
    }


    fun saveData(context: Context, key: String, value: Any?) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("PropertyZoneSP", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
        }
        editor.apply()
    }

    fun getData(context: Context, key: String, defaultValue: Any): Any? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("PropertyZoneSP", Context.MODE_PRIVATE)
        return when (defaultValue) {
            is String -> sharedPreferences.getString(key, defaultValue)
            is Int -> sharedPreferences.getInt(key, defaultValue)
            is Boolean -> sharedPreferences.getBoolean(key, defaultValue)
            is Float -> sharedPreferences.getFloat(key, defaultValue)
            is Long -> sharedPreferences.getLong(key, defaultValue)
            else -> null
        }
    }

    private fun identifyDateFormat(date: String): String {
        return when {
            date.matches(Regex("\\d{2}/\\d{2}/\\d{4}")) -> "dd/MM/yyyy"
            date.matches(Regex("\\d{1}/\\d{1}/\\d{4}")) -> "d/M/yyyy"
            date.matches(Regex("\\d{2} \\d{2} \\d{4}")) -> "dd MM yyyy"
            date.matches(Regex("\\d{1} \\d{1} \\d{4}")) -> "d M yyyy"
            date.matches(Regex("\\d{4}-\\d{2}-\\d{2}")) -> "yyyy-MM-dd"
            date.matches(Regex("\\d{4}-\\d{1}-\\d{1}")) -> "yyyy-M-d"
            date.matches(Regex("\\d{2}-\\d{2}-\\d{4}")) -> "dd-MM-yyyy"
            date.matches(Regex("\\d{1}-\\d{1}-\\d{4}")) -> "d-M-yyyy"
            date.matches(Regex("\\d{2}-\\d{1}-\\d{4}")) -> "dd-M-yyyy"
            date.matches(Regex("\\d{1}-\\d{2}-\\d{4}")) -> "d-MM-yyyy"
            date.matches(Regex("\\d{4}/\\d{2}/\\d{2}")) -> "yyyy/MM/dd"
            date.matches(Regex("\\d{2}/\\d{2}/\\d{4}")) -> "dd/MM/yyyy"
            date.matches(Regex("\\d{1}/\\d{1}/\\d{4}")) -> "d/M/yyyy"
            date.matches(Regex("\\d{1}/\\d{2}/\\d{4}")) -> "d/MM/yyyy"
            date.matches(Regex("\\d{2}/\\d{1}/\\d{4}")) -> "dd/M/yyyy"
            date.matches(Regex("\\d{2}-[A-Za-z]{3}-\\d{4}")) -> "dd-MMM-yyyy"
            date.matches(Regex("\\d{2} [A-Za-z]{3}, \\d{4}")) -> "dd MMM, yyyy"
            date.matches(Regex("\\d{2} [A-Za-z]{3}, \\d{4}")) -> "dd MMM yyyy"

            else -> "Unknown Format"
        }
    }

    fun changeDateFormat(inputDate: String, dateFormat: String): String {

        return try {
            val inputFormat = SimpleDateFormat(identifyDateFormat(inputDate), Locale.getDefault())
            val outputFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
            val date: Date? = inputFormat.parse(inputDate)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            return ""
        }
    }

    fun createImageFile(type: String, context: Context): File {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            type + "_$timeStamp", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }

    fun saveImage(
        type: String,
        bitmap: Bitmap,
        saveExternally: Boolean,
        context: Context
    ): String? {
        var file: File? = null
        try {
            val compressedBitmap = compressBitmapToTargetSize(bitmap, 100)

            val timeStamp: String =
                SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val directory: File = if (saveExternally) {
                val externalStorageDir =
                    File(Environment.getExternalStorageDirectory(), "PropertiZone")
                if (!externalStorageDir.exists()) {
                    externalStorageDir.mkdir()
                }
                externalStorageDir
            } else {
                val internalStorageDir = File(context.filesDir, "PropertiZone")
                if (!internalStorageDir.exists()) {
                    internalStorageDir.mkdir()
                }
                internalStorageDir
            }
            var path = File(directory, "Images")
            if (!path.exists()) {
                path.mkdir()
            }
            val userId = getData(context, "user_id", "").toString()
            path = File(path, "User_$userId")
            if (!path.exists()) {
                path.mkdir()
            }

            file =  File(path, """${type}_${userId}.jpg""")
            if (file.exists()){
                file.delete()
                saveImage(type,bitmap,saveExternally,context)
            }else{
                val outputStream = FileOutputStream(file)
                compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
            }

            println("imagePath" + file.absolutePath)

            // Toast.makeText(context, "getting image path", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Log.e("imagePath", e.message.toString())
            // Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show()
        }

        return file!!.absolutePath
    }

    fun removeHtmlTags(htmlText: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(htmlText).toString()
        }
    }

    fun convertToDouble(value: Any, defaultValue: Double): Double {
        var doubleValue: Double = defaultValue

        try {
            doubleValue = value.toString().toDouble()
        } catch (e: Exception) {
            return defaultValue
        }
        return doubleValue
    }
    fun convertToDefaultFloat(value: Any): Float {
        var doubleValue: Float = 0F

        try {
            doubleValue = value.toString().toFloat()
        } catch (e: Exception) {
            return doubleValue
        }
        return doubleValue
    }

    fun convertToInt(value: Any, defaultValue: Int): Int {
        var doubleValue: Int = defaultValue

        try {
            doubleValue = value.toString().toInt()
        } catch (e: Exception) {
            return defaultValue
        }
        return doubleValue
    }

    fun getCurrentTime(): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        return LocalTime.now().format(formatter)
    }

    fun showsConfirmation_old(context: Context, msg: String, onConfirm: () -> Unit) {
        val dialog = AlertDialog.Builder(context)
            .setTitle("Confirmation")
            .setMessage(msg)
            .setPositiveButton("Confirm") { _, _ ->
                onConfirm() // Perform the action for confirming removal
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss() // Simply dismiss the dialog
            }
            .create()

        // Accessing the title and setting the color
        dialog.setOnShowListener {
            val titleTextView = dialog.findViewById<TextView>(android.R.id.title)
            titleTextView?.setTextColor(Color.RED)

            val messageTextView = dialog.findViewById<TextView>(android.R.id.message)
            messageTextView?.setTextColor(Color.BLACK)

            val colorR = ContextCompat.getColorStateList(context, R.color.red)
            val colorG = ContextCompat.getColorStateList(context, R.color.secondary)
            // Customizing button text color
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)

            positiveButton.setTextColor(Color.WHITE) // Set positive button text color
            negativeButton.setTextColor(Color.WHITE) // Set negative button text color

            positiveButton.setBackgroundResource(R.drawable.button_bg)
            negativeButton.setBackgroundResource(R.drawable.button_bg)
            positiveButton.backgroundTintList = colorG
            negativeButton.backgroundTintList = colorR
            positiveButton.isAllCaps = false
            negativeButton.isAllCaps = false


            val layoutParamsPositive = positiveButton.layoutParams as LinearLayout.LayoutParams
            layoutParamsPositive.gravity = Gravity.END
            layoutParamsPositive.marginStart = 90
            positiveButton.layoutParams = layoutParamsPositive
            layoutParamsPositive.width =
                (90 * context.resources.displayMetrics.density).toInt() // 200dp in pixels
            layoutParamsPositive.height =
                (40 * context.resources.displayMetrics.density).toInt() // 200dp in pixels


            val layoutParamsNegative = negativeButton.layoutParams as LinearLayout.LayoutParams
            layoutParamsNegative.marginEnd = 190
            negativeButton.layoutParams = layoutParamsNegative
            layoutParamsNegative.width =
                (80 * context.resources.displayMetrics.density).toInt() // 200dp in pixels
            layoutParamsNegative.height = (40 * context.resources.displayMetrics.density).toInt()
            negativeButton.layoutParams = layoutParamsNegative
        }

        dialog.show()
    }
    fun isNetworkAvailable(context: Context): Boolean {
        var objConnectivityManager: ConnectivityManager?
        var isNetworkAvailable = false

        try {
            objConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (objConnectivityManager!!.activeNetworkInfo != null && objConnectivityManager!!.activeNetworkInfo!!
                    .isAvailable
                && objConnectivityManager!!.activeNetworkInfo!!.isConnected
            ) {
                return true.also { isNetworkAvailable = it }
            }
        } catch (e: java.lang.Exception) {
            e.message
        } finally {
            objConnectivityManager = null
        }
        return isNetworkAvailable
    }


    fun isValidEmail(email: String): String {
        val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        if (!emailRegex.matches(email)) {
            return "Invalid email address.\n"
        }
        return ""
    }

    fun maskAadhaarNumber(aadhaarNumber: String): String {
        val sanitizedNumber = aadhaarNumber.replace("\\D".toRegex(), "")

        // Check if the length is valid (12 digits)
        if (sanitizedNumber.length != 12) {
            Log.e("maskAadhaarNumber", "Invalid Aadhaar number. Must be 12 digits.")
        }
        return "XXXX XXXX " + sanitizedNumber.substring(8)
    }

    fun validatePAN(panCard: String): String {
        var message = ""

        if (panCard.trim().length != 10) {
            message += "PAN Card should be 10 characters\n"
        } else {
            for (i in panCard.indices) {
                val char = panCard[i]
                if ((i < 5 && char.isLetter()) || (i in 5..8 && char.isDigit()) || (i == 9 && char.isLetter())) {
                    if (i == 3 && !(char == 'p' || char == 'P')) {
                        message += "4th character should be is 'P' in PAN Card\n"
                        break;
                    }
                } else {
                    message += "PAN Card should contain first five, 10th characters as alpha, rest of characters as numeric\n"
                    break
                }
            }
        }

        if (panCard.contains(" ")) {
            message += "PAN Card should not contain space\n"
        }

        return message
    }




    fun compressBitmapToTargetSize(bitmap: Bitmap, targetSizeInKB: Int): Bitmap {
        var quality = 100
        val byteArrayOutputStream = ByteArrayOutputStream()
        var compressedData :ByteArray?=null
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
            compressedData = byteArrayOutputStream.toByteArray()
            var currentSizeInKB = compressedData.size / 1024 // Convert size to KB
            while (currentSizeInKB > targetSizeInKB && quality > 5) {
                byteArrayOutputStream.reset()
                quality -= 10
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
                compressedData = byteArrayOutputStream.toByteArray()
                currentSizeInKB = compressedData.size / 1024
            }
        }catch (e:Exception){
            Log.e("compressBitmapToTargetSize",e.message.toString())
        }

        return BitmapFactory.decodeByteArray(compressedData, 0, compressedData!!.size)
    }

    fun getBitmapFromUri(contentResolver: ContentResolver, uri: Uri): Bitmap? {
        var inputStream: InputStream? = null
        var bitmap: Bitmap? = null
        try {
            inputStream = contentResolver.openInputStream(uri)
            bitmap = BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream?.close()  // Make sure to close the InputStream
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return bitmap
    }

    fun isValidMobileNumber(mobile: String): Boolean {
        val regex = Regex("^[6-9][0-9]{9}$")
        return regex.matches(mobile)
    }



    suspend fun convertURLToLocalPath(context: Context, imageUrl: String, type: String): String? {
        return withContext(Dispatchers.IO) {  // Run in background thread
            var file: File? = null
            try {
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()

                val inputStream = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(inputStream)

                // Compress Bitmap before saving
                val compressedBitmap = compressBitmapToTargetSize(bitmap, 100)

                val timeStamp: String =
                    SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())

                val internalStorageDir = File(context.filesDir, "PropertiZone")
                if (!internalStorageDir.exists()) {
                    internalStorageDir.mkdirs()
                }

                var path = File(internalStorageDir, "Images")
                if (!path.exists()) {
                    path.mkdirs()
                }

                val userId = getData(context, "user_id", "").toString()
                path = File(path, "User_$userId")
                if (!path.exists()) {
                    path.mkdirs()
                }

                // File naming logic
                file = File(path, """${type}_${userId}.jpg""")
                if (file.exists()){
                    file.delete()
                    convertURLToLocalPath(context,imageUrl,type)
                }else{
                    // Save the compressed bitmap to the file
                    FileOutputStream(file).use { outputStream ->
                        compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    }
                }




                println("Image saved at: ${file.absolutePath}")
                return@withContext file.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext ""
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    fun popUpFullViewImage(path: String?, activity: Activity) {
        try {
            val builder = AlertDialog.Builder(activity)
            val inflater = activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(R.layout.imageview, null)

            var imageBitmap: Bitmap?=null
            val file = File(path)
            if (file.exists()){
                imageBitmap = BitmapFactory.decodeFile(path)
            }
            if (imageBitmap != null) {

                val imageView1 = view.findViewById<ImageView>(R.id.iv_image)
                val btn_close = view.findViewById<Button>(R.id.btn_close)
                imageView1.setImageBitmap(imageBitmap)
                builder.setView(view)
                builder.setCancelable(false)
                val dialog: AlertDialog = builder.create()
                dialog.show()
                btn_close.setOnClickListener(View.OnClickListener {
                    dialog.dismiss()
                })

                // Customize the button style

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun decodeFile(pathName: String?, dstWidth: Int, dstHeight: Int): Bitmap? {
        var scaledBitmap: Bitmap? = null
        try {
            if (!pathName.isNullOrEmpty()) {
                val options = BitmapFactory.Options().apply {
                    inJustDecodeBounds = true
                }
                BitmapFactory.decodeFile(pathName, options)

                options.apply {
                    inJustDecodeBounds = false
                    inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, dstWidth, dstHeight)
                }

                val unscaledBitmap = BitmapFactory.decodeFile(pathName, options)
                if (unscaledBitmap != null) {
                    scaledBitmap = Bitmap.createBitmap(unscaledBitmap.width, unscaledBitmap.height, Bitmap.Config.ARGB_8888)
                    val canvas = Canvas(scaledBitmap)
                    canvas.drawBitmap(unscaledBitmap, Rect(0, 0, 100, 100), Rect(0, 0, 100, 100), Paint(
                        Paint.FILTER_BITMAP_FLAG)
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return scaledBitmap
    }
    fun calculateSampleSize(srcWidth: Int, srcHeight: Int, dstWidth: Int, dstHeight: Int): Int {
        val srcAspect = srcWidth.toFloat() / srcHeight.toFloat()
        val dstAspect = dstWidth.toFloat() / dstHeight.toFloat()

        return if (srcAspect > dstAspect) {
            srcWidth / dstWidth
        } else {
            srcHeight / dstHeight
        }
    }

    fun isNull(data:Any?,type: String):Any{
        if (data ==null){
            when (type) {
                 "s" -> return ""
                 "i" -> return 0
                 "b" -> return false
                 "f" -> return 0
                 "l" -> return 0
                 "d" ->return 0.0
                else -> return ""
            }
        }
        return ""
    }


    fun showsConfirmation(context: Context, msg: String, onConfirm: () -> Unit) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_view, null)

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false) // Prevent accidental dismiss
            .create()

        // Find views inside custom layout
        val titleTextView = dialogView.findViewById<TextView>(R.id.dialogTitle)
        val messageTextView = dialogView.findViewById<TextView>(R.id.dialogMessage)
        val positiveButton = dialogView.findViewById<Button>(R.id.positiveButton)
        val negativeButton = dialogView.findViewById<Button>(R.id.negativeButton)

        // Set dynamic text
        messageTextView.text = msg

        // Handle button clicks
        positiveButton.setOnClickListener {
            onConfirm() // Perform the action for confirming
            dialog.dismiss()
        }

        negativeButton.setOnClickListener {
            dialog.dismiss() // Simply dismiss the dialog
        }

        // Show the dialog
        dialog.show()
    }

    fun isNull(value: String?):String{
        if (value==null || value==" "|| value.toLowerCase(Locale.getDefault())=="null"){
            return ""

        }
        return value.trim()
    }


}