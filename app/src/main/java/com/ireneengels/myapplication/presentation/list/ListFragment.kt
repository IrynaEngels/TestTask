package com.ireneengels.myapplication.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels


import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ireneengels.myapplication.R
import com.ireneengels.myapplication.presentation.list.adapter.PostsAdapter
import com.ireneengels.myapplication.presentation.list.state.PostListState
import com.ireneengels.myapplication.presentation.list.viewmodel.PostListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val viewModel: PostListViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView
    private val postsAdapter = PostsAdapter(mutableListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = view.findViewById(R.id.posts_recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)
        errorTextView = view.findViewById(R.id.error_text_view)
        setupRecyclerView()
        setupScrollListener()
        observePostListState()
        return view
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = postsAdapter
        }
    }

    private fun setupScrollListener() {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0) return // Check if scrolling down

                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                    viewModel.fetchPosts()
                }
            }
        })
    }

    private fun observePostListState() {
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collect { state ->
                when (state) {
                    is PostListState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                        errorTextView.visibility = View.GONE
                    }
                    is PostListState.LoadingNextPage -> postsAdapter.showLoading()
                    is PostListState.Success -> {
                        progressBar.visibility = View.GONE
                        postsAdapter.hideLoading()
                        postsAdapter.appendData(state.posts)
                    }
                    is PostListState.Error -> {
                        progressBar.visibility = View.GONE
                        errorTextView.visibility = View.VISIBLE
                        errorTextView.text = state.error.localizedMessage
                    }
                }
            }
        }
    }
}

