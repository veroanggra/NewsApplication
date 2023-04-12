package com.veroanggra.newsapplication.feature.headline

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.veroanggra.newsapplication.MainActivity
import com.veroanggra.newsapplication.R
import com.veroanggra.newsapplication.common.util.Constants.QUERY_PAGE_SIZE
import com.veroanggra.newsapplication.common.util.Resource
import com.veroanggra.newsapplication.databinding.FragmentHeadlineNewsBinding
import com.veroanggra.newsapplication.feature.NewsAdapter
import com.veroanggra.newsapplication.feature.NewsViewModel


class HeadlineNewsFragment : Fragment() {
    private lateinit var binding: FragmentHeadlineNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeadlineNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        loadRecyclerView()
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_headlineNewsFragment_to_newsDetailFragment,
                bundle
            )
        }

        viewModel.headlineNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
//                hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.headlineNewsPage == totalPages
                    }
                }
                is Resource.Error -> {
//                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
//                    showProgressBar()
                }
            }
        })
    }

    private fun loadRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
//            addOnScrollListener(this@HeadlineNewsFragment.scrollListener)
        }
    }


    companion object {
        val TAG = "HeadlineNewsFragment"
    }
}