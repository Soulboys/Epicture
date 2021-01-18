package com.example.epicture

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.epicture.Preference
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.epicture.ImgurClient
import com.example.epicture.ImgurResponse
import retrofit2.Call
import retrofit2.Callback

/**
 * An fragment for the Home page.
 *
 * The class is to create the home page and add the images, and make it work in async.
 */
class HomeFragment : Fragment() {

    var rvMain: RecyclerView? = null
    var imageList: List<Content>? = null
    var searchBar: SearchView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val preference = Preference(this.requireContext().applicationContext)

        val section: String? = preference.getRequestSection()
        val sort: String? = preference.getRequestSort()

        if (section != null && sort != null) {
            fetchTrendImgur(section, sort)
        }

        val accessToken = preference.getAccessToken()
        val refreshToken = preference.getRefreshToken()
        val accountUsername = preference.getAccountUsername()

        rvMain = view.findViewById(R.id.recyclerView_main) as RecyclerView
        rvMain?.layoutManager = LinearLayoutManager(this.requireContext())
        searchBar = view.findViewById(R.id.searchBar)

        setHasOptionsMenu(true)

        searchBar?.setOnQueryTextListener(object: SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query == null || query.isEmpty())
                    fetchTrendImgur(section.toString(), sort.toString())
                else
                    fetchSearchImgur(sort.toString(), query)

                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                return true
            }
        })
        println("Access == " + accessToken + " " + refreshToken + " " + accountUsername)

        return view
    }

    fun fetchTrendImgur(section: String, sort: String) {

        val client = ImgurClient.create()
        val call = client.getTrendImages(section, sort,"Bearer " + Preference(this.requireContext()).getAccessToken(), "Epicture")

        call.enqueue(object: Callback<ImgurResponse> {
            override fun onResponse(call: Call<ImgurResponse>, response: retrofit2.Response<ImgurResponse>) {
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

    fun fetchSearchImgur(sort: String, search: String) {

        val client = ImgurClient.create()
        val call = client.getSearch(sort ,search, "Bearer " + Preference(this.requireContext()).getAccessToken(), "Epicture")

        call.enqueue(object: Callback<ImgurResponse> {
            override fun onResponse(
                call: Call<ImgurResponse>, response: retrofit2.Response<ImgurResponse>) {
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

