package com.example.compose_news.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.compose_news.Models.Article
import com.example.compose_news.Models.ArticleDao

@Database(entities = [Article::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}