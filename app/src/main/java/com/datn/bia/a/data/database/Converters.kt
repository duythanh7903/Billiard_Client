package com.datn.bia.a.data.database

import androidx.room.TypeConverter
import com.datn.bia.a.domain.model.dto.res.OrderProduct
import com.datn.bia.a.domain.model.dto.res.ResVariantDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromOrderProductList(value: List<OrderProduct>?): String {
        return gson.toJson(value ?: emptyList<OrderProduct>())
    }

    // String -> List<OrderProduct>
    @TypeConverter
    fun toOrderProductList(value: String?): List<OrderProduct> {
        if (value.isNullOrEmpty()) return emptyList()

        val type = object : TypeToken<List<OrderProduct>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromVariantsProductList(value: List<ResVariantDTO>?): String =
        gson.toJson(value ?: emptyList<ResVariantDTO>())

    @TypeConverter
    fun toVariantsProductList(value: String?): List<ResVariantDTO> {
        if (value.isNullOrEmpty()) return emptyList()

        val type = object : TypeToken<List<ResVariantDTO>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromVariant(variant: ResVariantDTO): String {
        return gson.toJson(variant)
    }

    @TypeConverter
    fun toVariant(variantString: String): ResVariantDTO {
        val type = object : TypeToken<ResVariantDTO>() {}.type
        return gson.fromJson(variantString, type)
    }
}
