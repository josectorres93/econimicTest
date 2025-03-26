package com.visma.feature

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.visma.feature.viewmodel.ReceiptViewModel


@Composable
fun ReceiptApp(viewModel: ReceiptViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "receipt_screen") {
        composable("receipt_screen") {
            ReceiptScreen(viewModel, onAddReceipt = {
                navController.navigate("add_receipt_screen")
            })
        }
        composable("add_receipt_screen") {
            AddReceiptScreen(viewModel, onReceiptAdded = {
                navController.popBackStack()
            })
        }
    }
}
