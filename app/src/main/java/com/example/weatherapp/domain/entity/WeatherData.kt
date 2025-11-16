package com.example.weatherapp.domain.entity


data class WeatherData(
    val name: String,
    val localDateTime: String,
    val localTime: String,
    val localDate: String,
    val current: CurrentData,
    val forecast: ForecastData
)


data class ForecastData(
    val forecastDayData: List<ForecastDayData>
)

data class ForecastDayData(
    val date: String,
    val hourData: List<HourData>
)

data class HourData(
    val localDateTime: String,
    val localTime: String,
    val localDate: String,
    val condition: ConditionData,
    val temperature: Int,
    val timeEpoch: Long
)

data class CurrentData(
    val temperature: Int,
    val condition: ConditionData
)


data class ConditionData(
    val icon: String,
    val text: String
)
