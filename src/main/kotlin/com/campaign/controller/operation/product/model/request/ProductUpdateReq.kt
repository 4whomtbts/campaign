package com.campaign.controller.operation.product.model.request

data class ProductUpdateReq(
    val products: List<ProductUpdate>,
)

data class ProductUpdate(
    val productId: Long,
    val updatedPrice: Long,
)
