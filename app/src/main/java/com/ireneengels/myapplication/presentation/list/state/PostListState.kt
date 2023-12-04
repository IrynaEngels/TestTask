package com.ireneengels.myapplication.presentation.list.state

import com.ireneengels.myapplication.domain.model.Post

sealed class PostListState {
    object Loading : PostListState()
    object LoadingNextPage : PostListState()
    data class Success(val posts: List<Post>) : PostListState()
    data class Error(val error: Throwable) : PostListState()
}