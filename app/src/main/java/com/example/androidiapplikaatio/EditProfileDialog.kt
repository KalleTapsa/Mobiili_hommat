package com.example.androidiapplikaatio

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.request.SuccessResult
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

@Composable
fun EditProfileDialog(
    state: AccountState,
    onEvent: (AccountEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var ImageUri by remember { mutableStateOf(state.image) }

    val painter = rememberAsyncImagePainter(
        if (ImageUri.isEmpty())
            R.drawable.kaapo_suu
        else
            ImageUri
    )
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            ImageUri = it.toString()
            val userPhotoId = UUID.randomUUID().toString()
            val location = saveImageToInternalStorage(context = context, uri = it, userId = userPhotoId)
            onEvent(AccountEvent.setProfilepicture(location.toString()))
        }
    }

    AlertDialog(
        title = {
            Text(text = "Add friend")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        .clickable { launcher.launch("image/*") }
                )

                TextField(
                    value = state.username,
                    onValueChange = {
                        onEvent(AccountEvent.setUsername(it))
                    },
                    placeholder = {
                        Text(text = "Username")
                    }
                )
                TextField(
                    value = state.lastName,
                    onValueChange = {
                        onEvent(AccountEvent.SetLastName(it))
                    },
                    placeholder = {
                        Text(text = "lastname")
                    }
                )

            }
        },
        onDismissRequest = {
            onEvent(AccountEvent.HideDialog)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onEvent(AccountEvent.SaveAccount)
                }
            ) {
                Text("Save changes")
            }
        }
    )
}

fun saveImageToInternalStorage(context: Context, uri: Uri, userId: String): Uri {
    val inputStream = context.contentResolver.openInputStream(uri)

    val localAppFolder = File(context.filesDir, "profile_images")
    if (!localAppFolder.exists()) {
        localAppFolder.mkdirs()
    }

    val userFolder = File(localAppFolder, userId)
    if (!userFolder.exists()) {
        userFolder.mkdirs()
    }

    val localFile = File(userFolder, "pfp.jpg")

    if (localFile.exists()) {
        localFile.delete()
    }

    val outputStream = FileOutputStream(localFile)
    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }

    return localFile.toUri()
}
/*
@Composable
fun EditProfileDialog(
    state: AccountState,
    onEvent: (AccountEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var ImageUri by remember { mutableStateOf(state.image) }

    val painter = rememberAsyncImagePainter(
        if (ImageUri.isEmpty())
            R.drawable.kaapo_suu
        else
            ImageUri
    )
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            ImageUri = it.toString()
            val location = saveImageToInternalStorage(context = context, uri = it)
            onEvent(AccountEvent.setProfilepicture(location.toString()))
        }
    }

    AlertDialog(
        title = {
            Text(text = "Add friend")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,

                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        .clickable { launcher.launch("image/*") }
                )

                TextField(
                    value = state.username,
                    onValueChange = {
                        onEvent(AccountEvent.setUsername(it))
                    },
                    placeholder = {
                        Text(text = "Username")
                    }
                )
                TextField(
                    value = state.lastName,
                    onValueChange = {
                        onEvent(AccountEvent.SetLastName(it))
                    },
                    placeholder = {
                        Text(text = "lastname")
                    }
                )

            }
        },
        onDismissRequest = {
            onEvent(AccountEvent.HideDialog)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onEvent(AccountEvent.SaveAccount)
                }
            ) {
                Text("Save changes")
            }
        }
    )
}
fun saveImageToInternalStorage(context: Context, uri: Uri): Uri {
    val inputStream = context.contentResolver.openInputStream(uri)

    val localAppFolder = File(context.filesDir, "profile_images")
    if (!localAppFolder.exists()) {
        localAppFolder.mkdirs()
    }

    val localFile = File(localAppFolder, "pfp.jpg")

    if (localFile.exists()) {
        localFile.delete()
    }

    val outputStream = FileOutputStream(localFile)
    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }

    return localFile.toUri()
}
*/
 */
