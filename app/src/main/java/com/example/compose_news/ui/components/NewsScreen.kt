package com.example.compose_news.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.compose_news.viewModel.NewsViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose_news.Models.Article
import kotlinx.coroutines.launch
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.ui.platform.LocalContext
import com.example.compose_news.utils.NetworkUtil
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun NewsScreen(
    newsViewModel: NewsViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var selectedArticle by remember { mutableStateOf<Article?>(null) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    // LaunchedEffect to show Snackbar and handle retry
    LaunchedEffect(key1 = Unit) {
        while (true) {
            // If no internet, show the snackbar
            if (!newsViewModel.networkUtil.isNetworkAvailable()) {
                val result = snackbarHostState.showSnackbar(
                    message = "No Internet Connection",
                    actionLabel = "Retry"
                )

                // Retry action clicked
                if (result == SnackbarResult.ActionPerformed) {
                    // Try to fetch headlines again
                    newsViewModel.fetchTopHeadlines()
                }
            } else {
                break // If network is available, exit the loop
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(16.dp),
        sheetContent = {
            selectedArticle?.let { article ->
                Surface(
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp
                    ), // curved corners
                ) {
                    NewsDetailsBottomSheet(
                        article = article,
                        bottomSheetState = bottomSheetState,
                        onCloseSheet = {
                            scope.launch {
                                bottomSheetState.hide()
                            }
                        }
                    )
                }
            }
        }
    ) {
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
                    .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(16.dp))
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        newsViewModel.filterNews(it)
                    },
                    label = { Text("Search News") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(16.dp),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    placeholder = { Text("Type to search...") },
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Snackbar Host
            SnackbarHost(hostState = snackbarHostState)

            // News List
            NewsList(
                news = newsViewModel.filteredNews.value,
                onArticleClick = { article ->
                    selectedArticle = article
                    scope.launch {
                        bottomSheetState.show() // Await show
                    }
                }
            )

        }
    }
}


