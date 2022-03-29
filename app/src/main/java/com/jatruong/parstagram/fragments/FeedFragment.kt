package com.jatruong.parstagram.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jatruong.parstagram.MainActivity
import com.jatruong.parstagram.PostAdapter
import com.jatruong.parstagram.R
import com.jatruong.parstagram.model.Post
import com.parse.ParseQuery
import com.parse.ParseUser

open class FeedFragment : Fragment() {
    lateinit var postsRecyclerView: RecyclerView
    lateinit var adapter: PostAdapter
    var allPosts: MutableList<Post> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postsRecyclerView = view.findViewById<RecyclerView>(R.id.postsRecyclerView)
        adapter = PostAdapter(requireContext(), allPosts)
        postsRecyclerView.adapter = adapter

        postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        queryPosts()
    }

    open fun queryPosts() {
        // specify class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        // find all post objects
        query.include(Post.KEY_USER)
        query.addDescendingOrder("createdAt")
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

    companion object {
        const val TAG = "FeedFragment"
    }
}