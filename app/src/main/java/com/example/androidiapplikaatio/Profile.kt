package com.example.androidiapplikaatio

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Profile(
    navigateBack: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Centered content
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Kaapo",
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 40.sp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Image(
                painter = painterResource(R.drawable.kaapo_suu),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
            Spacer(modifier = Modifier.padding(5.dp))

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {

                Row {
                    Text(
                        text = "First Name:",
                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 20.sp)
                    )

                    Spacer(modifier = Modifier
                        .height(10.dp)
                        .width(20.dp))

                    Text(
                        text = "Kaapo",
                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 20.sp)
                    )

                }
                Row {
                    Text(
                        text = "Last Name:",
                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 20.sp)
                    )

                    Spacer(modifier = Modifier
                        .height(10.dp)
                        .width(20.dp))

                    Text(
                        text = "Kalju",
                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 20.sp)
                    )

                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {},
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .size(width = 80.dp, height = 40.dp)
            ) {
                Text(
                    text = "Edit",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Button in the top-left corner
        Button(
            onClick = navigateBack,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .size(width = 115.dp, height = 65.dp)
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Text(
                text = "Back",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                textAlign = TextAlign.Center
            )
        }
    }
}