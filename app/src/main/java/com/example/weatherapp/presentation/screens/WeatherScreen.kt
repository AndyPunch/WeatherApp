package com.example.weatherapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapp.R
import com.example.weatherapp.presentation.WeatherState


@Composable
fun WeatherScreen(
    state: WeatherState,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    state.weatherInfo?.let { data ->
        Card(
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor,
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = data.name,
                        color = Color.White
                    )
                    Text(
                        text = stringResource(R.string.todayTime, data.localTime),
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                AsyncImage(
                    model = data.current.condition.icon,
                    contentDescription = null,
                    modifier = Modifier.size(128.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.celsius, data.current.temperature),
                    fontSize = 50.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = data.current.condition.text,
                    fontSize = 20.sp,
                    color = Color.White
                )

            }
        }
    }
}