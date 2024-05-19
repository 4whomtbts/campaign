package com.campaign.controller.product.model.response

import com.campaign.controller.DefaultResponse

data class MinTotalPriceForAllCategoryByBrandResponse(
    val data: MinTotalPriceByBrandDto?,
) : DefaultResponse()

data class MinTotalPriceByBrandDto(
    val brandName: String,
    val categories: List<MinPriceProductInCategory>,
    val totalPrice: Long,
)

data class MinPriceProductInCategory(
    val category: String,
    val price: Long,
)
