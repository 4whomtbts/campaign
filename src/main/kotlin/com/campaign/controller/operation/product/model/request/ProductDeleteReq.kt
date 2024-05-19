package com.campaign.controller.operation.product.model.request

data class ProductDeleteReq(
    val targets: List<ProductDelete>,
)

data class ProductDelete(
    val productId: Long,
)
