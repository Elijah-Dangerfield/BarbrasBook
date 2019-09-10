package com.dangerfield.barbrasbook.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dangerfield.barbrasbook.model.Article

@Dao
interface ArticlesDao {

    /**
     * returns all articles in table
     */
    @Query("SELECT * from articleDataTable")
    fun getAll(): List<Article>

    /**
     * inserts all passed articles into database
     */
    @Insert
    fun insertAll(vararg articles: Article)

    /**
     * removes all articles in the database
     */
    @Query("DELETE from articleDataTable")
    fun deleteAll()
}