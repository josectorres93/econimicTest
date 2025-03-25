package com.visma.feature

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ReceiptApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "receipt_screen") {
        composable("receipt_screen") {
            ReceiptScreen(onAddReceipt = {
                navController.navigate("add_receipt_screen")
            })
        }
        composable("add_receipt_screen") {
            AddReceiptScreen(onReceiptAdded = {
                navController.popBackStack()
            })
        }
    }
}
