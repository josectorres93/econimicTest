package com.visma.feature

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.visma.feature.viewmodel.ReceiptViewModel

import java.io.File

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddReceiptScreen(
    viewModel: ReceiptViewModel = hiltViewModel(),
    onReceiptAdded: () -> Unit
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var amount by remember { mutableStateOf("") }
    var currency by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(System.currentTimeMillis()) }

    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
        } else {
            imageUri = null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add Receipt") })
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                imageUri?.let { uri ->
                    Image(
                        painter = rememberImagePainter(data = uri),
                        contentDescription = "Captured Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                } ?: run {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No Image Captured", color = Color.Gray)
                    }
                }

                Button(
                    onClick = {
                        if (cameraPermissionState.status.isGranted) {
                            imageUri = createImageUri(context)
                            launcher.launch(imageUri!!)
                        } else {
                            cameraPermissionState.launchPermissionRequest() // Request permission
                        }
                    }
                ) {
                    Text("Take Photo")
                }

                TextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                TextField(
                    value = currency,
                    onValueChange = { currency = it },
                    label = { Text("Currency") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        if (amount.isNotEmpty() && currency.isNotEmpty() && imageUri != null) {
                            viewModel.addReceipt(imageUri.toString(), date, amount.toDouble(), currency)
                            onReceiptAdded()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Receipt")
                }
            }
        }
    )
}

// Create a unique image URI based on the current time
fun createImageUri(context: Context): Uri {
    val timestamp = System.currentTimeMillis() // Generate a unique file name based on timestamp
    val imageFile = File(context.externalCacheDir, "receipt_image_$timestamp.jpg") // Unique filename
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        imageFile
    )
}
