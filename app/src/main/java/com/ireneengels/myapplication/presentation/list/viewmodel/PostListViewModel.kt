package com.ireneengels.myapplication.presentation.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ireneengels.myapplication.data.FetchPostsUseCase
import com.ireneengels.myapplication.domain.base_usecase.ResultCallbacks
import com.ireneengels.myapplication.presentation.list.state.PostListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val fetchPostsUseCase: FetchPostsUseCase
) : ViewModel() {

    private var currentPage = 1
    private var isLoading = false
    private var totalPages = 4

    private var _stateFlow = MutableStateFlow<PostListState>(PostListState.Loading)
    val stateFlow: StateFlow<PostListState> = _stateFlow

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        if (isLoading || currentPage > totalPages) return

        isLoading = true
        _stateFlow.value = if (currentPage == 1) PostListState.Loading else PostListState.LoadingNextPage

        fetchPostsUseCase.execute(viewModelScope, ResultCallbacks(
            onSuccess = { response ->
                totalPages = response.total_pages
                _stateFlow.value = PostListState.Success(response.posts)
                currentPage++
            },
            onLoading = { isLoading = it },
            onError = { error ->
                _stateFlow.value = PostListState.Error(error)
            }
        ), currentPage)
    }
}


