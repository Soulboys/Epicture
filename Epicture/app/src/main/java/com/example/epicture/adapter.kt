package com.example.epicture

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.image_row.view.*
import com.bumptech.glide.request.RequestOptions


/**
 * An adapter for images.
 *
 * The class is to make the images visible on a page.
 *
 * @property context which page on the app.
 * @property imageList list of all the images got from the imgur API.
 */
class MainAdapter(val imageList: List<Content>?, val context: Context): RecyclerView.Adapter<CustomViewHolder>() {

    val mContext: Context = context
    val CONTENT = "content"

    override fun getItemCount(): Int {
        if (imageList?.size == null)
            return 0
        return imageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.image_row, parent, false)

        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val content: Content? = imageList?.get(position)

        if (content?.images != null) {
            val image: List<ImageLib>? = content.images
            val optionsImage = RequestOptions().fitCenter()

            if (image?.get(0)?.type == "image/gif") {
                Glide.with(mContext)
                    .asGif()
                    .load(image[0].link)
                    .apply(optionsImage)
                    .into(holder.view.imageView)

            } else {
                Glide.with(mContext)
                    .load(image?.get(0)?.link)
                    .apply(optionsImage)
                    .into(holder.view.imageView)
            }
            holder.view.textView.text = content?.title
            holder.view.setOnClickListener { didSelectTableView(content, position) }
        } else if (content != null) {
            val image: Content? = imageList?.get(position);
            val optionsImage = RequestOptions().fitCenter()
            if (image?.type == "image/gif") {
                Glide.with(mContext)
                    .asGif()
                    .load(image?.link)
                    .apply(optionsImage)
                    .into(holder.view.imageView)

            } else {
                Glide.with(mContext)
                    .load(image?.link)
                    .apply(optionsImage)
                    .into(holder.view.imageView)
            }
            holder.view.textView.text = content.title
            holder.view.setOnClickListener { didSelectTableView(content, position) }
        }

    }

    fun didSelectTableView(content: Content?, position: Int) {

        val randomIntent = Intent(context, DetailImage::class.java)
        randomIntent.putExtra(CONTENT, content)
        startActivity(context, randomIntent, null)

    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {


}