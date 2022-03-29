package com.jatruong.parstagram

import android.os.Bundle
import android.view.Menu
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jatruong.parstagram.fragments.ComposeFragment
import com.jatruong.parstagram.fragments.FeedFragment
import com.jatruong.parstagram.fragments.ProfileFragment
import com.parse.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager

        val bottomNavigationView: BottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.action_home -> {
                    fragment = FeedFragment()
                }
                R.id.action_compose -> {
                    fragment = ComposeFragment()
                }
                R.id.action_profile -> {
                    fragment = ProfileFragment()
                }
            }
            if (fragment != null) {
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit()
            }
            // return true to say that we handled the interaction
            true
        }

        bottomNavigationView.selectedItemId = R.id.action_home
        //queryPosts()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}