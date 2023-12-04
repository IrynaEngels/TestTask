package com.ireneengels.myapplication.presentation.list.adapter

import android.graphics.drawable.Drawable
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ireneengels.myapplication.R
import com.ireneengels.myapplication.domain.model.Post
import com.ireneengels.myapplication.util.logError
import javax.sql.DataSource

class PostsAdapter(
    private var items: MutableList<Post>,
    private var isLoadingAdded: Boolean = false
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_post, parent, false)
            PostsViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoadingAdded && position == items.size) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            val post = items[position]
            (holder as PostsViewHolder).bind(post)
        }

    }

    override fun getItemCount(): Int {
        return items.size + if (isLoadingAdded) 1 else 0
    }

    fun appendData(newItems: List<Post>) {
        val startPosition = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(startPosition, newItems.size)
    }

    fun showLoading() {
        if (!isLoadingAdded) {
            isLoadingAdded = true
            notifyItemInserted(items.size)
        }
    }

    fun hideLoading() {
        if (isLoadingAdded) {
            isLoadingAdded = false
            val position = items.size
            notifyItemRemoved(position)
        }
    }
}
