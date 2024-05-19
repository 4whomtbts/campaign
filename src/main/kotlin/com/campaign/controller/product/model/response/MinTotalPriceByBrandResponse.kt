package com.campaign.controller.product.model.response

import com.campaign.controller.DefaultResponse

data class MinTotalPriceByBrandResponse(
    val brandName: String,
    val categories: List<MinPriceProductInCategory>,
    val totalPrice: Long,
) : DefaultResponse()

data class MinPriceProductInCategory(
    val category: String,
    val price: Long,
)
