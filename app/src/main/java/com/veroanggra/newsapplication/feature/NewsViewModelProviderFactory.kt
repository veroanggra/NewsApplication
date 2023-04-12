package com.veroanggra.newsapplication.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.veroanggra.newsapplication.common.repository.NewsRepository

class NewsViewModelProviderFactory(val newsRepository: NewsRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository = newsRepository) as T
    }
}
