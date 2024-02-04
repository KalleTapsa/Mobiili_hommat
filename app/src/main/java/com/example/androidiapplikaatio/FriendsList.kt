package com.example.androidiapplikaatio

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun FriendsList(
    navigateBack: () -> Unit,
    state: AccountState,
    onEvent: (AccountEvent) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row {
                Button(
                    onClick = navigateBack,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .size(width = 110.dp, height = 65.dp)
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Back",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
        }
        item {
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
                                onEvent(AccountEvent.SortAccounts(sortType)) },
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
        }
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