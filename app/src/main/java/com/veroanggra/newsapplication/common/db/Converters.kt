package com.veroanggra.newsapplication.common.db

import androidx.room.TypeConverter
import com.veroanggra.newsapplication.common.model.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}