package com.dangerfield.barbrasbook.db

import androidx.room.TypeConverter
import com.dangerfield.barbrasbook.model.Source

/**
 * Class is needed to store Articles in room database.
 */
class Converter {

    @TypeConverter
    fun stringToSource(source: String?): Source? {
        return source?.let { Source(it) }
    }

    @TypeConverter
    fun sourceToString(source: Source?): String? {
        return source?.name
    }
}


