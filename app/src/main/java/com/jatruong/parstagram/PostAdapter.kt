package com.jatruong.parstagram

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jatruong.parstagram.model.Post

class PostAdapter(val context: Context, val posts: MutableList<Post>, val grid: Boolean)
    : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    class ViewHolder(iView: View) : RecyclerView.ViewHolder(iView) {
        val tvUsername: TextView?
        val ivImage: ImageView
        val tvDescription: TextView?

        init {
            tvUsername = iView.findViewById(R.id.postOwner)
            ivImage = iView.findViewById(R.id.postImage)
            tvDescription = iView.findViewById(R.id.postDescription)
        }

        open fun bind(post: Post) {
            tvDescription?.text = post.getDescription()
            tvUsername?.text = post.getUser()?.username
            Glide.with(itemView.context).load(post.getImage()?.url).into(ivImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder {

        val view = if (!grid) LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        else LayoutInflater.from(context).inflate(R.layout.item_profile_post, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun clear() {
        posts.clear()
        notifyDataSetChanged()
    }

    fun addAll(newPosts : List<Post>) {
        posts.addAll(newPosts)
        notifyDataSetChanged()
    }
}