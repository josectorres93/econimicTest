package com.visma.feature

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.visma.data.local.ReceiptEntity
import com.visma.feature.repository.ReceiptRepository
import com.visma.feature.viewmodel.ReceiptViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ReceiptViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var receiptViewModel: ReceiptViewModel

    @Mock
    lateinit var receiptRepository: ReceiptRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        Dispatchers.setMain(UnconfinedTestDispatcher())

        receiptViewModel = ReceiptViewModel(receiptRepository)

        mockRepository()
    }

    private fun mockRepository() = runBlocking {
        whenever(receiptRepository.getReceiptFromDb()).thenReturn(
            listOf(ReceiptEntity(0, "uri", 1234567890, 100.0, "escudos"))
        )
    }

    @Test
    fun testLoadReceipts() = runTest {
        receiptViewModel.loadReceipts()

        advanceUntilIdle()

        val receipts = receiptViewModel.receipts.value
        assertNotNull(receipts)
        assertTrue(receipts?.isNotEmpty() == true)
        assertEquals(1, receipts?.size)
        assertEquals("uri", receipts?.get(0)?.imageUri)
    }

    @Test
    fun testAddReceipt() = runTest {
        val imageUri = "newUri"
        val date = 1234567890123L
        val amount = 150.0
        val currency = "dollar"

        receiptViewModel.addReceipt(imageUri, date, amount, currency)

        val newReceipt = ReceiptEntity(imageUri = imageUri, date = date, amount = amount, currency = currency)
        verify(receiptRepository).saveReceipt(newReceipt)

        whenever(receiptRepository.getReceiptFromDb()).thenReturn(
            listOf(
                ReceiptEntity(1, "uri", 1234567890, 100.0, "escudos"),
                newReceipt
            )
        )

        advanceUntilIdle()

        val receipts = receiptViewModel.receipts.value
        assertNotNull(receipts)
        assertTrue(receipts?.isNotEmpty() == true)
        assertEquals("uri", receipts?.get(0)?.imageUri)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}