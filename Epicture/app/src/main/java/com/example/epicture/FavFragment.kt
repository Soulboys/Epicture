package com.example.epicture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback

/**
 * A fragment for favorites.
 *
 * The class is to add the favorites button, and make it work in async.
 */
class FavFragment : Fragment() {
    var rvMain: RecyclerView? = null
    var imageList: List<Content>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_fav, container, false)
        val preference = Preference(this.requireContext().applicationContext)
        val sort: String? = preference.getRequestSort()
        val refreshToken = preference.getRefreshToken()
        val accessToken = preference.getAccessToken()
        val accountUsername = preference.getAccountUsername()

        if (accountUsername != null && sort != null) {
            fetchTrendImgur(accountUsername, sort)
        }
        rvMain = view.findViewById(R.id.recyclerView_fav) as RecyclerView
        rvMain?.layoutManager = LinearLayoutManager(this.requireContext())

        setHasOptionsMenu(true)
        println("Access == " + accessToken + " " + refreshToken + " " + accountUsername)

        return view
    }
    fun fetchTrendImgur(accountUsername: String, sort: String) {

        val client = ImgurClient.create()
        val call = client.getfavoriteImage(
            accountUsername,"Bearer " + Preference(this.requireContext()).getAccessToken(), "Epicture")

        call.enqueue(object : Callback<ImgurResponse> {
            override fun onResponse(
                call: Call<ImgurResponse>,
                response: retrofit2.Response<ImgurResponse>
            ) {
                val body = response.body()
                imageList = body?.data
                rvMain?.adapter = MainAdapter(imageList, requireContext())

                println("REQUEST " + body)
            }

            override fun onFailure(call: Call<ImgurResponse>, t: Throwable) {
                println("REQUEST FAILED")
                println(t.message)
            }
        })

    }
}