package com.visma.feature

import com.visma.data.local.ReceiptDao
import com.visma.feature.repository.ReceiptRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ReceiptModule {
    @Provides
    fun provideReceiptRepository(receiptDao: ReceiptDao): ReceiptRepository {
        return ReceiptRepository(receiptDao)
    }
}
