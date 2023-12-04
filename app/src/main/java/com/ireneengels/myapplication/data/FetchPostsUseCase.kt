package com.ireneengels.myapplication.data

import com.ireneengels.myapplication.domain.base_usecase.BaseUseCase
import com.ireneengels.myapplication.domain.response.PostResponse
import javax.inject.Inject

class FetchPostsUseCase @Inject constructor(
    private val postsRepository: PostsRepositoryImpl
) : BaseUseCase<PostResponse, Int>() {

    override suspend fun remoteWork(params: Int?): PostResponse {
        return postsRepository.fetchPostsData(params ?: 1)
    }
}