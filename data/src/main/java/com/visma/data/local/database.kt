package com.visma.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ReceiptEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun receiptDao(): ReceiptDao

}
