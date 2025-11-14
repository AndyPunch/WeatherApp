package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.model.Forecast
import com.example.weatherapp.data.model.Forecastday
import com.example.weatherapp.data.model.Hour
import com.example.weatherapp.data.model.WeatherDto
import com.example.weatherapp.domain.entity.ConditionData
import com.example.weatherapp.domain.entity.CurrentData
import com.example.weatherapp.domain.entity.DateTime
import com.example.weatherapp.domain.entity.ForecastData
import com.example.weatherapp.domain.entity.ForecastDayData
import com.example.weatherapp.domain.entity.HourData
import com.example.weatherapp.domain.entity.WeatherData
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

const val DATE_HOUR_PATTERN = "yyyy-MM-dd HH:mm"
const val HOUR_PATTERN = "HH"
const val HOURS_MINUTES_FORMAT = "%02d:%02d"
fun WeatherDto.toWeatherData(): WeatherData {
    val dateTime = getDateTime(location.localtime)
    val icon = getIconUrl(current.condition.icon)
    val forecast = forecast.toForecastData()
    return WeatherData(
        name = location.name,
        localDateTime = location.localtime,
        localTime = dateTime.timeStr,
        localDate = dateTime.dateStr,
        current = CurrentData(
            temperature = current.tempC.roundToInt(),
            condition = ConditionData(
                text = current.condition.text,
                icon = icon
            )
        ),
        forecast = forecast
    )
}

fun Forecast.toForecastData(): ForecastData {
    val list = mutableListOf<ForecastDayData>()
    forecastday.forEachIndexed { index, forecastDay ->
        val result = forecastDay.toForecastDayData(index)
        list.add(result)
    }
    return ForecastData(
        forecastDayData = list
    )
}


fun Forecastday.toForecastDayData(index: Int): ForecastDayData {
    val list = mutableListOf<HourData>()
    val currentHourStr = getCurrentTime(HOUR_PATTERN)
    hour.forEach { hour ->
        val result = hour.toHourData()
        if(index == 0) {
            val isFirstHourAfterSecond = isFirstHourAfterSecond(
                currentHourStr,
                result.hourStr)
            if(isFirstHourAfterSecond) {
                list.add(result)
            }
        } else {
            list.add(result)
        }
    }
    return ForecastDayData(
        date = date,
        hourData = list,
        )
}

fun Hour.toHourData(): HourData {
    val dateTime = getDateTime(time)
    val icon = getIconUrl(condition.icon)
    return HourData(
        localDateTime = time,
        localTime = dateTime.timeStr,
        localDate = dateTime.dateStr,
        temperature = tempC.roundToInt(),
        condition = ConditionData(
            text = condition.text,
            icon = icon
        ),
        hourStr = dateTime.hourStr

    )
}


fun getDateTime(dateTime: String, pattern: String = DATE_HOUR_PATTERN): DateTime {
    val inputFormatter = DateTimeFormatter.ofPattern(pattern)
    val dateTime = LocalDateTime.parse(dateTime, inputFormatter)
    val hours = dateTime.hour
    val minutes = dateTime.minute
    val date = dateTime.toLocalDate()
    val hour = if (hours < 10) "0$hours" else "$hours"
    return DateTime( dateStr = date.toString(),
        timeStr = String.format(Locale.getDefault(), HOURS_MINUTES_FORMAT, hours, minutes),
        hourStr = hour
    )
}


fun getCurrentTime(pattern: String): String {
    val currentTime = LocalTime.now()
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return currentTime.format(formatter)
}

fun getIconUrl(icon: String): String {
    return icon.replace("64x64", "128x128")
        .replace("//", "https://")
}

fun isFirstHourAfterSecond(hourString1: String, hourString2: String): Boolean {
    val hour1 = hourString1.toInt()
    val hour2 = hourString2.toInt()
    return when {
        hour1 < hour2 -> true
        hour1 > hour2 -> false
        else -> true
    }
}

