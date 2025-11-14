package com.example.weatherapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.weatherapp.domain.entity.HourData

@Composable
fun HourScreen(
    hourData: HourData,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = hourData.localTime,
            color = Color.LightGray
        )
        AsyncImage(
            model = hourData.condition.icon,
            contentDescription = null,
            modifier = Modifier.width(40.dp)
        )
        Text(
            text = "${hourData.temperature}°C",
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}