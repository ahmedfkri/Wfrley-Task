package com.example.wfrleytask.model

import com.google.gson.annotations.SerializedName

data class Category(
    val id: Int,
    val image: String,
    val isActive: Boolean,
    val merchantId: String,
    val minSaleQty: Int,
    val name: String,
    @SerializedName("name_En")
    val nameEn: String,
    @SerializedName("name_Fr")
    val nameFr: String,
    val parentCategoryId: Int,
    val rank: Int
)