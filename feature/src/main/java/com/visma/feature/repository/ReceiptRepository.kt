package com.visma.feature.repository

import com.visma.data.local.ReceiptDao
import com.visma.data.local.ReceiptEntity
import javax.inject.Inject

class ReceiptRepository @Inject constructor(
    private val receiptDao: ReceiptDao
) {
    suspend fun getReceiptFromDb(): List<ReceiptEntity> {
        return receiptDao.getAllReceipts()
        }
    suspend fun saveReceipt(receipt: ReceiptEntity) {
        receiptDao.insertReceipt(receipt)
    }
    }
