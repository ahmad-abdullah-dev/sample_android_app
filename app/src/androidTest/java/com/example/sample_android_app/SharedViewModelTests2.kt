package com.example.sample_android_app

import android.util.Log
import com.example.sample_android_app.viewmodels.ItemsSharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

// [START coroutine_test_homeviewmodel_test]
class SharedViewModelTests2 {

    @Test
    fun testLoadItems() = runTest {

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        try {

            val viewModel = ItemsSharedViewModel()

            //viewModel.loadItems()// Uses testDispatcher, runs its coroutine eagerly
            val items = viewModel.items.value

            assertEquals(0, viewModel.items.value?.size ?: 0)

        } finally {
            Dispatchers.resetMain()
        }
    }
}
// [END coroutine_test_homeviewmodel_test]
