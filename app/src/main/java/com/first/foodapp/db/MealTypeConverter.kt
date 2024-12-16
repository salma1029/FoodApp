package com.first.foodapp.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverter {

    @TypeConverter
    //Room will use it when inserting data to DB
    fun fromAnyToString(attribute : Any?) : String{
        if(attribute == null)
            return ""
        return attribute.toString()
    }

    @TypeConverter
    //Room will use it when retrieving data to DB
    fun fromStringToAny(attribute : String?) : Any{
        if(attribute == null)
            return ""
        return attribute
    }
}