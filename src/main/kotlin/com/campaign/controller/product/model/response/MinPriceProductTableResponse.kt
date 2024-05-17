package com.campaign.controller.product.model.response

import com.campaign.service.MinPriceProductProvider

data class MinPriceProductTableResponse(
    val products: List<MinPriceProductProvider.ProductDetail>,
    val totalPrice: Long,
)
