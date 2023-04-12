package com.veroanggra.newsapplication.common.repository

import com.veroanggra.newsapplication.common.db.NewsDatabase
import com.veroanggra.newsapplication.common.model.News
import com.veroanggra.newsapplication.common.service.RetrofitConfiguration

class NewsRepository(
    val db: NewsDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitConfiguration.retrofitConfig.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitConfiguration.retrofitConfig.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(news: News) = db.getNewsDao().upsert(news)

    fun getSavedNews() = db.getNewsDao().getAllNews()

    suspend fun deleteArticle(news: News) = db.getNewsDao().deleteNews(news)
}