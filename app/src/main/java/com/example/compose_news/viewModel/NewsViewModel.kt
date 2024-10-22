package com.example.compose_news.viewModel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_news.Models.Article
import com.example.compose_news.Models.ArticleDao
import com.example.compose_news.data.NewsApiService
import com.example.compose_news.utils.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsApiService: NewsApiService,
    private val articleDao: ArticleDao,
    private val networkUtil: NetworkUtil
) : ViewModel() {

    private val _allNews = mutableStateOf<List<Article>>(emptyList())
    val allNews: State<List<Article>> = _allNews

    private val _filteredNews = mutableStateOf<List<Article>>(emptyList())
    val filteredNews: State<List<Article>> = _filteredNews

    init {
        fetchTopHeadlines()
    }

     private fun fetchTopHeadlines() {
        viewModelScope.launch {
            if(networkUtil.isNetworkAvailable()){
                try {
                    val response = newsApiService.getTopHeadlines(apiKey = "75bce8f0756b40a59b27d0ba8b2a5a0f")
                    _allNews.value = response.articles
                    // Initialize with all news
                    _filteredNews.value = response.articles
                    //first clear old cache
                    articleDao.deleteAllArticles()
                    //then add new
                    articleDao.insertAll(response.articles.map { Article(it.id, it.title, it.description, it.urlToImage?:"https://placehold.co/600x400",it.url) })
                } catch (e: Exception) {
                    e.printStackTrace()
                    loadArticlesFromDatabase()
                }
            }else{
                loadArticlesFromDatabase()
            }

        }
    }

    fun filterNews(query: String) {
        _filteredNews.value = if (query.isEmpty()) {
            _allNews.value
        } else {
            _allNews.value.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }
    }

    private fun loadArticlesFromDatabase() {
        viewModelScope.launch {
            articleDao.getAllArticles().collect { articles ->
                _allNews.value = articles.map { Article(it.id,it.title, it.description, it.urlToImage, it.url) }
                _filteredNews.value = articles.map { Article(it.id,it.title, it.description, it.urlToImage, it.url) }
            }
        }
    }
}
