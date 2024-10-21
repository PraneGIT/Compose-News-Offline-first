package com.example.compose_news.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_news.Models.Article
import com.example.compose_news.data.NewsApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsApiService: NewsApiService
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
            try {
                val response = newsApiService.getTopHeadlines(apiKey = "75bce8f0756b40a59b27d0ba8b2a5a0f")
                _allNews.value = response.articles
                _filteredNews.value = response.articles // Initialize with all news
            } catch (e: Exception) {
                e.printStackTrace()
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
}
