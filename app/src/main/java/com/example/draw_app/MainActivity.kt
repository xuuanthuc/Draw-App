package com.example.draw_app

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_brush_size.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawing_view.setSizeBrush(10.toFloat())
        ib_brush.setOnClickListener {
            showBrushSizeChosserDialog()
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
}