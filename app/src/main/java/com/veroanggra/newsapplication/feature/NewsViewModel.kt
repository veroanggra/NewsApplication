package com.veroanggra.newsapplication.feature

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veroanggra.newsapplication.common.model.News
import com.veroanggra.newsapplication.common.model.NewsResponse
import com.veroanggra.newsapplication.common.repository.NewsRepository
import com.veroanggra.newsapplication.common.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {
    val headlineNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var headlineNewsPage = 1
    var headlineNewsResponse: NewsResponse? = null

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        headlineNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, headlineNewsPage)
        headlineNews.postValue(handleHeadlineNewsResponse(response))
    }

    private fun handleHeadlineNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                headlineNewsPage++
                if (headlineNewsResponse == null) {
                    headlineNewsResponse = resultResponse
                } else {
                    val oldArticles = headlineNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(headlineNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if (searchNewsResponse == null) {
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: News) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: News) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}