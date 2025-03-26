package com.visma.economic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import com.visma.economic.ui.theme.MyApplicationTheme
import com.visma.feature.ReceiptApp
import com.visma.feature.viewmodel.ReceiptViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                 val viewModel: ReceiptViewModel = hiltViewModel()
                ReceiptApp(viewModel)
            }
        }
    }
}

