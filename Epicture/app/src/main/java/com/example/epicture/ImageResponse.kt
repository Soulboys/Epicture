package com.example.epicture
import android.media.Image

/**
 * Infos given by the image request.
 *
 * Each requests give info for an image, this class keeps track of it.
 */
public class ImageResponse {

    internal var id: String? = null
    internal var type: String? = null
    internal var views: Int = 0
    internal var favorite: Boolean = false
    internal var link: String? = null
    internal var mp4: String? = null
    internal var gifv: String? = null
    internal var hls: String? = null
}