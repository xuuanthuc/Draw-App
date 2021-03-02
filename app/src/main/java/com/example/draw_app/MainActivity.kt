package com.example.draw_app

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_brush_size.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var mImageButtonCurrentPaint: ImageButton? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawing_view.setSizeBrush(10.toFloat())
        mImageButtonCurrentPaint = ll_paint_color[0] as ImageButton
        mImageButtonCurrentPaint!!.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.pallet_color_onpressed
            )
        )
        ib_brush.setOnClickListener {
            showBrushSizeChosserDialog()
        }
        ib_images.setOnClickListener {
            if(isReadStorageAllow()){
                //run
                val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(pickPhoto, GALLERY)
            } else{
                requestStoragePermission()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == GALLERY){
                try {
                    if(data!!.data != null){
                        iv_background.visibility = View.VISIBLE
                        iv_background.setImageURI(data.data)
                    } else {
                        Toast.makeText(this, "Failed" , Toast.LENGTH_LONG).show()

                    }
                }
                catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }

    private fun showBrushSizeChosserDialog() {
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush Size")
        val sizeOne = brushDialog.ib_brush_size_one
        sizeOne.setOnClickListener {
            drawing_view.setSizeBrush(4.toFloat())
            brushDialog.dismiss()
        }
        val sizeTwo = brushDialog.ib_brush_size_two
        sizeTwo.setOnClickListener {
            drawing_view.setSizeBrush(8.toFloat())
            brushDialog.dismiss()
        }
        val sizeThree = brushDialog.ib_brush_size_three
        sizeThree.setOnClickListener {
            drawing_view.setSizeBrush(13.toFloat())
            brushDialog.dismiss()
        }
        val sizeFour = brushDialog.ib_brush_size_four
        sizeFour.setOnClickListener {
            drawing_view.setSizeBrush(18.toFloat())
            brushDialog.dismiss()
        }
        val sizeFive = brushDialog.ib_brush_size_five
        sizeFive.setOnClickListener {
            drawing_view.setSizeBrush(23.toFloat())
            brushDialog.dismiss()
        }
        brushDialog.show()

    }

    fun paintClicker(view: View) {
        if (view !== mImageButtonCurrentPaint) {
            val imageButton = view as ImageButton
            val colorTag = imageButton.tag.toString()
            drawing_view.setColor(colorTag)
            imageButton.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_color_onpressed)
            )
            mImageButtonCurrentPaint!!.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_color)
            )
            mImageButtonCurrentPaint = view
        }
    }

    private fun requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                ).toString()
            )
        ){

        }
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,), STORAGE_PERMISSION_CODE
            )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "No permission" , Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this, "No permission" , Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun isReadStorageAllow(): Boolean {
        val result = ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val STORAGE_PERMISSION_CODE = 1
        private const val GALLERY = 2
    }
}