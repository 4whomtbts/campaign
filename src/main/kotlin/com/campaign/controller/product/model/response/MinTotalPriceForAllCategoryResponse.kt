package com.campaign.controller.product.model.response

import com.campaign.controller.DefaultResponse
import com.campaign.service.model.ProductDetail

data class MinTotalPriceForAllCategoryResponse(
    val data: MinPriceProductTableDto,
) : DefaultResponse()

data class MinPriceProductTableDto(
    val products: List<ProductDetail>,
    val totalPrice: Long,
) : DefaultResponse()
