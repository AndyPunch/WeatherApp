package com.example.weatherapp.domain.util.moshi

import android.content.SharedPreferences
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.Types
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.IOException
import java.util.Date
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty

inline fun <reified T, reified PARAM> Moshi.parametrizedAdapter(): JsonAdapter<T> =
    adapter(Types.newParameterizedType(T::class.java, PARAM::class.java))

inline fun <reified T> Moshi.listAdapter(): JsonAdapter<List<T>> =
    adapter(Types.newParameterizedType(List::class.java, T::class.java))

inline fun <reified T : Enum<T>> EnumWithDefaultValueJsonAdapter(default: T) =
    MoshiUtils.EnumWithDefaultValueJsonAdapter(T::class.java, default)

object MoshiUtils {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        .build()

    inline fun <reified T> toJson(t: T): String = moshi.adapter(T::class.java).toJson(t)
    inline fun <reified T> fromJson(s: String?): T? =
        s?.let { moshi.adapter(T::class.java).fromJson(s) }

    inline fun <reified T> listToJson(t: List<T>): String = moshi.listAdapter<T>().toJson(t)
    inline fun <reified T> listFromJson(string: String): List<T>? =
        moshi.listAdapter<T>().fromJson(string)

    class EnumWithDefaultValueJsonAdapter<T : Enum<T>>(
        private val enumType: Class<T>,
        private val defaultValue: T
    ) : JsonAdapter<T>() {
        private val nameStrings: Array<String?>
        private val constants: Array<T>
        private val options: JsonReader.Options

        init {
            try {
                constants = enumType.enumConstants!!
                nameStrings = arrayOfNulls<String>(constants.size)
                for (i in constants.indices) {
                    val constant = constants[i]
                    val annotation =
                        enumType.getField(constant.name).getAnnotation(Json::class.java)
                    val name = if (annotation != null) annotation.name else constant.name
                    nameStrings[i] = name
                }
                options = JsonReader.Options.of(*nameStrings)
            } catch (e: NoSuchFieldException) {
                throw AssertionError(e)
            }

        }

        @FromJson
        @Throws(IOException::class)
        override fun fromJson(reader: JsonReader): T? {
            val index = reader.selectString(options)
            if (index != -1) return constants[index]

            return defaultValue
        }

        @ToJson
        @Throws(IOException::class)
        override fun toJson(writer: JsonWriter, value: T?) {
            writer.value(nameStrings[value!!.ordinal])
        }

        override fun toString(): String {
            return "JsonAdapter(" + enumType.name + ").defaultValue( " + defaultValue + ")"
        }
    }

}

inline fun <reified T> SharedPreferences.moshiProperty(key: String, initialValue: T? = null)
        : ReadWriteProperty<Any?, T?> =
    Delegates.observable(
        MoshiUtils.fromJson<T>(getString(key, null))
            ?: initialValue
    ) { _, _, newValue ->
        edit().putString(key, MoshiUtils.toJson(newValue)).apply()
    }
