package com.example.ebot.actvities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import com.example.ebot.R
import com.example.ebot.models.SaveBankDetails


class CameraActivity : AppCompatActivity() {
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var imageCapture: ImageCapture
    private lateinit var previewView: PreviewView
    private lateinit var captureButton: Button
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        previewView = findViewById(R.id.previewView)
        captureButton = findViewById(R.id.captureButton)
        cameraExecutor = Executors.newSingleThreadExecutor()

        requestPermissions()

        captureButton.setOnClickListener {
            takePhoto()
        }
    }

    private fun startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                .build()

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

            cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)

        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Face_IMG_${System.currentTimeMillis()}.jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()

        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Log.d("CameraX", "Photo saved at: ${file.absolutePath}")
                   // Toast.makeText(this@CameraActivity, "Photo saved!", Toast.LENGTH_SHORT).show()
                    processCapturedImage(file)
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("CameraX", "Photo capture failed: ${exception.message}")
                }
            })
    }

    private fun processCapturedImage(file: File) {
        val image = InputImage.fromFilePath(this, Uri.fromFile(file))

        val detector = FaceDetection.getClient(
            FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .build()
        )

        detector.process(image)
            .addOnSuccessListener { faces ->
                if (faces.isNotEmpty()) {
                    Toast.makeText(this, "Face detected!", Toast.LENGTH_SHORT).show()
                    Log.d("FaceDetection", "Face detected in saved image!")
                    val i=Intent(this@CameraActivity,KYCVerificationScreen::class.java)
                    i.putExtra("ImagePath",file.absolutePath)
                    i.putExtra("isCameraOpen",true)
                    i.putExtra("aadharNumber",intent.getStringExtra("aadharNumber"))
                    i.putExtra("PANNumber",intent.getStringExtra("PANNumber"))
                    i.putExtra("aadhaarFront",intent.getStringExtra("aadhaarFront"))
                    i.putExtra("aadhaarBack",intent.getStringExtra("aadhaarBack"))
                    i.putExtra("PANFront",intent.getStringExtra("PANFront"))
                    i.putExtra("bankDetails",intent.getParcelableExtra<SaveBankDetails>("bankDetails"))
                    startActivity(i)
                    finish()
                } else {
                    Toast.makeText(this, "No face detected!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Log.e("FaceDetection", "Face detection failed: ${e.message}")
            }
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
        } else {
            startCamera()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            Toast.makeText(this, "Permissions required!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
