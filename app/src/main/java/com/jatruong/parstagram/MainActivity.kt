package com.jatruong.parstagram

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jatruong.parstagram.fragments.ComposeFragment
import com.jatruong.parstagram.fragments.FeedFragment
import com.jatruong.parstagram.model.Post
import com.parse.*
import java.io.File

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
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    fragment = FeedFragment()
                }
                R.id.action_compose -> {
                    fragment = ComposeFragment()
                }
                R.id.action_profile -> {
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
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

    fun queryPosts() {
        // specify class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        // find all post objects
        query.include(Post.KEY_USER)
        query.findInBackground { posts, e ->
            if (e != null) {
                Log.e(TAG, "Error fetching posts")
            } else {
                if (posts != null) {
                    for (post in posts) {
                        Log.i(TAG, "Post: " + post.getDescription()
                                + ", User: " + post.getUser()?.username)
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}