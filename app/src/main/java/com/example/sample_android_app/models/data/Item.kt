package com.example.sample_android_app.models.data

import com.google.gson.annotations.SerializedName

class Item (
    @SerializedName("created_at"            ) var createdAt           : String?      = null,
    @SerializedName("price"                 ) var price               : String?      = null,
    @SerializedName("name"                  ) var name                : String?      = null,
    @SerializedName("uid"                   ) var uid                 : String?      = null,
    @SerializedName("image_ids"             ) var imageIds            : List<String> = arrayListOf(),
    @SerializedName("image_urls"            ) var imageUrls           : List<String> = arrayListOf(),
    @SerializedName("image_urls_thumbnails" ) var imageUrlsThumbnails : List<String> = arrayListOf()
)

//
//class Item (
//    @Json(name = "created_at")
//    val createdAt: String,
//
//    val price: String,
//    val name: String,
//    val uid: String,
//
//    @Json(name = "image_ids")
//    val imageIDS: List<String>,
//
//    @Json(name = "image_urls")
//    val imageUrls: List<String>,
//
//    @Json(name = "image_urls_thumbnails")
//    val imageUrlsThumbnails: List<String>
//)