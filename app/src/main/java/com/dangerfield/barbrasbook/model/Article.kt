package com.dangerfield.barbrasbook.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 *  Represents and article from News API. Annotations allow for
 *  serialization with Retrofit as well as Column definitions for Room database
 */
@Entity(tableName = "articleDataTable")
@Parcelize
data class Article(@PrimaryKey(autoGenerate = true) var id: Long?,

                   @SerializedName("author") @ColumnInfo(name="author")  var author: String?,
                   @SerializedName("content") @ColumnInfo(name="content") var content: String? = "",
                   @SerializedName("description") @ColumnInfo(name="description") var description: String? = "",
                   @SerializedName("publishedAt")  @ColumnInfo(name="publishedAt")var publishedAt: String? = "",
                   @SerializedName("source")  @ColumnInfo(name="source") var source: Source = Source(""),
                   @SerializedName("title") @ColumnInfo(name="title") var title: String? = "",
                   @SerializedName("url") @ColumnInfo(name="url") var url: String? = "",
                   @SerializedName("urlToImage") @ColumnInfo(name="urlToImage") var urlToImage: String? = "",
                  @Ignore var expandedStatus: ExpandedStatus?
) : Parcelable {
    constructor(): this(null,
        "",
        "",
        "",
        "",
        Source(""),
        "",
        "",
        "",
        null)
}

 enum class ExpandedStatus {
    EXPANDED,
    COLLAPSED
}


