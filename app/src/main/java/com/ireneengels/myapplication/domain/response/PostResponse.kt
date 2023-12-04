package com.ireneengels.myapplication.domain.response

import com.ireneengels.myapplication.domain.model.Post

data class PostResponse(
    val page: String,
    val total_pages: Int,
    val posts: List<Post>
)
