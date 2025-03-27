package com.visma.feature.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.visma.data.local.ReceiptEntity
import com.visma.feature.repository.ReceiptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val receiptRepository: ReceiptRepository
) : ViewModel() {

    private val _receipts = MutableLiveData<List<ReceiptEntity>>()
    val receipts: LiveData<List<ReceiptEntity>> = _receipts

    init {
        loadReceipts()
    }

    fun loadReceipts() {
        viewModelScope.launch {
            try {
                val receiptList = receiptRepository.getReceiptFromDb()
                _receipts.postValue(receiptList)
            } catch (e: Exception) {
                Log.e("ReceiptViewModel", "Error loading receipts", e)
            }
        }
    }

    fun addReceipt(imageUri: String, date: Long, amount: Double, currency: String) {
        viewModelScope.launch {
            try {
                val newReceipt = ReceiptEntity(
                    imageUri = imageUri,
                    date = date,
                    amount = amount,
                    currency = currency
                )
                receiptRepository.saveReceipt(newReceipt)
                loadReceipts()
            } catch (e: Exception) {
                Log.e("ReceiptViewModel", "Error adding receipt", e)
            }
        }
    }
}
