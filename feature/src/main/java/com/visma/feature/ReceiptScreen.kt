package com.visma.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import com.visma.data.local.ReceiptEntity
import com.visma.feature.viewmodel.ReceiptViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptScreen(
    viewModel: ReceiptViewModel = hiltViewModel(),
    onAddReceipt: () -> Unit
) {
    // Observe the list of receipts from ViewModel
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
                    ReceiptItem(receipt) // Display each receipt
                }
            }
        }
    )
}

// Composable to display individual receipt item
@Composable
fun ReceiptItem(receipt: ReceiptEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp) // Corrected elevation for Material 3
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Amount: ${receipt.amount} ${receipt.currency}",
                style = MaterialTheme.typography.headlineSmall // Updated typography for Material 3
            )
            Text(
                "Date: ${formatDate(receipt.date)}",
                style = MaterialTheme.typography.bodyMedium // Updated typography for Material 3
            )
        }
    }
}

// Helper function to format the date
fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}