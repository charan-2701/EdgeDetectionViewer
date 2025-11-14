package com.flamapp.edgedetection

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.OpenCVLoader
import org.opencv.core.Mat

class MainActivity : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2 {

    private lateinit var cameraBridgeViewBase: CameraBridgeViewBase

    companion object {
        private const val TAG = "EdgeDetection"
        private const val CAMERA_PERMISSION_REQUEST = 1

        init {
            // OpenCV initialization will be done at runtime (if present)
            OpenCVLoader.initDebug()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST)
        } else {
            initializeCamera()
        }
    }

    private fun initializeCamera() {
        cameraBridgeViewBase = findViewById(R.id.camera_view)
        cameraBridgeViewBase.visibility = CameraBridgeViewBase.VISIBLE
        cameraBridgeViewBase.setCvCameraViewListener(this)
        cameraBridgeViewBase.enableView()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeCamera()
            }
        }
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        Log.d(TAG, "Camera started: ${width}x${height}")
    }

    override fun onCameraViewStopped() {
        Log.d(TAG, "Camera stopped")
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame): Mat {
        val rgba = inputFrame.rgba()
        nativeProcessFrame(rgba.nativeObjAddr)
        return rgba
    }

    override fun onPause() {
        super.onPause()
        cameraBridgeViewBase.disableView()
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
            cameraBridgeViewBase.enableView()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraBridgeViewBase.disableView()
    }

    private external fun nativeProcessFrame(matAddr: Long)
}
