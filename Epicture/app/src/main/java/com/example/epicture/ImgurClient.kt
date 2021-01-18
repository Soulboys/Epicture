package com.example.epicture

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * The imgur client with the list of the requests.
 *
 * The imgur client with the list of all the possible requests for every implementation.
 */
public interface ImgurClient {

    companion object {
        fun create(): ImgurClient {

            val builder = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())

            val retrofit: Retrofit = builder.build()
            return retrofit.create(ImgurClient::class.java)
        }
    }

    @GET("3/gallery/{section}/{sort}/0.json")
    fun getTrendImages(@Path("section", encoded = true) section: String,
                       @Path("sort", encoded = true) sort: String,
                       @Header("Authorization") contentRange: String,
                       @Header("User-Agent") userAgent: String): Call<ImgurResponse>

    @GET("3/gallery/search/{sort}")
    fun getSearch(@Path("sort", encoded = true) sort: String,
                  @Query("q") search: String,
                  @Header("Authorization") contentRange: String,
                  @Header("User-Agent") userAgent: String): Call<ImgurResponse>

    @GET("3/account/{username}/images")
    fun getUploadedImage(@Path("username", encoded = true) username: String,
                         @Header("Authorization") contentRange: String,
                         @Header("User-Agent") userAgent: String): Call<ImgurResponse>

    @DELETE("3/image/{id}")
    fun deleteImage(@Path("id", encoded = true) id: String,
                    @Header("Authorization") contentRange: String,
                    @Header("User-Agent") userAgent: String): Call<UploadResponse>

    @FormUrlEncoded
    @POST("3/image")
    fun uploadImage(@Field("image") image: String,
                    @Field("title") title: String,
                    @Field("name") name: String,
                    @Field("description") description: String,
                    @Header("Authorization") contentRange: String,
                    @Header("User-Agent") userAgent: String): Call<UploadResponse>

    @POST("3/image/{id}/favorite")
    fun favoriteImage(@Path("id", encoded = true) id: String,
                      @Header("Authorization") contentRange: String,
                      @Header("User-Agent") userAgent: String): Call<UploadResponse>

    @GET("3/account/{username}/favorites/0")
    fun getfavoriteImage(@Path("username", encoded = true) username: String,
                      @Header("Authorization") contentRange: String,
                      @Header("User-Agent") userAgent: String): Call<ImgurResponse>

    @PUT("https://api.imgur.com/3/account/{username}/settings")
    fun putParametre(@Path("username", encoded = true ) username: String,
                     @Header("Authorization") contentRange: String,
                     @Header("User-Agent") userAgent: String): Call<UploadResponse>
}