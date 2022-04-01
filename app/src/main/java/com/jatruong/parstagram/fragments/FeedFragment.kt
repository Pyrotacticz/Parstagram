package com.jatruong.parstagram.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jatruong.parstagram.PostAdapter
import com.jatruong.parstagram.R
import com.jatruong.parstagram.model.Post
import com.parse.ParseQuery

open class FeedFragment : Fragment() {
    lateinit var postsRecyclerView: RecyclerView
    lateinit var adapter: PostAdapter
    lateinit var swipeContainer: SwipeRefreshLayout
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
        requireActivity().title = "Parstagram"

        postsRecyclerView = view.findViewById<RecyclerView>(R.id.postsRecyclerView)
        adapter = PostAdapter(requireContext(), allPosts, false)
        postsRecyclerView.adapter = adapter
        swipeContainer = view.findViewById(R.id.swipeContainer)
        swipeContainer.setOnRefreshListener {
            queryNewPosts()
        }

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);

        postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        queryPosts()
    }

    private fun queryNewPosts() {
        adapter.clear()
        queryPosts()
        swipeContainer.isRefreshing = false
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