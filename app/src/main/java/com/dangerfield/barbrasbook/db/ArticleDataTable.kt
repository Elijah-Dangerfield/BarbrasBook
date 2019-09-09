package com.dangerfield.barbrasbook.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 *  Represents a table within the database.
 */
@Entity(tableName = "articleDataTable")
data class ArticleDataTable(@PrimaryKey(autoGenerate = true) var id: Long?,
                            @ColumnInfo(name="author") val author: String?,
                            @ColumnInfo(name="content") val content: String,
                            @ColumnInfo(name="description") val description: String,
                            @ColumnInfo(name="publishedAt") val publishedAt: String,
                            @ColumnInfo(name="source") val source: String,
                            @ColumnInfo(name="title") val title: String,
                            @ColumnInfo(name="url") val url: String,
                            @ColumnInfo(name="urlToImage") val urlToImage: String
){
    constructor():this(
        0,
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "")
}