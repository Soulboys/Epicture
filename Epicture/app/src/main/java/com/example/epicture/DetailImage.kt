package com.example.epicture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Get the images details.
 *
 * The class is to get the infos of the image, like the number of likes and comments.
 */
class DetailImage : AppCompatActivity() {

    var rvMain: RecyclerView? = null
    var imageList: List<ImageLib>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_photo)

        supportActionBar?.show()
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvMain = findViewById(R.id.recyclerView_detail)
        rvMain?.layoutManager = LinearLayoutManager(baseContext)

        val content: Content = intent.extras?.get("content") as Content
        rvMain?.adapter = PhotoAdapter(content, context = baseContext)

    }
}
