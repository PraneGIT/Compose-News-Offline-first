package com.example.compose_news.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.compose_news.Models.Article

@Composable
fun NewsList(
    news: List<Article>,
    onArticleClick: (Article) -> Unit
) {
    LazyColumn {
        items(news.size) { index ->
            NewsItem(article = news[index]) {
                onArticleClick(news[index]) // Trigger the click event for the article
            }
        }
    }
}