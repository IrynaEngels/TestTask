package com.ireneengels.myapplication.data

import com.ireneengels.myapplication.domain.PostsRepository
import com.ireneengels.myapplication.domain.response.PostResponse
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(private val apiService: ApiService): PostsRepository {

    override suspend fun fetchPostsData(page: Int): PostResponse {
        return apiService.fetchPostsData(page)
    }

}