package com.jatruong.parstagram.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.jatruong.parstagram.PostAdapter
import com.jatruong.parstagram.model.Post
import com.parse.ParseQuery
import com.parse.ParseUser

class ProfileFragment : FeedFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = ParseUser.getCurrentUser().username

        adapter = PostAdapter(requireContext(), allPosts, true)
        postsRecyclerView.adapter = adapter
        postsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

    }

    override fun queryPosts() {
        // specify class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        // find all post objects
        query.include(Post.KEY_USER)
        query.addDescendingOrder("createdAt")
        // only returns posts from currently signed in user
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())
        // limits the query results
        query.limit = 20
        query.findInBackground { posts, e ->
            if (e != null) {
                Log.e(TAG, "Error fetching posts: $e")
            } else {
                if (posts != null) {
                    for (post in posts) {
                        Log.i(
                            TAG, "Post: " + post.getDescription()
                                    + ", User: " + post.getUser()?.username
                                    + ", Url: " + post.getImage()?.url)
                    }
                    allPosts.addAll(posts)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}