package com.example.weatherapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.domain.entity.DayType
import com.example.weatherapp.presentation.WeatherState

@Composable
fun WeatherForecastScreen(
    state: WeatherState,
    dayType: DayType,
    modifier: Modifier = Modifier
) {
    val index = when (dayType) {
        DayType.TODAY -> 0
        DayType.TOMORROW -> 1
        DayType.AFTER_TOMORROW -> 2
    }

    state.weatherInfo?.forecast?.forecastDayData?.get(index)?.let { data ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = if (index == 0) stringResource(R.string.today) else data.date,
                fontSize = 20.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(content = {
                itemsIndexed(
                    data.hourData,
                    key = { index, item -> item.timeEpoch }) { index, item ->
                    HourScreen(
                        hourData = item,
                        modifier = Modifier
                            .height(100.dp)
                            .padding(horizontal = 16.dp)
                    )
                }
            })
        }
    }
}