package com.example.drken.blocks

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton

import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        val button = findViewById<View>(R.id.btnLaunch) as ImageButton
        val imageViewTarget = DrawableImageViewTarget(button)
        Glide.with(this).load(R.raw.eos_spinning_logo).into(imageViewTarget)
        button.setOnClickListener {
            val intent = Intent(this@LaunchActivity, BlockListActivity::class.java)
            startActivity(intent)
        }
    }
}
