package com.campaign.controller.product.model.response

data class MinTotalPriceByBrandResponse(
    val brandName: String,
    val categories: List<MinPriceProductInCategory>,
    val totalPrice: Long,
)

data class MinPriceProductInCategory(
    val category: String,
    val price: Long,
)
