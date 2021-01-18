package com.example.epicture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.content_main.*

/**
 * Main page
 *
 * Where the request for the imgur authentification is made.
 */
class MainActivity : AppCompatActivity() {
    private val url: String = Constants.BASE_URL + "oauth2/authorize?client_id=" + Constants.CLIENT_ID + "&response_type=" + Constants.RESPONSE_TYPE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.Login).setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
            }
        }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri = intent?.data
        val randomIntent = Intent(this, HomeVc::class.java)
        if (uri != null && uri.toString().startsWith("epicture.com://callback")) {
            val parsedUri = Uri.parse(uri.toString().replace('#', '?'))
            val accessToken: String = parsedUri.getQueryParameter("access_token").toString()
            val refreshToken: String = parsedUri.getQueryParameter("refresh_token").toString()
            val accountUsername: String = parsedUri.getQueryParameter("account_username").toString()
            val sharedPref = Preference(applicationContext)
            sharedPref.setAccessToken(accessToken)
            sharedPref.setRefreshToken(refreshToken)
            sharedPref.setAccountUsername(accountUsername)
            //randomIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(randomIntent)
            finish()
        }
    }
}
