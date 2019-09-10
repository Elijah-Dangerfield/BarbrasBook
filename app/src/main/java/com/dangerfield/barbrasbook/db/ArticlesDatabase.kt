package com.dangerfield.barbrasbook.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dangerfield.barbrasbook.model.Article

@TypeConverters(Converter::class)
@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class ArticlesDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticlesDao

    companion object {
        @Volatile private var instance: ArticlesDatabase? = null
        private val LOCK = Any()

        /**
         * using invoke will make it very simple to create a database instance
         * val db = ArticlesDatabase(context)
         */
        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            ArticlesDatabase::class.java, "articles.db")
            .build()
    }
}