package com.example.epicture

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.detail_photo.view.*
import kotlinx.android.synthetic.main.image_row.view.*
import retrofit2.Call
import retrofit2.Callback

/**
 * The details of the image
 *
 * When you click on an image, this is to see all the infos just under the image.
 */
class PhotoAdapter(val contentData: Content?, val context: Context): RecyclerView.Adapter<CustomDetailViewHolder>() {
    val mContext: Context = context
    val imageList: List<ImageLib>? = contentData?.images

    override fun getItemCount(): Int {
        if (imageList?.size == null )
            return 1
        return imageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomDetailViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.detail_photo, parent, false)

        return CustomDetailViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomDetailViewHolder, position: Int) {
        val content: ImageLib? = imageList?.get(position)
        if (contentData?.images != null) {

            val favoriteButton = holder.view.findViewById(R.id.favoriteButton) as ImageView
            favoriteButton.setOnClickListener { callFavoriteAction(content, holder.view) }

            val optionsImage = RequestOptions().fitCenter()

            if (content?.type == "image/gif") {
                Glide.with(mContext)
                    .asGif()
                    .load(content?.link)
                    .apply(optionsImage)
                    .into(holder?.view.imageViewDetail)

            } else {
                Glide.with(mContext)
                    .load(content?.link)
                    .apply(optionsImage)
                    .into(holder.view.imageViewDetail)
            }
//        image_view_resource.setImageResource(R.drawable.flower)
            if (contentData?.favorite == true) {
                holder.view.favoriteButton.setImageResource(R.drawable.ic_favorite_black_24dp)
            } else {
                holder.view.favoriteButton.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            }
            holder.view.ups.text = contentData?.ups.toString()
            holder.view.downs.text = contentData?.downs.toString()
            holder.view.textViewDetail.text = contentData?.title
            holder.view.setOnClickListener { didSelectTableView(content, position) }
        } else {
            val favoriteButton = holder.view.findViewById(R.id.favoriteButton) as ImageView
            favoriteButton.setOnClickListener { callFavoriteAction2(contentData, holder.view) }

            val optionsImage = RequestOptions().fitCenter()

            if (contentData?.type == "image/gif") {
                Glide.with(mContext)
                    .asGif()
                    .load(contentData?.link)
                    .apply(optionsImage)
                    .into(holder.view.imageViewDetail)

            } else {
                Glide.with(mContext)
                    .load(contentData?.link)
                    .apply(optionsImage)
                    .into(holder.view.imageViewDetail)
            }
            if (contentData?.favorite == true) {

                holder.view.favoriteButton.setImageResource(R.drawable.ic_favorite_black_24dp)
            } else {
                holder.view.favoriteButton.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            }
            holder.view.ups.text = contentData?.ups.toString()
            holder.view.downs.text = contentData?.downs.toString()
            holder.view.textViewDetail.text = contentData?.title
            holder.view.setOnClickListener { didSelectTableView(content, position) }
        }
    }

    fun didSelectTableView(content: ImageLib?, position: Int) {

    return
    }

    fun callFavoriteAction(image: ImageLib?, view: View) {

        val id = image?.id

        if (id == null) {
            Toast.makeText(mContext, "Problem while favorite an image", Toast.LENGTH_SHORT).show()
            return
        }

        val client = ImgurClient.create()
        val call = client.favoriteImage(id,"Bearer " + Preference(mContext).getAccessToken(), "Epicture")

        call.enqueue(object: Callback<UploadResponse> {
            override fun onResponse(call: Call<UploadResponse>, response: retrofit2.Response<UploadResponse>) {
                val body = response.body()

                if (body?.data == "unfavorited")
                    view.favoriteButton.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                else
                    view.favoriteButton.setImageResource(R.drawable.ic_favorite_black_24dp)

                Toast.makeText(mContext, body?.data, Toast.LENGTH_SHORT).show()
                println("REQUEST " + body)
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                println("REQUEST FAILED")
                println(t.message)
            }
        })
    }

    fun callFavoriteAction2(contentData: Content?, view: View) {

        val id = contentData?.id

        if (id == null) {
            Toast.makeText(mContext, "Problem while favorite an image", Toast.LENGTH_SHORT).show()
            return
        }

        val client = ImgurClient.create()
        val call =
            client.favoriteImage(id, "Bearer " + Preference(mContext).getAccessToken(), "Epicture")

        call.enqueue(object : Callback<UploadResponse> {
            override fun onResponse(
                call: Call<UploadResponse>,
                response: retrofit2.Response<UploadResponse>
            ) {
                val body = response.body()

                if (body?.data == "unfavorited")
                    view.favoriteButton.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                else
                    view.favoriteButton.setImageResource(R.drawable.ic_favorite_black_24dp)

                Toast.makeText(mContext, body?.data, Toast.LENGTH_SHORT).show()
                println("REQUEST " + body)
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                println("REQUEST FAILED")
                println(t.message)
            }
        })
    }
}

class CustomDetailViewHolder(val view: View): RecyclerView.ViewHolder(view) {


}