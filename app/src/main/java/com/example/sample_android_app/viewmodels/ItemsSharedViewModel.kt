package com.example.sample_android_app.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.sample_android_app.models.data.Item
import com.example.sample_android_app.networking.ItemsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemsSharedViewModel : ViewModel() {

    private val TAG = "ItemsSharedViewModel"

    val items: MutableLiveData<List<Item>?> = MutableLiveData<List<Item>?>()
    val selectedItem: MutableLiveData<Item?> = MutableLiveData<Item?>()

    var updateProgressState: ((Boolean) -> Unit)? = null

    init {
        viewModelScope.launch {
            updateProgressState?.invoke(true)
            //items as MutableLiveData
            items.value = getItems()
            updateProgressState?.invoke(false)
        }
    }

    fun loadItems(){
        viewModelScope.launch {
            updateProgressState?.invoke(true)

            //(items as MutableLiveData).value = getItems()
            //items.value = getItems()
            //TODO: if you have a multiple serialized calls that depends in previous call, you could use the below approach:
            val tempItems = async { getItems() }
            items.value = tempItems.await()

            updateProgressState?.invoke(false)
        }
    }

    fun clearSelectedItem(){
        selectedItem.value = null
    }

    private suspend fun getItems(): List<Item>? {
        return withContext(Dispatchers.IO) {
            Log.i(TAG, "Getting Items ...")
            ItemsApi().getItems().body()?.results
        }
    }

}