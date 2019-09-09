package com.dangerfield.barbrasbook.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface ArticlesDao {

    @Query("SELECT * from articleDataTable")
    fun getAll(): List<ArticleDataTable>

    @Insert(onConflict = REPLACE)
    fun update(articles: List<ArticleDataTable>)

    @Query("DELETE from articleDataTable")
    fun deleteAll()
}