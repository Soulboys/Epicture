package com.example.epicture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Home page.
 *
 * Handle the navigation and the fragments.
 */
class HomeVc : AppCompatActivity() {
        lateinit var selectedFragment: Fragment

         override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home_vc)

            val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
            bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

            setFragment(HomeFragment())
        }

         private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.nav_parametre -> {
                    selectedFragment = SettingsFragment()
                    setFragment(selectedFragment)

                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_upload -> {
                selectedFragment = UploadFragment()
                setFragment(selectedFragment)

                return@OnNavigationItemSelectedListener true
            }
                R.id.nav_home -> {
                    selectedFragment = HomeFragment()
                    setFragment(selectedFragment)

                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_favorite -> {
                    selectedFragment = FavFragment()
                    setFragment(selectedFragment)

                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_profil -> {
                    selectedFragment = ProfilFragment()
                    setFragment(selectedFragment)

                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
    private fun setFragment(selectedFragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()

    }

    }
