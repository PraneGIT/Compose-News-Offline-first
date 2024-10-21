package com.example.compose_news.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.compose_news.viewModel.NewsViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NewsScreen(
    newsViewModel: NewsViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(16.dp)) // Add border here
        ) {
            TextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    newsViewModel.filterNews(it)
                },
                label = { Text("Search News Locally") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.Transparent, // Remove focused underline
                    unfocusedIndicatorColor = Color.Transparent // Remove unfocused underline
                ),
                shape = RoundedCornerShape(16.dp), // Keep the same shape as the border
                // To disable the underline for both focused and unfocused states
                textStyle = MaterialTheme.typography.bodyLarge, // Customize text style if needed
                placeholder = { Text("Type to search...") }, // Optional placeholder
                singleLine = true // Optional, depending on your design
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // News List
        NewsList(news = newsViewModel.filteredNews.value)
    }
}

