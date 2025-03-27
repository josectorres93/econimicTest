package com.visma.feature

import com.visma.data.local.ReceiptDao
import com.visma.data.local.ReceiptEntity
import com.visma.feature.repository.ReceiptRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.InjectMocks
import org.mockito.Mock

class ReceiptRepositoryTest {

    @Mock
    private lateinit var receiptDao: ReceiptDao

    @InjectMocks
    private lateinit var receiptRepository: ReceiptRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test getReceiptFromDb returns list of receipts`() = runBlocking {
        // Arrange
        val receipts = listOf(
            ReceiptEntity(
                id = 1,
                imageUri = "uri1",
                date = 1617830400000,
                amount = 10.0,
                currency = "euros"
            ),
            ReceiptEntity(
                id = 2,
                imageUri = "uri2",
                date = 1617916800000,
                amount = 20.0,
                currency = "pesos"
            )
        )
        `when`(receiptDao.getAllReceipts()).thenReturn(receipts)

        val result = receiptRepository.getReceiptFromDb()

        verify(receiptDao, times(1)).getAllReceipts()
        assert(result == receipts)
    }

    @Test
    fun `test saveReceipt calls insertReceipt on DAO`() = runBlocking {
        val receipt = ReceiptEntity(
            id = 0,
            imageUri = "uri3",
            date = 1618003200000,
            amount = 15.0,
            currency = "escudos"
        )

        receiptRepository.saveReceipt(receipt)

        verify(receiptDao, times(1)).insertReceipt(receipt)
    }
}