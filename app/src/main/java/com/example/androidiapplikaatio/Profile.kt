package com.example.androidiapplikaatio

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.ui.Alignment.Companion.CenterVertically


@Composable
fun Profile(
    navigateBack: () -> Unit,
    navigateToScreenThree: () -> Unit,
    state: AccountState,
    onEvent: (AccountEvent) -> Unit
) {
    var text by remember {mutableStateOf(state.username)}
    text = "Kaapo"

    var ImageUri by remember { mutableStateOf(state.image) }

    val painter = rememberAsyncImagePainter(
        if (ImageUri.isEmpty())
            R.drawable.kaapo_suu
        else
            ImageUri = state.image
    )


    if (state.isAddingContact) {
        EditProfileDialog(state = state, onEvent = onEvent)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Centered content
        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Kaapo's Profile",
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 20.sp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Image(
                painter = painter,
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
                        text = text,
                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 40.sp)
                    )
                }
            }
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                /*item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SortType.values().forEach { sortType ->
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        onEvent(AccountEvent.SortAccounts(sortType))
                                    },
                                verticalAlignment = CenterVertically
                            ) {
                                RadioButton(
                                    selected = state.sortType == sortType,
                                    onClick = {
                                        onEvent(AccountEvent.SortAccounts(sortType))
                                    }
                                )
                                Text(text = sortType.name)
                            }
                        }
                    }
                }*/
                items(state.accounts) { account ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val imagePainter = rememberAsyncImagePainter(account.image)
                        Image(painter = imagePainter, contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        )
                        Spacer(modifier = Modifier.padding(5.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "${account.username} ${account.lastName}",
                                fontSize = 20.sp
                            )
                        }
                        IconButton(onClick = {
                            onEvent(AccountEvent.DeleteAccount(account))
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete contact"
                            )
                        }
                    }
                }
            }
        }
        
        // Button in the top-right corner
        Button(
            onClick = {
                onEvent(AccountEvent.ShowDialog)
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .size(width = 110.dp, height = 65.dp)
                .padding(12.dp)
                .align(Alignment.BottomEnd)
        ) {
            Text(
                text = "Add friends",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                textAlign = TextAlign.Center
            )
        }

        // Button in the top-left corner
        Button(
            onClick = navigateBack,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .size(width = 110.dp, height = 65.dp)
                .padding(12.dp)
                .align(Alignment.TopStart)
        ) {
            Text(
                text = "Back",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = navigateToScreenThree,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .size(width = 125.dp, height = 65.dp)
                .padding(12.dp)
                .align(Alignment.TopEnd)
        ) {
            Text(
                text = "Friends",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.padding(10.dp))
    }
}