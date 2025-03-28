package com.visma.feature

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.visma.data.local.ReceiptEntity
import com.visma.feature.viewmodel.ReceiptViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptScreen(
    viewModel: ReceiptViewModel = hiltViewModel() ,
    onAddReceipt: () -> Unit
) {

    val receipts by viewModel.receipts.observeAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Receipts") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddReceipt) {
                Icon(Icons.Default.Add, contentDescription = "Add Receipt")
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                items(receipts) { receipt ->
                    ReceiptItem(receipt)
                }
            }
        }
    )
}

@Composable
fun ReceiptItem(receipt: ReceiptEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Log.d("ReceiptItem", "Image URI: ${receipt.imageUri}")

            if (receipt.imageUri.isNotEmpty()) {
                Image(
                    painter = rememberImagePainter(
                        data = receipt.imageUri,
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = "Captured Receipt Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "Placeholder Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                "Amount: ${receipt.amount} ${receipt.currency}",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                "Date: ${formatDate(receipt.date)}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}