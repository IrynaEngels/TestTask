package com.ireneengels.myapplication.presentation.list.adapter

import android.graphics.drawable.Drawable
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ireneengels.myapplication.R
import com.ireneengels.myapplication.domain.model.Post
import com.ireneengels.myapplication.util.logError

class PostsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvUserName: TextView = view.findViewById(R.id.tvUserName)
    val tvDate: TextView = view.findViewById(R.id.tvDate)
    val ivUserPic: ImageView = view.findViewById(R.id.ivUserPic)
    val tvMessage: TextView = view.findViewById(R.id.tvMessage)
    val ivPhoto: ImageView = view.findViewById(R.id.ivPhoto)

    fun bind(post: Post) {
       tvUserName.text = post.user_name

        val dateStringUTC = post.date.replace(" +0000", " UTC")
        tvDate.text = dateStringUTC

        tvMessage.text = post.message
        Linkify.addLinks(tvMessage, Linkify.WEB_URLS)

        Glide.with(ivUserPic.context)
            .load(post.user_pic)
            .placeholder(R.drawable.avatar)
            .circleCrop()
            .into(ivUserPic)

        if (post.photo != null) {
            ivPhoto.visibility = View.VISIBLE
            Glide.with(ivPhoto.context)
                .load(post.photo)
                .placeholder(R.drawable.placeholder)
                .listener(object : RequestListener<Drawable> {


                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        logError("Error loading image")
                        e?.let { logError(it.message.toString()) }
                        return false
                    }

                })
                .into(ivPhoto)
        } else {
            ivPhoto.visibility = View.GONE
        }

        if (tvMessage.linksClickable) {
            tvMessage.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}