package com.ireneengels.myapplication.domain

import com.ireneengels.myapplication.domain.response.PostResponse

interface PostsRepository {

    suspend fun fetchPostsData(page: Int): PostResponse
}