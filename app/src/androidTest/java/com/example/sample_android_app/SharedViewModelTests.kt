package com.example.sample_android_app

import com.example.sample_android_app.viewmodels.ItemsSharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

// [START coroutine_test_homeviewmodel_test]

@ExperimentalCoroutinesApi
class SharedViewModelTests {

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        // Sets the given [dispatcher] as an underlying dispatcher of [Dispatchers.Main].
        // All consecutive usages of [Dispatchers.Main] will use given [dispatcher] under the hood.
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        // Resets state of the [Dispatchers.Main] to the original main dispatcher.
        // For example, in Android Main thread dispatcher will be set as [Dispatchers.Main].
        Dispatchers.resetMain()

        // Clean up the TestCoroutineDispatcher to make sure no other work is running.
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testGetItemsNotNull() = runBlocking {
        val viewModel = ItemsSharedViewModel()
        //viewModel.loadMessage() // Uses testDispatcher, runs its coroutine eagerly
        assertNotNull(viewModel.items.value)
    }

    @Test
    fun testGetItemsCount() = runBlocking {
        val viewModel = ItemsSharedViewModel()
        //viewModel.loadMessage() // Uses testDispatcher, runs its coroutine eagerly
        assertEquals(20, viewModel.items.value?.size)
    }

}
// [END coroutine_test_homeviewmodel_test]
