package com.infinitytech.template.items

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.infinitytech.template.BaseActivity
import com.infinitytech.template.R
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File


private const val CAMERA_REQUEST = 0x001
private const val CAMERA_PERMISSION = 0x002
private const val AUTHORITIES = "com.infinitytech.template.items.CameraActivity"

class CameraActivity : BaseActivity() {
    private lateinit var imageFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        imageFile = File(filesDir, "picture.png")
        if (imageFile.exists()) imageFile.delete()
        fab.setOnClickListener {
            withPermission(Manifest.permission.CAMERA) {
                if (it) openCamera()
                else Toast.makeText(this, "No Camera Permission", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openCamera() {
        startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            putExtra(MediaStore.EXTRA_OUTPUT,
                    FileProvider.getUriForFile(this@CameraActivity, AUTHORITIES, imageFile))
        }, CAMERA_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST) {
            imageView.setImageURI(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        FileProvider.getUriForFile(this, AUTHORITIES, imageFile)
                    else
                        Uri.fromFile(imageFile))
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
