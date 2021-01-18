package com.example.epicture

import java.io.Serializable

/**
 * Infos about imageList.
 *
 * All the infos, images per images, so it keeps track of each singular photos, even in an album.
 * @property id The ID of the image.
 * @property title The title of the image.
 * @property type gif/jpeg or png.
 * @property animated is the image a gif or not.
 * @property favorite if the image in the favorties of the user or not.
 * @property link The URL of the image.
 * @property ups The upvotes of the image.
 * @property downs The downvotes of the image.
 * @property points The score of the image.
 */
class ImageLib(id: String?, title: String?, type: String?, animated: Boolean,
               favorite: Boolean, link: String?, gifv: String?,
               ups: Int, downs: Int, points: Int?, score: Int?): Serializable {

    internal var id: String? = null
    internal var title: String? = null
    internal var type: String? = null
    internal var animated: Boolean = false
    internal var favorite: Boolean = false
    internal var link: String? = null
    internal var gifv: String? = null
    internal var ups: Int? = null
    internal var downs: Int? = null
    internal var points: Int? = null
    internal var score: Int? = null
}