package com.example.epicture

import android.os.Bundle
import android.widget.Toast
import androidx.preference.PreferenceFragmentCompat
import kotlinx.android.synthetic.main.detail_photo.view.*
import retrofit2.Call
import retrofit2.Callback

/**
 * SettingsFragment class
 *
 * Fragment for the setting page.
 */
class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
    override fun onDetach() {
        super.onDetach()
        val preference = Preference(this.requireContext().applicationContext)
        val accountUsername = preference.getAccountUsername().toString()

        val client = ImgurClient.create()
        val call = client.putParametre(accountUsername,"Bearer " + Preference(this.requireContext()).getAccessToken(), "Epicture")

        call.enqueue(object : Callback<UploadResponse> {
            override fun onResponse(
                call: Call<UploadResponse>,
                response: retrofit2.Response<UploadResponse>
            ) {
                val body = response.body()
                println("REQUEST " + body)
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                println("REQUEST FAILED")
                println(t.message)
            }
        })
    }
}