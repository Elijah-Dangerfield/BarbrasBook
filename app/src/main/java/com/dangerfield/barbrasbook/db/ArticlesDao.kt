package com.dangerfield.barbrasbook.db

import androidx.room.*
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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<Article>)

    /**
     * removes all articles in the database
     */
    @Query("DELETE from articleDataTable")
    fun deleteAll()

    /**
     * replaces all data in database. Chosen to do this rather than
     * only replacing on conflict because as time goes on, users will retain
     * ALL articles ever seen. Data will accumulate. This will only hold on to the
     * most recent data :)
     */
    @Transaction
    fun updateAll(articles: List<Article>) {
        deleteAll()
        insertAll(articles)
    }
}