package com.example.compose_news.Models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<Article>)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<Article>>
    @Query("DELETE FROM articles")
    suspend fun deleteAllArticles()
}
