package com.example.compose_news.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.compose_news.Models.Article

@Composable
fun NewsList(news: List<Article>) {
    LazyColumn {
        items(news.size) { index ->
            NewsItem(article = news[index]) {
                //todo click
            }
        }
    }
}