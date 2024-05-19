package com.campaign.controller.operation.product.model.request

import com.campaign.controller.DefaultResponse

data class ProductUpdateReq(
    val products: List<ProductUpdate>,
) : DefaultResponse()

data class ProductUpdate(
    val productId: Long,
    val updatedPrice: Long,
) : DefaultResponse()
