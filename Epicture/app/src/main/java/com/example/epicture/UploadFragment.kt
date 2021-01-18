package com.example.epicture

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_upload.*
import kotlinx.android.synthetic.main.fragment_upload.view.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import retrofit2.Call
import retrofit2.Callback

/**
 * UploadFragment class
 *
 * Fragment for the upload page.
 */
class UploadFragment : Fragment() {
    private var imageview: ImageView? = null
    private var bitmapCopy: Bitmap? = null
    private val GALLERY = 0
    private val CAMERA = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_upload, container, false)

        imageview = view.findViewById(R.id.Image)

        view.gallery.setOnClickListener { view ->
            showPictureDialog()
        }

        view.upload.setOnClickListener { view ->
            uploadImage()
        }

        return view
    }

    fun showPictureDialog() {

        val pictureDialog = AlertDialog.Builder(this.requireContext())
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        startActivityForResult(intent, CAMERA)
    }


    override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY) {

            if (data != null) {
                val contentURI = data.data
                try {
                    bitmapCopy = MediaStore.Images.Media.getBitmap(activity?.contentResolver, contentURI)
                    imageview?.setImageBitmap(bitmapCopy)
                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this.requireContext(), "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        else if (requestCode == CAMERA) {

            bitmapCopy = data?.extras?.get("data") as Bitmap
            imageview?.setImageBitmap(bitmapCopy)
            Toast.makeText(this.requireContext(), "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }

    fun uploadImage() {

        if (bitmapCopy != null) {
            val byteA: ByteArrayOutputStream = ByteArrayOutputStream()
            bitmapCopy?.compress(Bitmap.CompressFormat.PNG, 100, byteA)
            val byteArray = byteA.toByteArray()
            val encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)
            val client = ImgurClient.create()

            val title = title.text.toString()
            val description = description.text.toString()

            val call = client.uploadImage(
                encoded, title, "", description,
                "Bearer " + Preference(this.requireContext()).getAccessToken(), "Epicture"
            )

            call.enqueue(object : Callback<UploadResponse> {
                override fun onResponse(
                    call: Call<UploadResponse>, response: retrofit2.Response<UploadResponse>
                ) {
                    val body = response.body()
                    Toast.makeText(requireContext(), "Image successfully uploaded !", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                    println("REQUEST FAILED")
                    println(t.message)
                }
            })
        } else {
            Toast.makeText(requireContext(), "Select a photo first", Toast.LENGTH_SHORT).show()
        }
    }
}