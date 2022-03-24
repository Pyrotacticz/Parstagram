package com.jatruong.parstagram.network

import android.app.Application
import com.jatruong.parstagram.R
import com.jatruong.parstagram.model.Post
import com.parse.Parse
import com.parse.ParseObject

class ParstagramApp : Application() {

    override fun onCreate() {
        super.onCreate()

        ParseObject.registerSubclass(Post::class.java)

        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());
    }
}