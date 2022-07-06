package com.example.sample_android_app.models.responses

import com.example.sample_android_app.models.data.Item

data class ItemsResponse (
    val results: List<Item>,
    val pagination: Pagination
)

data class Pagination (
    val key: Any? = null
)