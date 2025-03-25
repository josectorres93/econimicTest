package com.visma.domain.model

data class ReceiptEntity(
    val id: Int = 0,
    val imageUri: String,
    val date: Long,
    val amount: Double,
    val currency: String
)
