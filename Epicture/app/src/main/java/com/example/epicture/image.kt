package com.example.epicture

import android.media.Image
import java.io.Serializable

/**
 * Infos of an image.
 *
 * Put all the infos from an imgur image into a class.
 * @property id The ID of the image.
 * @property title The title of the image.
 * @property description The description of the image.
 * @property link The URL of the image.
 * @property type gif/jpeg or png
 * @property ups The upvotes of the image.
 * @property downs The downvotes of the image.
 * @property points The score of the image.
 * @property favorite The number of time the image has been added as favorite.
 * @property is_album If the page multiple images.
 * @property images list of [ImageLib].
 */
public class Content(id: String, title: String?, description: String?, link: String?, type: String?, ups: Int, downs: Int,
                     points: Int, favorite: Boolean, is_album: Boolean, images: List<ImageLib>?): Serializable {

    internal var id: String? = null
    internal var title: String? = null
    internal var description: String? = null
    internal var link: String? = null
    internal var type: String? = null
    internal var ups: Int = 0
    internal var downs: Int = 0
    internal var points: Int = 0
    internal var favorite: Boolean = false
    internal var is_album: Boolean = false
    internal var images: List<ImageLib>? = null
}